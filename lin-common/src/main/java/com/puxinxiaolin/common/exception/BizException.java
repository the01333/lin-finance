package com.puxinxiaolin.common.exception;

import com.puxinxiaolin.common.constant.ApiResponseCode;

/**
 * @Description: 业务异常
 * @Author: YCcLin
 * @Date: 2025/3/31 20:34
 */
public class BizException extends BaseException {

    public BizException(String message) {
        super(ApiResponseCode.BUSINESS_ERROR.getCode(), message);
    }

    public BizException(int code, String message) {
        super(code, message);
    }

    public BizException(String message, Throwable t) {
        super(message, t);
    }
}
