package com.puxinxiaolin.mybatis.help;

import lombok.Data;

/**
 * @Description: 数据库字段信息实体类
 * @Author: YCcLin
 * @Date: 2025/4/9 14:55
 */
@Data
public class DbField {

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库字段对应的java属性名称
     */
    private String propertyName;

    /**
     * 数据库字段类型
     */
    private String jdbcType;

    /**
     * 数据库对应的java字段类型
     */
    private String javaType;

    public DbField(String dbName, String propertyName, String jdbcType, String javaType) {
        this.dbName = dbName;
        this.propertyName = propertyName;
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

}
