package com.puxinxiaolin.finance.biz.domain;

import java.util.Date;

/**
 * 用户表绑定手机表（表：member_bind_phone）
 *
 * @author YCcLin
 */
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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