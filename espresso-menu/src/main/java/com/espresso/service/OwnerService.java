package com.espresso.service;

import com.espresso.dao.model.User;
import org.springframework.stereotype.Service;

/**
 * Owner Service
 *
 * @author Mingze Ma
 */
public interface OwnerService {

    /**
     * Get current logged-in user
     * @return User
     */
    User getLoggedInUserInfo();

}
