package com.puxinxiaolin.finance.biz.domain;

import java.util.Date;

/**
 * 用户表（表：member）
 *
 * @author YCcLin
 */
public class Member {
    /**
     * 
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 角色id，多个以英文逗号分隔
     */
    private String sysRoleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSysRoleIds() {
        return sysRoleIds;
    }

    public void setSysRoleIds(String sysRoleIds) {
        this.sysRoleIds = sysRoleIds;
    }

    public void initDefault() {
        if (this.getName() == null) {
            this.setName("");
        }
        if (this.getNickName() == null) {
            this.setNickName("");
        }
        if (this.getDisable() == null) {
            this.setDisable(false);
        }
        if (this.getAvatarUrl() == null) {
            this.setAvatarUrl("");
        }
        if (this.getEmail() == null) {
            this.setEmail("");
        }
        if (this.getTenantId() == null) {
            this.setTenantId(0L);
        }
        if (this.getCreateTime() == null) {
            this.setCreateTime(new Date());
        }
        if (this.getUpdateTime() == null) {
            this.setUpdateTime(new Date());
        }
    }

    public void initUpdate() {
    }
}