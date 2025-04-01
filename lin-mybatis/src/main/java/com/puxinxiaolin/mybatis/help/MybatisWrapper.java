package com.puxinxiaolin.mybatis.help;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Setter
@Getter
public class MybatisWrapper<T> {

    public MybatisWrapper() {
        this.oredCriteria = new ArrayList<>();
    }

    protected StringBuilder selectBuilder;
    protected StringBuilder whereBuilder;
    protected StringBuilder updateSql;
    protected StringBuilder orderByClause;
    protected StringBuilder groupBySql;
    protected Integer rows;
    protected Integer offset;
    protected Map<String, Object> updateRow;
    private Boolean countFlag = false;
    private Integer pageIndex;
    private Integer pageSize;

    // 查询条件
    protected List<Criteria<T>> oredCriteria;

    public MybatisWrapper<T> distinct(DbField... fields) {
        select(fields);
        selectBuilder.insert(0, "distinct ");
        return this;
    }

    /**
     * 查询指定的字段
     *
     * @param fields
     * @return
     */
    public MybatisWrapper<T> select(DbField... fields) {
        if (fields == null || fields.length == 0) {
            selectBuilder = new StringBuilder("*");
            return this;
        }
        if (selectBuilder == null) {
            selectBuilder = new StringBuilder();
        }
        for (DbField field : fields) {
            if (selectBuilder.length() > 0) {
                selectBuilder.append(",");
            }
            selectBuilder.append(field.getDbName());
        }
        return this;
    }

    public MybatisWrapper<T> sum(DbField field) {
        sum(field, null);
        return this;
    }

    public MybatisWrapper<T> sum(DbField... fields) {
        for (DbField field : fields) {
            sum(field, null);
        }
        return this;
    }

    public MybatisWrapper<T> sum(DbField field, String asName) {
        if (selectBuilder == null) {
            selectBuilder = new StringBuilder();
        }

        if (selectBuilder.length() > 0) {
            selectBuilder.append(",");
        }
        selectBuilder.append("sum(");
        selectBuilder.append(field.getDbName());
        selectBuilder.append(")");
        if (Strings.isNotBlank(asName)) {
            selectBuilder.append(" as ");
            selectBuilder.append(asName);
        }
        return this;
    }

    public MybatisWrapper<T> max(DbField field) {
        return max(field, null);
    }

    public MybatisWrapper<T> max(DbField field, String asName) {
        if (selectBuilder == null) {
            selectBuilder = new StringBuilder();
        }

        if (selectBuilder.length() > 0) {
            selectBuilder.append(",");
        }
        selectBuilder.append("max(");
        selectBuilder.append(field.getDbName());
        selectBuilder.append(")");
        selectBuilder.append(" as ");
        if (Strings.isNotBlank(asName)) {
            selectBuilder.append(asName);
        } else {
            selectBuilder.append(field.getDbName());
        }
        return this;
    }

    public MybatisWrapper<T> min(DbField field) {
        return min(field, null);
    }

    public MybatisWrapper<T> min(DbField field, String asName) {
        if (selectBuilder == null) {
            selectBuilder = new StringBuilder();
        }

        if (selectBuilder.length() > 0) {
            selectBuilder.append(",");
        }
        selectBuilder.append("min(");
        selectBuilder.append(field.getDbName());
        selectBuilder.append(")");
        selectBuilder.append(" as ");
        if (Strings.isNotBlank(asName)) {
            selectBuilder.append(asName);
        } else {
            selectBuilder.append(field.getDbName());
        }
        return this;
    }

    public MybatisWrapper<T> limit(int... limit) {
        this.offset = limit[0];
        if (limit.length > 1) {
            this.rows = limit[1];
        }
        return this;
    }

    /**
     * 设置要查询的分页信息
     *
     * @param pageIndex 页号
     * @param pageSize  页大小
     * @return
     */
    public MybatisWrapper<T> page(int pageIndex, int pageSize) {
        return page(pageIndex, pageSize, true);
    }

    /**
     * 设置要查询的分页信息
     *
     * @param pageIndex   页号
     * @param pageSize    页大小
     * @param selectCount 是否查询记录条数
     * @return
     */
    public MybatisWrapper<T> page(int pageIndex, int pageSize, boolean selectCount) {
        // 如果页码小于等于0，则返回第一页的数据
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        // 计算偏移量
        int offset = (pageIndex - 1) * pageSize;
        this.countFlag = selectCount;
        return limit(offset, pageSize);
    }

    public MybatisWrapper<T> orderByAsc(DbField... fields) {
        orderBy(fields);
        this.orderByClause.append(" asc");
        return this;
    }

    public MybatisWrapper<T> orderByDesc(DbField... fields) {
        orderBy(fields);
        this.orderByClause.append(" desc");
        return this;
    }

    private MybatisWrapper<T> orderBy(DbField... fields) {
        if (fields == null || fields.length == 0) {
            return this;
        }
        if (orderByClause == null) {
            orderByClause = new StringBuilder();
        }

        for (DbField field : fields) {
            if (orderByClause.length() > 0) {
                orderByClause.append(",");
            }
            orderByClause.append(field.getDbName());
        }
        return this;
    }

    public MybatisWrapper<T> groupBy(DbField... fields) {
        if (fields == null || fields.length == 0) {
            return this;
        }
        if (groupBySql == null) {
            groupBySql = new StringBuilder();
        }

        for (DbField field : fields) {
            if (groupBySql.length() > 0) {
                groupBySql.append(",");
            }
            groupBySql.append(field.getDbName());
        }
        return this;
    }

