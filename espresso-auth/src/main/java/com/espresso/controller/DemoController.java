package com.espresso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @PreAuthorize("hasAuthority('thirdparty')")
    @GetMapping("/thirdparty/hello")
    public String thirdPartyHello(){
        return "Thirdparty Hello.";
    }
}
