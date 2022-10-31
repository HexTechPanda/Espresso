package com.espresso.service.impl;


import com.espresso.commons.model.UserProfile;
import com.espresso.commons.result.Result;
import com.espresso.domain.LoginRequest;
import com.espresso.domain.LoginUser;
import com.espresso.dto.SysUser;
import com.espresso.service.LoginService;
import com.espresso.commons.utils.JwtUtil;
import com.espresso.commons.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public Result login(LoginRequest loginRequest) {
        // 3 Authenticate using the ProviderManager auth method
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Authenticate fail
        if(Objects.isNull(authenticate)){
            throw new BadCredentialsException("username or password error");
        }

        // 4 Generate jwt to the front end
        LoginUser loginUser= (LoginUser) (authenticate.getPrincipal());
        SysUser sysUser = loginUser.getSysUser();
        String userId = sysUser.getId();
        String jwt = JwtUtil.createJWT(userId);
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

        return Result.ok("Login success.", map);
    }

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getSysUser().getId();
        redisCache.deleteObject("login:" + userId);

        return Result.ok();
    }

}