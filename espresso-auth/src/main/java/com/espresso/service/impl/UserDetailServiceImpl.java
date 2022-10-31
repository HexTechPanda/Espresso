package com.espresso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.espresso.domain.LoginUser;
import com.espresso.dto.SysUser;
import com.espresso.mapper.SysUserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. if username empty
        if(StringUtils.isEmpty(username)){
            throw new BadCredentialsException("Username should not be empty!");
        }

        // Query user information by user name
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = sysUserMapper.selectOne(wrapper);
        // If no data can be queried, a prompt is given by throwing an exception
        if(Objects.isNull(user)){
            throw new BadCredentialsException("username or password error");
        }
        // TODO query permission from the database, now make a mock permission
        List<String> permissionList = new ArrayList<>(Arrays.asList("thirdparty", "customer"));
        return new LoginUser(user, permissionList);
    }
}
