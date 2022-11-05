package com.espresso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.espresso.commons.result.Result;
import com.espresso.domain.OAuth2RegisterRequest;
import com.espresso.domain.RegisterRequest;
import com.espresso.dto.SysUser;
import com.espresso.dto.UserRole;
import com.espresso.mapper.SysUserMapper;
import com.espresso.mapper.SysUserRoleMapper;
import com.espresso.security.oauth2.OAuth2AuthenticationProcessingException;
import com.espresso.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Result checkUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        return Result.ok( sysUser == null ? false : true );
    }

    @Override
    public Result register(RegisterRequest req) {
        if(StringUtils.isEmpty( req.getUsername() )) {
            return Result.error("Username can not be empty.");
        }

        if(StringUtils.isEmpty( req.getPassword() )) {
            return Result.error("Password can not be empty.");
        }

        if(StringUtils.isEmpty( req.getRepPassword() )) {
            return Result.error("Confirm password can not be empty.");
        }

        if( !StringUtils.equals(req.getPassword(), req.getRepPassword())) {
            return Result.error("The two entered passwords do not match.");
        }

        // Check if username exists
        Result result = checkUsername(req.getUsername());
        if( (Boolean) result.getData() ) {
            return Result.error("Username has already been registered, please change a username.");
        }

        SysUser sysUser = new SysUser();
        sysUser.setUsername( req.getUsername() );
        sysUser.setPassword( passwordEncoder.encode( req.getPassword() ) );
        sysUser.setIsAccountNonLocked(1);
        sysUser.setIsAccountNonLocked(1);
        sysUser.setIsCredentialsNonExpired(1);
        sysUser.setIsEnabled(1);
        sysUser.setNickName( req.getNickName() );
        sysUser.setMobile( req.getMobile() );
        sysUser.setEmail( req.getEmail() );
        sysUser.setCreateDate( new Date() );
        sysUser.setUpdateDate( new Date() );
        sysUser.setPwdUpdateDate( new Date() );

        try {
            this.save(sysUser);
        } catch (DuplicateKeyException ex){
            return Result.error("Mobile or email has registered.");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(sysUser.getId());
        userRole.setRole("ROLE_customer");
        sysUserRoleMapper.insert(userRole);

        return Result.ok();
    }

    @Override
    public SysUser oauth2Register(OAuth2RegisterRequest req) throws OAuth2AuthenticationProcessingException{
        if(StringUtils.isEmpty( req.getEmail())){
            throw new OAuth2AuthenticationProcessingException("Email can not be empty.");
        }
        // Use email as username
        Result result = checkUsername(req.getEmail());
        if( (Boolean) result.getData() ) {
            throw new OAuth2AuthenticationProcessingException("Email has already been registered.");
        }

        SysUser sysUser = new SysUser();
        // Email as username
        sysUser.setUsername( req.getEmail() );
        sysUser.setIsAccountNonLocked(1);
        sysUser.setIsAccountNonLocked(1);
        sysUser.setIsCredentialsNonExpired(1);
        sysUser.setIsEnabled(1);
        sysUser.setNickName( req.getNickName() );
        sysUser.setEmail( req.getEmail() );
        sysUser.setCreateDate( new Date() );
        sysUser.setUpdateDate( new Date() );
        sysUser.setPwdUpdateDate( new Date() );
        sysUser.setProvider(req.getProvider());
        sysUser.setProviderId(req.getProviderId());

        try {
            this.save(sysUser);
        } catch (DuplicateKeyException ex){
            throw new OAuth2AuthenticationProcessingException("Mobile or email has registered.");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(sysUser.getId());
        userRole.setRole("ROLE_customer");
        sysUserRoleMapper.insert(userRole);
        return sysUser;
    }

    @Override
    public SysUser findByEmail(String email){
        if(StringUtils.isEmpty(email)){
            return null;
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return baseMapper.selectOne(wrapper);
    }

    public SysUser updateUser(SysUser sysUser){
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return sysUser;
    }
}
