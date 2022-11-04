package com.espresso.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User Entity
 *
 * @author Mingze Ma
 */
@Getter
@Setter
@Entity
@Table(name = "espresso_user")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    private String password;

    private Integer isAccountNonExpired;

    private Integer isAccountNonLocked;

    private Integer isCredentialsNonExpired;

    private Integer isEnabled;

    private String nickName;

    @Column(unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;

    private Date createDate;

    private Date updateDate;

    private Date pwdUpdateDate;

    private String provider;

    private String providerId;

}
