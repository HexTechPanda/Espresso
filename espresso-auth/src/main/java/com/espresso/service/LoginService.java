package com.espresso.service;

import com.espresso.domain.LoginRequest;
import com.espresso.commons.result.Result;

public interface LoginService {
    Result login(LoginRequest loginRequest);
    Result logout();
}
