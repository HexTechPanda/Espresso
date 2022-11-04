package com.espresso.security.oauth2;

import com.alibaba.fastjson.JSON;
import com.espresso.commons.model.UserProfile;
import com.espresso.commons.utils.JwtUtil;
import com.espresso.commons.utils.RedisCache;
import com.espresso.config.AppConfig;
import com.espresso.domain.LoginUser;
import com.espresso.dto.SysUser;
import com.espresso.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Resource
    AppConfig appConfig;

    @Autowired
    RedisCache redisCache;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        // 4 Generate jwt to the front end
        LoginUser loginUser= (LoginUser) (authentication.getPrincipal());
        SysUser sysUser = loginUser.getSysUser();
        String userId = sysUser.getId();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", JSON.toJSON(loginUser.getPermissions()));
        String jwt = JwtUtil.createJWT(userId, claims);
        Map<String, String> map = new HashMap();
        map.put("token",jwt);
        // 5 Put all information related to system users into Redis
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        userProfile.setUsername(sysUser.getUsername());
        userProfile.setNickName(sysUser.getNickName());
        userProfile.setMobile(sysUser.getMobile());
        userProfile.setEmail(sysUser.getEmail());
        userProfile.setPermissions(loginUser.getPermissions());

        redisCache.setCacheObject("login:" + userId, userProfile, 1800, TimeUnit.SECONDS);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", jwt)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appConfig.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
