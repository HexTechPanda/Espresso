package com.espresso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.espresso.commons.result.Result;
import com.espresso.domain.RegisterRequest;
import com.espresso.dto.SysUser;

public interface UserService extends IService<SysUser> {
    Result checkUsername(String username);
    Result register(RegisterRequest registerRequest);
}
