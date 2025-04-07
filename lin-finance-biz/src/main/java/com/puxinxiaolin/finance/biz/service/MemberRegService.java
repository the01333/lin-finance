package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.common.dto.TokenResponse;
import com.puxinxiaolin.finance.biz.dto.form.PhoneRegisterForm;
import com.puxinxiaolin.finance.biz.dto.vo.GenerateMpRegCodeVo;
import com.puxinxiaolin.wx.dto.MpSubscribeEventRequest;

public interface MemberRegService {

    /**
     * 手机号注册
     *
     * @param form
     * @return
     */
    Long phoneReg(PhoneRegisterForm form);

    /**
     * 生成微信公众号二维码（关注注册）
     *
     * @param clientId
     * @return
     */
    GenerateMpRegCodeVo generateMpRegCode(String clientId);

    /**
     * 处理微信公众号关注事件
     *
     * @param mpSubscribeEventRequest
     */
    void handleMpSubscribeEventRequest(MpSubscribeEventRequest mpSubscribeEventRequest);

    /**
     * 通过openId注册
     *
     * @param appId
     * @param clientId
     * @param openId
     * @return
     */
    TokenResponse registerByOpenId(String appId, String clientId, String openId);

    /**
     * 扫码注册
     *
     * @param appId
     * @param openId
     * @return
     */
    Long scReg(String appId, String openId);

}
