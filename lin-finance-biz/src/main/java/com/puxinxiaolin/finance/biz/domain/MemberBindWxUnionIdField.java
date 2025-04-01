package com.puxinxiaolin.finance.biz.domain;

import com.puxinxiaolin.mybatis.help.DbField;
import com.puxinxiaolin.mybatis.help.FieldResult;
import java.util.Collections;

public class MemberBindWxUnionIdField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField MemberId = new DbField("member_id","memberId","BIGINT","java.lang.Long");

    public static DbField UnionId = new DbField("union_id","unionId","VARCHAR","java.lang.String");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static DbField Disable = new DbField("disable","disable","BIT","java.lang.Boolean");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setMemberId(Long memberId) {
        return new FieldResult(MemberId, Collections.singletonList(memberId));
    }

    public static FieldResult setUnionId(String unionId) {
        return new FieldResult(UnionId, Collections.singletonList(unionId));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }

    public static FieldResult setDisable(Boolean disable) {
        return new FieldResult(Disable, Collections.singletonList(disable));
    }
}