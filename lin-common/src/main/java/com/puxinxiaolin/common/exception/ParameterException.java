package com.puxinxiaolin.common.exception;

import com.puxinxiaolin.common.constant.ApiResponseCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 参数异常
 * @Author: YCcLin
 * @Date: 2025/3/31 20:35
 */
public class ParameterException extends BaseException {

    private Map<String, String> fieldErrors;

    public ParameterException(String message) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), message);
    }

    public ParameterException(int code, String message) {
        super(code, message);
    }

    public ParameterException(Map<String, String> fieldErrors) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), ApiResponseCode.PARAMETER_INVALID.getMessage());
        this.fieldErrors = fieldErrors;
    }

    public ParameterException(String key, String value) {
        super(ApiResponseCode.PARAMETER_INVALID.getCode(), ApiResponseCode.PARAMETER_INVALID.getMessage());
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put(key, value);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public ParameterException(String message, Throwable t) {
        super(message, t);
    }
}
