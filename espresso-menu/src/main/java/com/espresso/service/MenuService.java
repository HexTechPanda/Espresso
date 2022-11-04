package com.espresso.service;

import com.espresso.commons.result.Result;
import com.espresso.domain.vo.CreateOutlineRequest;
import org.springframework.stereotype.Service;

/**
 * Menu Service Interface
 *
 * @author Mingze Ma
 */
public interface MenuService {

    Result createOutline(CreateOutlineRequest request);
}
