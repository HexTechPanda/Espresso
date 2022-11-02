package com.espresso.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2RegisterRequest {
    private String nickName;
    private String email;
    private String provider;
    private String providerId;
}
