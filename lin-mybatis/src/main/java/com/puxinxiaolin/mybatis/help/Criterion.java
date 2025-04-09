package com.puxinxiaolin.mybatis.help;

import lombok.Data;
import lombok.Getter;

import java.util.Collection;

/**
 * @Description: 查询条件和查询参数
 * @Author: YCcLin
 * @Date: 2025/4/9 15:05
 */
@Data
public class Criterion {

    /**
     * 查询条件，如 xxx =、xxx > 等
     */
    @Getter
    private String condition;

    /**
     * 查询条件对应的值
     */
    @Getter
    private Object value;

    /**
     * 用于处理范围查询（如 BETWEEN）时的第二个值
     */
    @Getter
    private Object secondValue;

    /**
     * 表示该查询条件是否不需要值，例如 IS NULL 或 IS NOT NULL 这类条件
     */
    @Getter
    private boolean noValue;

    /**
     * 标志查询条件是否只需要一个值
     */
    @Getter
    private boolean singleValue;

    /**
     * 标志查询条件是否是范围查询（如 BETWEEN）
     */
    @Getter
    private boolean betweenValue;

    /**
     * 标志查询条件的值是否是一个集合，例如 IN 条件
     */
    @Getter
    private boolean listValue;

    /**
     * 指定处理值的类型处理器，是一个字符串类型
     */
    @Getter
    private String typeHandler;

    private String jdbcType;

    protected Criterion(String condition) {
        super();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    protected Criterion(String condition, Object value, String jdbcType, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
        if (value instanceof Collection<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    protected Criterion(String condition, Object value, String jdbcType) {
        this(condition, value, jdbcType, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String jdbcType, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
        this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue, String jdbcType) {
        this(condition, value, secondValue, jdbcType, null);
    }

}
