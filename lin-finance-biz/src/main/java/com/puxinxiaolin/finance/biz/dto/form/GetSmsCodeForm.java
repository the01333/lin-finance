package com.puxinxiaolin.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 获取短信验证码入参
 * @Author: YCcLin
 * @Date: 2025/4/1 21:53
 */
@Data
public class GetSmsCodeForm {

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,32}$", message = "clientId非法")
    @NotBlank(message = "客户端id不能为空")
    private String clientId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误")
    private String phone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "图形验证码")
    @NotBlank(message = "请输入图形验证码")
    @Pattern(regexp = "^[a-zA-Z0-9]{5}$", message = "图形验证码格式错误")
    private String code;

    /**
     * 验证码类型 REG|LOGIN
     */
    @ApiModelProperty(value = "验证码类型 REG|LOGIN")
    @NotBlank(message = "请输入短信验证码类型")
    @Pattern(regexp = "^REG|LOGIN$", message = "短信验证码类型非法")
    private String smsCodeType;

}
