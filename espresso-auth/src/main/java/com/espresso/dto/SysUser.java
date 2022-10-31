package com.espresso.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("espresso_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 5629146250912887163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String password;
    private Integer isAccountNonExpired;
    private Integer isAccountNonLocked;
    private Integer isCredentialsNonExpired;
    private Integer isEnabled;
    private String nickName;
    private String mobile;
    private String email;
    private Date createDate;
    private Date updateDate;
    private Date pwdUpdateDate;
}
