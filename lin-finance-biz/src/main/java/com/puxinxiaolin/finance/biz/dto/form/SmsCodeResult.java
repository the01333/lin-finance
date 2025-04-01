package com.puxinxiaolin.finance.biz.dto.form;

import lombok.Data;

import java.util.Date;

/**
 * @Description: 短信验证码 Result
 * @Author: YCcLin
 * @Date: 2025/4/1 22:07
 */
@Data
public class SmsCodeResult {

    /**
     * 短信验证码
     */
    private String code;

    /**
     * 短信验证码存储到redis的时间
     */
    private Date getTime;

    /**
     * 手机号
     */
    private String phone;

}