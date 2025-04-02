package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.finance.biz.dto.form.PhoneRegisterForm;

public interface MemberRegService {

    /**
     * 手机号注册
     * @param form
     * @return
     */
    Long phoneReg(PhoneRegisterForm form);

}
