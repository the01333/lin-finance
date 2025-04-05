package com.puxinxiaolin.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description: 获取微信 token 返回接口实体信息
 * @Author: YCcLin
 * @Date: 2025/4/5 21:28
 */
@Data
public class AccessTokenResult {

    /**
     * 获取到的凭证
     */
    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒，目前是7200秒之前的值
     */
    @JsonProperty(value = "expires_in", required = true)
    private String expiresIn;

    /**
     * 错误码
     */
    @JsonProperty(value = "errcode", required = true)
    private String errCode;

    /**
     * 错误信息
     */
    @JsonProperty(value = "errmsg", required = true)
    private String errMsg;

}
