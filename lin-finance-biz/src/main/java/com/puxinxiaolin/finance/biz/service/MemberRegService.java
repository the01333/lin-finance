package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.finance.biz.dto.form.PhoneRegisterForm;
import com.puxinxiaolin.finance.biz.dto.vo.GenerateMpRegCodeVo;

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

}
