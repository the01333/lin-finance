package com.puxinxiaolin.common.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description: Redisson 装配实体
 *
 * @Author: YCcLin
 * @Date: 2025/4/2 16:58
 */
@Data
@ConfigurationProperties(prefix = "redisson")
@ConditionalOnProperty("redisson.password")
public class RedisProperties {

    private int timeout = 3000;

    private String address;

    private String password;

    private int database = 0;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize = 10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}
