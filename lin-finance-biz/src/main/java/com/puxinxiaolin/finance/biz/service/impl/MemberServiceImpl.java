package com.puxinxiaolin.finance.biz.service.impl;

import com.puxinxiaolin.common.constant.CommonConstant;
import com.puxinxiaolin.finance.biz.domain.Member;
import com.puxinxiaolin.finance.biz.mapper.MemberMapper;
import com.puxinxiaolin.finance.biz.service.MemberService;
import com.puxinxiaolin.mybatis.help.MybatisWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.puxinxiaolin.finance.biz.domain.MemberField.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    final MemberMapper memberMapper;

    /**
     * 用户注册
     *
     * @param tenantId
     * @return
     */
    @Override
    public Long reg(Long tenantId) {
        Member member = new Member();
        member.setTenantId(tenantId);
        member.setSysRoleIds("[" + CommonConstant.ROLE_MEMBER + "]");
        member.initDefault();
        memberMapper.insert(member);

        return member.getId();
    }

    /**
     * 根据 id 获取用户信息
     *
     * @param memberId
     * @return
     */
    @Override
    public Member get(Long memberId) {
        MybatisWrapper<Member> wrapper = new MybatisWrapper<>();
        wrapper.select(Id, Name, NickName, AvatarUrl, Email, TenantId, SysRoleIds, Disable)
                .whereBuilder().andEq(setId(memberId));

        return memberMapper.topOne(wrapper);
    }
}
