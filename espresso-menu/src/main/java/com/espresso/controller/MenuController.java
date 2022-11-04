package com.espresso.controller;

import com.espresso.commons.result.Result;
import com.espresso.domain.vo.CreateOutlineRequest;
import com.espresso.service.MenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Menu Controller
 *
 * @author Mingze Ma
 */
@RestController
public class MenuController {

    @Resource
    MenuService menuService;

    @PostMapping("/outline/create")
    public Result createOutline(@Validated @RequestBody CreateOutlineRequest request) {
        return menuService.createOutline(request);
    }
}
