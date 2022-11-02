package com.espresso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.espresso.commons.result.Result;
import com.espresso.domain.OAuth2RegisterRequest;
import com.espresso.domain.RegisterRequest;
import com.espresso.dto.SysUser;

public interface UserService extends IService<SysUser> {
    Result checkUsername(String username);
    Result register(RegisterRequest registerRequest);
    SysUser oauth2Register(OAuth2RegisterRequest req);
    SysUser findByEmail(String email);
    SysUser updateUser(SysUser sysUser);
}
