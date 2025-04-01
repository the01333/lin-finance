package com.puxinxiaolin.finance.biz.service.impl;

import com.puxinxiaolin.finance.biz.domain.MemberBindPhone;
import com.puxinxiaolin.finance.biz.mapper.MemberBindPhoneMapper;
import com.puxinxiaolin.finance.biz.service.MemberBindPhoneService;
import com.puxinxiaolin.mybatis.help.MybatisWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.puxinxiaolin.finance.biz.domain.MemberBindPhoneField.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberBindPhoneServiceImpl implements MemberBindPhoneService {
    final MemberBindPhoneMapper memberBindPhoneMapper;

    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     * @return
     */
    @Override
    public MemberBindPhone getMemberByPhone(String phone) {
        MybatisWrapper<MemberBindPhone> wrapper = new MybatisWrapper<>();
        wrapper.select(MemberId, Phone, Password)
                .whereBuilder().andEq(setPhone(phone))
                .andEq(setDisable(false));

        return memberBindPhoneMapper.topOne(wrapper);
    }

}
