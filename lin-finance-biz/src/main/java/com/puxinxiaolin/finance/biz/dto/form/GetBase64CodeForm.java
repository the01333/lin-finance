package com.puxinxiaolin.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 获取图形验证码入参
 * @Author: YCcLin
 * @Date: 2025/3/31 17:18
 */
@Data
public class GetBase64CodeForm {

    @ApiModelProperty(value = "客户端id")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,32}$", message = "clientId非法")
    @NotBlank(message = "客户端id不能为空")
    private String clientId;

}