    public MybatisWrapper<T> update(DbField dbField, Object value) {
        return update(new FieldResult(dbField, Collections.singletonList(value)));
    }

    public MybatisWrapper<T> update(FieldResult fieldResult) {
        if (this.updateSql == null) {
            this.updateSql = new StringBuilder();
        }
        this.updateSql
                .append(fieldResult.getField().getDbName())
                .append(" = #{example.updateRow.")
                .append(fieldResult.getField().getPropertyName())
                .append(", jdbcType=")
                .append(fieldResult.getField().getJdbcType())
                .append("},");
        if (this.updateRow == null) {
            this.updateRow = new HashMap<>();
        }
        this.updateRow.put(fieldResult.getField().getPropertyName(), fieldResult.getValue());
        return this;
    }

    public MybatisWrapper<T> updateIncr(DbField dbField, Object value) {
        return updateIncr(new FieldResult(dbField, Collections.singletonList(value)));
    }

    public MybatisWrapper<T> updateIncr(FieldResult fieldResult) {
        if (this.updateSql == null) {
            this.updateSql = new StringBuilder();
        }
        this.updateSql
                .append(fieldResult.getField().getDbName())
                .append(" = ")
                .append(fieldResult.getField().getDbName())
                .append(" + ")
                .append(" #{example.updateRow.")
                .append(fieldResult.getField().getPropertyName())
                .append(", jdbcType=")
                .append(fieldResult.getField().getJdbcType())
                .append("},");
        if (this.updateRow == null) {
            this.updateRow = new HashMap<>();
        }
        this.updateRow.put(fieldResult.getField().getPropertyName(), fieldResult.getValue());
        return this;
    }

    public MybatisWrapper<T> updateDecr(DbField dbField, Object value) {
        return updateDecr(new FieldResult(dbField, Collections.singletonList(value)));
    }

    public MybatisWrapper<T> updateDecr(FieldResult fieldResult) {
        if (this.updateSql == null) {
            this.updateSql = new StringBuilder();
        }
        this.updateSql
                .append(fieldResult.getField().getDbName())
                .append(" = ")
                .append(fieldResult.getField().getDbName())
                .append(" - ")
                .append(" #{example.updateRow.")
                .append(fieldResult.getField().getPropertyName())
                .append(", jdbcType=")
                .append(fieldResult.getField().getJdbcType())
                .append("},");
        if (this.updateRow == null) {
            this.updateRow = new HashMap<>();
        }
        this.updateRow.put(fieldResult.getField().getPropertyName(), fieldResult.getValue());
        return this;
    }

    /**
     * 联接字符串
     *
     * @param dbField
     * @param value
     * @return
     */
    public MybatisWrapper<T> updateConcat(DbField dbField, Object value) {
        return updateConcat(new FieldResult(dbField, Collections.singletonList(value)));
    }

    /**
     * 联接字符串
     *
     * @param fieldResult
     * @return
     */
    public MybatisWrapper<T> updateConcat(FieldResult fieldResult) {
        if (this.updateSql == null) {
            this.updateSql = new StringBuilder();
        }
        this.updateSql
                .append(fieldResult.getField().getDbName())
                .append(" = CONCAT(")
                .append(fieldResult.getField().getDbName())
                .append(" , ")
                .append(" #{example.updateRow.")
                .append(fieldResult.getField().getPropertyName())
                .append(", jdbcType=")
                .append(fieldResult.getField().getJdbcType())
                .append("}),");
        if (this.updateRow == null) {
            this.updateRow = new HashMap<>();
        }
        this.updateRow.put(fieldResult.getField().getPropertyName(), fieldResult.getValue());
        return this;
    }

    public MybatisWrapper<T> selectCount(boolean countFlag) {
        this.countFlag = countFlag;
        return this;
    }

    public void or(Criteria<T> criteria) {
        criteria.setAndOrOr(false);
        oredCriteria.add(criteria);
    }

    public Criteria<T> or() {
        Criteria<T> criteria = createCriteriaInternal();
        criteria.setAndOrOr(false);
        oredCriteria.add(criteria);
        return criteria;
    }

    public void and(Criteria<T> criteria) {
        criteria.setAndOrOr(true);
        oredCriteria.add(criteria);
    }

    public Criteria<T> and() {
        Criteria<T> criteria = createCriteriaInternal();
        criteria.setAndOrOr(true);
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * 创建查询条件
     *
     * @return
     */
    public Criteria<T> whereBuilder() {
        Criteria<T> criteria = createCriteriaInternal();
        if (oredCriteria.isEmpty()) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria<T> createCriteriaInternal() {
        return new Criteria<>();
    }

    public void clear() {
        selectBuilder = null;
        whereBuilder = null;
        updateSql = null;
        if (!CollectionUtils.isEmpty(oredCriteria)) {
            oredCriteria.clear();
        }
    }

    /**
     * 分页查询
     *
     * @param commonMapper
     * @return
     */
    public PageInfo<T> listPage(CommonMapper<T> commonMapper) {
        int count = 0;
        if (countFlag) {
            count = commonMapper.count(this);
        }
        return new PageInfo<>(this.pageIndex, this.pageSize,
                count,
                commonMapper.list(this));
    }
}
