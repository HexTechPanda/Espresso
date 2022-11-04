package com.espresso.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create Outline Request
 *
 * @author Mingze Ma
 */
@Data
public class CreateOutlineRequest {

    @NotEmpty
    @ApiModelProperty(name = "Menu Outline Name")
    private String menuName;

    private boolean setDefault = false;

}
