package com.puxinxiaolin.finance.biz.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户表绑定微信openid表（表：member_bind_wx_openid）
 *
 * @author YCcLin
 */
@Setter
@Getter
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