package com.achain.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SscStatTotalCriteria implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offSet;

    protected Integer pageSize;

    private static final long serialVersionUID = 1L;

    public SscStatTotalCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setOffSet(Integer offSet) {
        this.offSet=offSet;
    }

    public Integer getOffSet() {
        return offSet;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize=pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        private static final long serialVersionUID = 1L;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameIsNull() {
            addCriterion("total_stat_name is null");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameIsNotNull() {
            addCriterion("total_stat_name is not null");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameEqualTo(String value) {
            addCriterion("total_stat_name =", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameNotEqualTo(String value) {
            addCriterion("total_stat_name <>", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameGreaterThan(String value) {
            addCriterion("total_stat_name >", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameGreaterThanOrEqualTo(String value) {
            addCriterion("total_stat_name >=", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameLessThan(String value) {
            addCriterion("total_stat_name <", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameLessThanOrEqualTo(String value) {
            addCriterion("total_stat_name <=", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameLike(String value) {
            addCriterion("total_stat_name like", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameNotLike(String value) {
            addCriterion("total_stat_name not like", value, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameIn(List<String> values) {
            addCriterion("total_stat_name in", values, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameNotIn(List<String> values) {
            addCriterion("total_stat_name not in", values, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameBetween(String value1, String value2) {
            addCriterion("total_stat_name between", value1, value2, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatNameNotBetween(String value1, String value2) {
            addCriterion("total_stat_name not between", value1, value2, "totalStatName");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueIsNull() {
            addCriterion("total_stat_value is null");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueIsNotNull() {
            addCriterion("total_stat_value is not null");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueEqualTo(Long value) {
            addCriterion("total_stat_value =", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueNotEqualTo(Long value) {
            addCriterion("total_stat_value <>", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueGreaterThan(Long value) {
            addCriterion("total_stat_value >", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueGreaterThanOrEqualTo(Long value) {
            addCriterion("total_stat_value >=", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueLessThan(Long value) {
            addCriterion("total_stat_value <", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueLessThanOrEqualTo(Long value) {
            addCriterion("total_stat_value <=", value, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueIn(List<Long> values) {
            addCriterion("total_stat_value in", values, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueNotIn(List<Long> values) {
            addCriterion("total_stat_value not in", values, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueBetween(Long value1, Long value2) {
            addCriterion("total_stat_value between", value1, value2, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andTotalStatValueNotBetween(Long value1, Long value2) {
            addCriterion("total_stat_value not between", value1, value2, "totalStatValue");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {
        private static final long serialVersionUID = 1L;

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        private static final long serialVersionUID = 1L;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}