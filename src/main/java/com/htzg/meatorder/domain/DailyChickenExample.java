package com.htzg.meatorder.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DailyChickenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DailyChickenExample() {
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

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChickenNumberIsNull() {
            addCriterion("chicken_number is null");
            return (Criteria) this;
        }

        public Criteria andChickenNumberIsNotNull() {
            addCriterion("chicken_number is not null");
            return (Criteria) this;
        }

        public Criteria andChickenNumberEqualTo(Integer value) {
            addCriterion("chicken_number =", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberNotEqualTo(Integer value) {
            addCriterion("chicken_number <>", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberGreaterThan(Integer value) {
            addCriterion("chicken_number >", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("chicken_number >=", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberLessThan(Integer value) {
            addCriterion("chicken_number <", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberLessThanOrEqualTo(Integer value) {
            addCriterion("chicken_number <=", value, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberIn(List<Integer> values) {
            addCriterion("chicken_number in", values, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberNotIn(List<Integer> values) {
            addCriterion("chicken_number not in", values, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberBetween(Integer value1, Integer value2) {
            addCriterion("chicken_number between", value1, value2, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("chicken_number not between", value1, value2, "chickenNumber");
            return (Criteria) this;
        }

        public Criteria andChickenTypeIsNull() {
            addCriterion("chicken_type is null");
            return (Criteria) this;
        }

        public Criteria andChickenTypeIsNotNull() {
            addCriterion("chicken_type is not null");
            return (Criteria) this;
        }

        public Criteria andChickenTypeEqualTo(ChickenType value) {
            addCriterion("chicken_type =", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeNotEqualTo(ChickenType value) {
            addCriterion("chicken_type <>", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeGreaterThan(ChickenType value) {
            addCriterion("chicken_type >", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeGreaterThanOrEqualTo(ChickenType value) {
            addCriterion("chicken_type >=", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeLessThan(ChickenType value) {
            addCriterion("chicken_type <", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeLessThanOrEqualTo(ChickenType value) {
            addCriterion("chicken_type <=", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeLike(ChickenType value) {
            addCriterion("chicken_type like", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeNotLike(ChickenType value) {
            addCriterion("chicken_type not like", value, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeIn(List<ChickenType> values) {
            addCriterion("chicken_type in", values, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeNotIn(List<ChickenType> values) {
            addCriterion("chicken_type not in", values, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeBetween(ChickenType value1, ChickenType value2) {
            addCriterion("chicken_type between", value1, value2, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenTypeNotBetween(ChickenType value1, ChickenType value2) {
            addCriterion("chicken_type not between", value1, value2, "chickenType");
            return (Criteria) this;
        }

        public Criteria andChickenNameIsNull() {
            addCriterion("chicken_name is null");
            return (Criteria) this;
        }

        public Criteria andChickenNameIsNotNull() {
            addCriterion("chicken_name is not null");
            return (Criteria) this;
        }

        public Criteria andChickenNameEqualTo(String value) {
            addCriterion("chicken_name =", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameNotEqualTo(String value) {
            addCriterion("chicken_name <>", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameGreaterThan(String value) {
            addCriterion("chicken_name >", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameGreaterThanOrEqualTo(String value) {
            addCriterion("chicken_name >=", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameLessThan(String value) {
            addCriterion("chicken_name <", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameLessThanOrEqualTo(String value) {
            addCriterion("chicken_name <=", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameLike(String value) {
            addCriterion("chicken_name like", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameNotLike(String value) {
            addCriterion("chicken_name not like", value, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameIn(List<String> values) {
            addCriterion("chicken_name in", values, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameNotIn(List<String> values) {
            addCriterion("chicken_name not in", values, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameBetween(String value1, String value2) {
            addCriterion("chicken_name between", value1, value2, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenNameNotBetween(String value1, String value2) {
            addCriterion("chicken_name not between", value1, value2, "chickenName");
            return (Criteria) this;
        }

        public Criteria andChickenIdIsNull() {
            addCriterion("chicken_id is null");
            return (Criteria) this;
        }

        public Criteria andChickenIdIsNotNull() {
            addCriterion("chicken_id is not null");
            return (Criteria) this;
        }

        public Criteria andChickenIdEqualTo(String value) {
            addCriterion("chicken_id =", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdNotEqualTo(String value) {
            addCriterion("chicken_id <>", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdGreaterThan(String value) {
            addCriterion("chicken_id >", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdGreaterThanOrEqualTo(String value) {
            addCriterion("chicken_id >=", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdLessThan(String value) {
            addCriterion("chicken_id <", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdLessThanOrEqualTo(String value) {
            addCriterion("chicken_id <=", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdLike(String value) {
            addCriterion("chicken_id like", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdNotLike(String value) {
            addCriterion("chicken_id not like", value, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdIn(List<String> values) {
            addCriterion("chicken_id in", values, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdNotIn(List<String> values) {
            addCriterion("chicken_id not in", values, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdBetween(String value1, String value2) {
            addCriterion("chicken_id between", value1, value2, "chickenId");
            return (Criteria) this;
        }

        public Criteria andChickenIdNotBetween(String value1, String value2) {
            addCriterion("chicken_id not between", value1, value2, "chickenId");
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

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

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