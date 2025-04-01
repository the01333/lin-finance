package com.puxinxiaolin.mybatis.help;

import lombok.Data;
import lombok.Getter;

import java.util.Collection;

@Data
public class Criterion {

    @Getter
    private String condition;

    @Getter
    private Object value;

    @Getter
    private Object secondValue;

    @Getter
    private boolean noValue;

    @Getter
    private boolean singleValue;

    @Getter
    private boolean betweenValue;

    @Getter
    private boolean listValue;

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
