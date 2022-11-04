package com.espresso.service.impl;

import com.espresso.commons.result.Result;
import com.espresso.commons.result.ResultEnum;
import com.espresso.dao.model.MenuOutline;
import com.espresso.dao.model.User;
import com.espresso.dao.repository.MenuOutlineRepository;
import com.espresso.domain.vo.CreateOutlineRequest;
import com.espresso.domain.vo.CreateOutlineResponse;
import com.espresso.service.MenuService;
import com.espresso.service.OwnerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Menu Service Impl
 *
 * @author Mingze Ma
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuOutlineRepository menuOutlineRepository;

    @Resource
    OwnerService ownerService;

    @Override
    public Result createOutline(CreateOutlineRequest request) {
        // get user info
        User curUser = ownerService.getLoggedInUserInfo();
        if (curUser == null) {
            return Result.build(ResultEnum.AUTH_FAIL);
        }
        // create entity
        MenuOutline menuOutline = new MenuOutline();
        menuOutline.setMenuName(request.getMenuName());
        menuOutline.setOwner(curUser);
        menuOutline.setDefaultStatus(request.isSetDefault());
        menuOutline.setActiveStatus(false);
        // save outline
        MenuOutline res = menuOutlineRepository.save(menuOutline);

        // Build response
        CreateOutlineResponse response = new CreateOutlineResponse();
        BeanUtils.copyProperties(res, response);
        return Result.ok(response);
    }
}
