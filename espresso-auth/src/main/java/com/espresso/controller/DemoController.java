package com.espresso.controller;

import com.espresso.commons.model.UserProfile;
import com.espresso.commons.result.Result;
import com.espresso.domain.LoginUser;
import com.espresso.dto.SysUser;
import com.espresso.security.oauth2.CurrentUser;
import com.espresso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @PreAuthorize("hasRole('ROLE_thirdparty')")
    @GetMapping("/thirdparty/hello")
    public String thirdPartyHello(){
        return "Thirdparty Hello.";
    }


//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public Result getCurrentUser() {
//        @CurrentUser LoginUser loginUser
//        if(loginUser == null || loginUser.getSysUser() == null){
//            return Result.error("User profile not found.");
//        }
//        SysUser sysUser = userService.findByEmail(loginUser.getSysUser().getEmail());
//        return Result.ok(sysUser);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        String username = userProfile.getUsername();
        return Result.ok(userProfile);
    }
}
