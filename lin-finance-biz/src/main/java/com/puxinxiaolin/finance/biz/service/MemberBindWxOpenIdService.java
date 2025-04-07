package com.puxinxiaolin.finance.biz.service;

import com.puxinxiaolin.finance.biz.domain.MemberBindWxOpenId;

public interface MemberBindWxOpenIdService {

    /**
     * 根据 appId 和 openId 获取绑定关系
     *
     * @param appId
     * @param openId
     * @return
     */
    MemberBindWxOpenId get(String appId, String openId);

    /**
     * 根据 appId 和 openId 创建绑定关系
     *
     * @param appId
     * @param openId
     * @param memberId
     * @return
     */
    Boolean reg(String appId, String openId, Long memberId);

}
