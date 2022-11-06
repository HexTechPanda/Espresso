package com.espresso.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -3881908608453549709L;
    private String id;
    private String username;
    private String nickName;
    private String mobile;
    private String email;

    private List<String> permissions;
}
