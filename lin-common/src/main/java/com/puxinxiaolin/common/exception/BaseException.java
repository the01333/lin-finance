package com.puxinxiaolin.common.exception;

import com.puxinxiaolin.common.constant.ApiResponseCode;

/**
 * @Description: 基本异常类
 * @Author: YCcLin
 * @Date: 2025/3/31 20:32
 */
public class BaseException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
        this.code = ApiResponseCode.SERVICE_ERROR.getCode();
    }

    public BaseException(String message, Throwable t) {
        super(message, t);
        this.code = ApiResponseCode.SERVICE_ERROR.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }
}
