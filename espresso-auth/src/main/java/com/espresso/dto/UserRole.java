package com.espresso.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("espresso_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 5629146250912887169L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String userId;
    private String role;
}
