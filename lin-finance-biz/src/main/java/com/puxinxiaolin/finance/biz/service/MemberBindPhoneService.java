package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.finance.biz.domain.MemberBindPhone;

public interface MemberBindPhoneService {

    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     * @return
     */
    MemberBindPhone getMemberByPhone(String phone);

}
