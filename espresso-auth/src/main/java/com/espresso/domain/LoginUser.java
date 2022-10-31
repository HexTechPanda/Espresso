package com.espresso.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.espresso.dto.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private SysUser sysUser;

    List<String> permissions;

    public LoginUser(SysUser user, List<String> permissions) {
        this.sysUser = user;
        this.permissions = permissions;
    }

    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }

        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getIsAccountNonExpired() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getIsAccountNonLocked() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getIsCredentialsNonExpired() == 1;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getIsEnabled() == 1;
    }
}
