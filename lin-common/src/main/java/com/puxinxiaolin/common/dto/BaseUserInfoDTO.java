package com.puxinxiaolin.common.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BaseUserInfoDTO {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 角色 id
     */
    private Set<Long> sysRoleIds;

    /**
     * 租户 id
     */
    private Long tenantId;

    /**
     * token
     */
    private TokenResponse token;

    /**
     * 客户端 id（最新登录设备的id）
     */
    private String clientId;

}
