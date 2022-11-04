package com.espresso.service.impl;

import com.espresso.commons.model.UserProfile;
import com.espresso.dao.model.User;
import com.espresso.dao.repository.UserRepository;
import com.espresso.service.OwnerService;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Owner Service Impl
 * @author Mingze Ma
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    @Resource
    UserRepository userRepository;

    @Override
    public User getLoggedInUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();
        Optional<User> userRes = userRepository.findById(userProfile.getId());
        if (userRes.isEmpty()) {
            return null;
        }
        return userRes.get();
    }
}
