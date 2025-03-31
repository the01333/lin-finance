package com.puxinxiaolin.common.exception;

import com.puxinxiaolin.common.constant.ApiResponseCode;

/**
 * @Description: 登录异常
 * @Author: YCcLin
 * @Date: 2025/3/31 20:35
 */
public class LoginException extends BaseException {

    public LoginException(String message) {
        super(ApiResponseCode.LOGIN_ERROR.getCode(), message);
    }

    public LoginException(String message, Throwable t) {
        super(message, t);
    }

}
