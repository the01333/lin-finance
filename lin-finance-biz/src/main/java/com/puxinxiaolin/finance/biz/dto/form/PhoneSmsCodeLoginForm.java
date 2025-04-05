package com.puxinxiaolin.finance.biz.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 手机号短信登录入参
 * @Author: YCcLin
 * @Date: 2025/4/2 20:18
 */
@Data
public class PhoneSmsCodeLoginForm {

    /**
     * 客户端id
     */
    @NotBlank(message = "请输入客户端id")
    @Pattern(regexp = "^[0-9A-Za-z]{6,32}$", message = "clientId非法")
    private String clientId;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误！")
    private String phone;

    /**
     * 短信验证码
     */
    @ApiModelProperty("短信验证码")
    @NotBlank(message = "请输入短信验证码")
    @Pattern(regexp = "^[0-9]{6}$", message = "短信验证码格式不正确")
    private String smsCode;

}
