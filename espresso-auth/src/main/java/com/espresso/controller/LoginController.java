package com.espresso.controller;

import com.espresso.domain.LoginRequest;
import com.espresso.domain.RegisterRequest;
import com.espresso.service.LoginService;
import com.espresso.commons.result.Result;
import com.espresso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @PostMapping("/public/user/login")
    public Result login(@RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }

    @PostMapping("/user/logout")
    public Result logout(){
        return loginService.logout();
    }

    @PostMapping("/public/user/register")
    public Result register(@RequestBody @Valid RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

}