package com.puxinxiaolin.finance.biz.service.impl;

import com.puxinxiaolin.finance.biz.domain.MemberBindWxOpenId;
import com.puxinxiaolin.finance.biz.mapper.MemberBindWxOpenIdMapper;
import com.puxinxiaolin.finance.biz.service.MemberBindWxOpenIdService;
import com.puxinxiaolin.mybatis.help.MybatisWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.puxinxiaolin.finance.biz.domain.MemberBindWxOpenIdField.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberBindWxOpenIdServiceImpl implements MemberBindWxOpenIdService {
    final MemberBindWxOpenIdMapper memberBindWxOpenIdMapper;

    /**
     * 根据 appId 和 openId 获取绑定关系
     *
     * @param appId
     * @param openId
     * @return
     */
    @Override
    public MemberBindWxOpenId get(String appId, String openId) {
        MybatisWrapper<MemberBindWxOpenId> wrapper = new MybatisWrapper<>();
        wrapper.select(MemberId)
                .whereBuilder()
                .andEq(setAppId(appId)).andEq(setOpenId(openId));

        return memberBindWxOpenIdMapper.topOne(wrapper);
    }

    /**
     * 根据 appId 和 openId 创建绑定关系
     *
     * @param appId
     * @param openId
     */
    @Override
    public Boolean reg(String appId, String openId, Long memberId) {
        MemberBindWxOpenId memberBindWxOpenId = new MemberBindWxOpenId();
        memberBindWxOpenId.setMemberId(memberId);
        memberBindWxOpenId.setAppId(appId);
        memberBindWxOpenId.setOpenId(openId);
        memberBindWxOpenId.initDefault();

        return memberBindWxOpenIdMapper.insert(memberBindWxOpenId) > 0;
    }
}
