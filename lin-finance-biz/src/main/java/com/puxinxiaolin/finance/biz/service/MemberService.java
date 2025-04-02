package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.finance.biz.domain.Member;

public interface MemberService {

    /**
     * 用户注册
     *
     * @param tenantId
     * @return
     */
    Long reg(Long tenantId);

    /**
     * 根据 id 获取用户信息
     *
     * @param memberId
     * @return
     */
    Member get(Long memberId);

}
