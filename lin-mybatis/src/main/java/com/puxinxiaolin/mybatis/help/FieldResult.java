package com.puxinxiaolin.mybatis.help;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description: 拼接更新语句和更新参数，查询条件和查询参数
 * @Author: YCcLin
 * @Date: 2025/4/9 14:57
 */
@Getter
public class FieldResult {
    @Setter
    private DbField field;
    @Setter
    private Object value;
    private List<?> values;

    public FieldResult(DbField field, List<?> values) {
        this.field = field;
        this.values = values;
        if (values.size() == 1) {
            this.value = values.get(0);
        }
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}