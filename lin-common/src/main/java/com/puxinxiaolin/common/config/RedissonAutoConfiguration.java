package com.puxinxiaolin.common.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description: Redisson 自动装配
 * @Author: YCcLin
 * @Date: 2025/4/2 16:58
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonAutoConfiguration {

    @Resource
    private RedisProperties redisProperties;

    /**
     * 哨兵模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonClient() {
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers()
                .addSentinelAddress(redisProperties.getSentinelAddresses())
                .setMasterName(redisProperties.getMasterName())
                .setTimeout(redisProperties.getTimeout())
                .setMasterConnectionPoolSize(redisProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redisProperties.getSlaveConnectionPoolSize());

        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serversConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redisProperties.getAddress())
                // 等待节点回复命令的时间，该时间从命令发送成功时开始计时
                .setTimeout(redisProperties.getTimeout())
                // 设置对于 master 节点的连接池中连接数最大为 50
                .setConnectionPoolSize(redisProperties.getConnectionPoolSize())
                // 连接池的最小空闲连接数，当连接池中的连接数低于此值时，会尝试创建新连接
                .setConnectionMinimumIdleSize(redisProperties.getConnectionMinimumIdleSize());

        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
