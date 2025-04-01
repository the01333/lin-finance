package com.puxinxiaolin.finance.biz.mapper;

import com.puxinxiaolin.finance.biz.domain.Member;
import com.puxinxiaolin.mybatis.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends CommonMapper<Member> {
}