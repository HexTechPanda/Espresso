package com.espresso.controller;

import com.espresso.commons.model.UserProfile;
import com.espresso.commons.result.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String healthCheck() {
        return "HEALTH CHECK OK!";
    }

    @GetMapping("/public/hello")
    public String helloWorld(){
        return "Hello world!";
    }

    @GetMapping("/username")
    public Result username(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        String username = userProfile.getUsername();
        return Result.ok(username);
    }
}
