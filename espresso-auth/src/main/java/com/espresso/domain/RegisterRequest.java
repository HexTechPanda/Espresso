package com.espresso.domain;

import com.espresso.commons.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String username;

    @NotNull
    @Length(min=6)
    private String password;

    @NotNull
    @Length(min=6)
    private String repPassword;

    private String nickName;

    @IsMobile
    private String mobile;

    @Email
    private String email;
}
