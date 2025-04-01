package com.puxinxiaolin.finance.biz.enums;

import lombok.Getter;

/**
 * @Description: 短信验证码枚举
 * @Author: YCcLin
 * @Date: 2025/4/1 22:59
 */
@Getter
public enum SmsCodeEnum {

    REG("REG", "注册"),
    LOGIN("LOGIN", "登录"),
    ;

    private final String code;
    private final String message;

    SmsCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SmsCodeEnum getByCode(String code) {
        for (SmsCodeEnum smsCodeEnum : SmsCodeEnum.values()) {
            if (smsCodeEnum.getCode().equals(code)) {
                return smsCodeEnum;
            }
        }
        return null;
    }

}
