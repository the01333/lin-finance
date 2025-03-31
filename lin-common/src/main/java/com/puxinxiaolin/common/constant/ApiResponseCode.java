package com.puxinxiaolin.common.constant;

import lombok.Getter;

/**
 * @Description: 错误码枚举
 * @Author: YCcLin
 * @Date: 2025/3/31 16:28
 */
@Getter
public enum ApiResponseCode {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 参数错误
     */
    PARAMETER_INVALID(100, "参数错误"),
    /**
     * 业务错误
     */
    BUSINESS_ERROR(200, "业务错误"),
    /**
     * 登录错误
     */
    LOGIN_ERROR(201, "登录失败"),
    /**
     * 账号或密码错误
     */
    ACCOUNT_PASSWORD_ERROR(202, "账号或密码错误"),
    /**
     * 账号错误
     */
    ACCOUNT_ERROR(203, "账号错误"),
    /**
     * 服务异常
     */
    SERVICE_ERROR(500, "服务异常");

    private final Integer code;

    private final String message;

    ApiResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
