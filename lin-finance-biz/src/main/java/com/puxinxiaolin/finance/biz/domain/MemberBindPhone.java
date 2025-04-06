package com.puxinxiaolin.finance.biz.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户表绑定手机表（表：member_bind_phone）
 *
 * @author YCcLin
 */
@Setter
@Getter
public class MemberBindPhone {
    /**
     * 
     */
    private Long id;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

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
        if (this.getPhone() == null) {
            this.setPhone("");
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