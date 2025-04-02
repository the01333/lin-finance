package com.puxinxiaolin.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 登录返回实体
 * @Author: YCcLin
 * @Date: 2025/4/2 20:22
 */
@Data
public class TokenResponse implements Serializable {

    /**
     * token 登录凭证
     */
    private String token;

    /**
     * 过期时间（时间戳-只读）
     */
    private Long expireDateTime;

    /**
     * 过期时间，多少时间后过期（单位通过timeUnit设置）
     */
    private Integer expire;

    /**
     * 过期时间单位
     */
    private TimeUnit timeunit;

}
