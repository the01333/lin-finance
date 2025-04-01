package com.puxinxiaolin.finance.biz.domain;

import java.util.Date;

/**
 * 用户表绑定微信openid表（表：member_bind_wx_openid）
 *
 * @author YCcLin
 */
public class MemberBindWxOpenId {
    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 小程序或者公众号appid
     */
    private String appId;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否禁用
     */
    private Boolean disable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public void initDefault() {
        if (this.getMemberId() == null) {
            this.setMemberId(0L);
        }
        if (this.getAppId() == null) {
            this.setAppId("");
        }
        if (this.getOpenId() == null) {
            this.setOpenId("");
        }
        if (this.getCreateTime() == null) {
            this.setCreateTime(new Date());
        }
        if (this.getUpdateTime() == null) {
            this.setUpdateTime(new Date());
        }
        if (this.getDisable() == null) {
            this.setDisable(false);
        }
    }

    public void initUpdate() {
    }
}