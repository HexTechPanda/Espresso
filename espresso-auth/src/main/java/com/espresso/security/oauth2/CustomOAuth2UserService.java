package com.espresso.security.oauth2;

import com.espresso.domain.LoginUser;
import com.espresso.domain.OAuth2RegisterRequest;
import com.espresso.dto.SysUser;
import com.espresso.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SysUser sysUser = userService.findByEmail(oAuth2UserInfo.getEmail());
        if(sysUser != null){ // email exist
            if(!StringUtils.hasText(sysUser.getProvider()) || !StringUtils.hasText(sysUser.getProviderId())){
                // provider not exist
                sysUser = updateExistingUser(sysUser, oAuth2UserRequest, oAuth2UserInfo);
            }else{
                // provider exist but not equals
                if(!sysUser.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())){
                    throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                            sysUser.getProvider() + " account. Please use your " + sysUser.getProvider() +
                            " account to login.");
                }
            }
        }else{ // email not exist
            sysUser = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        // TODO: mock permission
        List<String> permissionList = new ArrayList<>(Arrays.asList("thirdparty", "customer"));
        LoginUser loginUser = new LoginUser(sysUser, permissionList);
        loginUser.setAttributes(oAuth2User.getAttributes());

        return loginUser;
    }

    private SysUser registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        OAuth2RegisterRequest request = new OAuth2RegisterRequest();
        request.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        request.setProviderId(oAuth2UserInfo.getId());
        request.setNickName(oAuth2UserInfo.getName());
        request.setEmail(oAuth2UserInfo.getEmail());
        return userService.oauth2Register(request);
    }

    private SysUser updateExistingUser(SysUser existingUser, OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        existingUser.setProviderId(oAuth2UserInfo.getId());
        return userService.updateUser(existingUser);
    }
}
