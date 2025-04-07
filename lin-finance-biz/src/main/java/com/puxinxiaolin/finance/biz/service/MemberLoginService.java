package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.common.dto.TokenResponse;
import com.puxinxiaolin.finance.biz.domain.Member;
import com.puxinxiaolin.finance.biz.dto.form.GetBase64CodeForm;
import com.puxinxiaolin.finance.biz.dto.form.GetSmsCodeForm;
import com.puxinxiaolin.finance.biz.dto.form.PhonePasswordLoginForm;
import com.puxinxiaolin.finance.biz.dto.form.PhoneSmsCodeLoginForm;

public interface MemberLoginService {

    /**
     * 获取客户端 ID
     *
     * @return
     */
    String getClientId();

    /**
     * 获取图形验证码
     *
     * @param form
     * @return
     */
    String getBase64Code(GetBase64CodeForm form);

    /**
     * 获取短信验证码
     *
     * @param form
     */
    void sendSmsCode(GetSmsCodeForm form);

    /**
     * 校验图形验证码
     *
     * @param clientId
     * @param code
     * @return
     */
    Boolean checkBase64Code(String clientId, String code);

    /**
     * 校验短信验证码
     *
     * @param phone
     * @param smsCode
     * @param smsCodeType
     * @return
     */
    Boolean checkSmsCode(String phone, String smsCode, String smsCodeType);

    /**
     * 手机号密码登录
     *
     * @param form
     * @return
     */
    TokenResponse phonePasswordLogin(PhonePasswordLoginForm form);

    /**
     * 登录成功
     *
     * @param member
     * @param clientId
     * @return
     */
    TokenResponse loginSuccess(Member member, String clientId);

    /**
     * 手机号短信登录
     *
     * @param form
     * @return
     */
    TokenResponse phoneSmsCodeLogin(PhoneSmsCodeLoginForm form);

    /**
     * 获取客户端 token
     *
     * @param clientId
     * @return
     */
    TokenResponse getClientToken(String clientId);

}
