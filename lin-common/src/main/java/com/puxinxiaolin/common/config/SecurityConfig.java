package com.puxinxiaolin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 安全配置
 * @Author: YCcLin
 * @Date: 2025/4/2 20:54
 */
@ConfigurationProperties(prefix = "security")
@Component
@Data
public class SecurityConfig {

    /**
     * 是否开启安全系统
     */
    private Boolean enable = false;

    /**
     * 获取用户信息方式
     * token: 从 token 获取
     * gateway: 从网关传递的请求头 user 中获取
     */
    private String getUserType = "token";

    /**
     * token 过期时间（秒）
     * 默认 1 小时
     */
    private Integer expire = 3600;

    private List<String> ignores;

}
