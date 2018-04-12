package com.achain.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SscTransactionExCriteria implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offSet;

    protected Integer pageSize;

    private static final long serialVersionUID = 1L;

    public SscTransactionExCriteria() {
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

        public Criteria andTrxIdIsNull() {
            addCriterion("trx_id is null");
            return (Criteria) this;
        }

        public Criteria andTrxIdIsNotNull() {
            addCriterion("trx_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrxIdEqualTo(String value) {
            addCriterion("trx_id =", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotEqualTo(String value) {
            addCriterion("trx_id <>", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdGreaterThan(String value) {
            addCriterion("trx_id >", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdGreaterThanOrEqualTo(String value) {
            addCriterion("trx_id >=", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLessThan(String value) {
            addCriterion("trx_id <", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLessThanOrEqualTo(String value) {
            addCriterion("trx_id <=", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdLike(String value) {
            addCriterion("trx_id like", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotLike(String value) {
            addCriterion("trx_id not like", value, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdIn(List<String> values) {
            addCriterion("trx_id in", values, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotIn(List<String> values) {
            addCriterion("trx_id not in", values, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdBetween(String value1, String value2) {
            addCriterion("trx_id between", value1, value2, "trxId");
            return (Criteria) this;
        }

        public Criteria andTrxIdNotBetween(String value1, String value2) {
            addCriterion("trx_id not between", value1, value2, "trxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdIsNull() {
            addCriterion("orig_trx_id is null");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdIsNotNull() {
            addCriterion("orig_trx_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdEqualTo(String value) {
            addCriterion("orig_trx_id =", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdNotEqualTo(String value) {
            addCriterion("orig_trx_id <>", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdGreaterThan(String value) {
            addCriterion("orig_trx_id >", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdGreaterThanOrEqualTo(String value) {
            addCriterion("orig_trx_id >=", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdLessThan(String value) {
            addCriterion("orig_trx_id <", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdLessThanOrEqualTo(String value) {
            addCriterion("orig_trx_id <=", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdLike(String value) {
            addCriterion("orig_trx_id like", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdNotLike(String value) {
            addCriterion("orig_trx_id not like", value, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdIn(List<String> values) {
            addCriterion("orig_trx_id in", values, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdNotIn(List<String> values) {
            addCriterion("orig_trx_id not in", values, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdBetween(String value1, String value2) {
            addCriterion("orig_trx_id between", value1, value2, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andOrigTrxIdNotBetween(String value1, String value2) {
            addCriterion("orig_trx_id not between", value1, value2, "origTrxId");
            return (Criteria) this;
        }

        public Criteria andFromAcctIsNull() {
            addCriterion("from_acct is null");
            return (Criteria) this;
        }

        public Criteria andFromAcctIsNotNull() {
            addCriterion("from_acct is not null");
            return (Criteria) this;
        }

        public Criteria andFromAcctEqualTo(String value) {
            addCriterion("from_acct =", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctNotEqualTo(String value) {
            addCriterion("from_acct <>", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctGreaterThan(String value) {
            addCriterion("from_acct >", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctGreaterThanOrEqualTo(String value) {
            addCriterion("from_acct >=", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctLessThan(String value) {
            addCriterion("from_acct <", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctLessThanOrEqualTo(String value) {
            addCriterion("from_acct <=", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctLike(String value) {
            addCriterion("from_acct like", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctNotLike(String value) {
            addCriterion("from_acct not like", value, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctIn(List<String> values) {
            addCriterion("from_acct in", values, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctNotIn(List<String> values) {
            addCriterion("from_acct not in", values, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctBetween(String value1, String value2) {
            addCriterion("from_acct between", value1, value2, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAcctNotBetween(String value1, String value2) {
            addCriterion("from_acct not between", value1, value2, "fromAcct");
            return (Criteria) this;
        }

        public Criteria andFromAddrIsNull() {
            addCriterion("from_addr is null");
            return (Criteria) this;
        }

        public Criteria andFromAddrIsNotNull() {
            addCriterion("from_addr is not null");
            return (Criteria) this;
        }

        public Criteria andFromAddrEqualTo(String value) {
            addCriterion("from_addr =", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrNotEqualTo(String value) {
            addCriterion("from_addr <>", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrGreaterThan(String value) {
            addCriterion("from_addr >", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrGreaterThanOrEqualTo(String value) {
            addCriterion("from_addr >=", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrLessThan(String value) {
            addCriterion("from_addr <", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrLessThanOrEqualTo(String value) {
            addCriterion("from_addr <=", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrLike(String value) {
            addCriterion("from_addr like", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrNotLike(String value) {
            addCriterion("from_addr not like", value, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrIn(List<String> values) {
            addCriterion("from_addr in", values, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrNotIn(List<String> values) {
            addCriterion("from_addr not in", values, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrBetween(String value1, String value2) {
            addCriterion("from_addr between", value1, value2, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andFromAddrNotBetween(String value1, String value2) {
            addCriterion("from_addr not between", value1, value2, "fromAddr");
            return (Criteria) this;
        }

        public Criteria andToAcctIsNull() {
            addCriterion("to_acct is null");
            return (Criteria) this;
        }

        public Criteria andToAcctIsNotNull() {
            addCriterion("to_acct is not null");
            return (Criteria) this;
        }

        public Criteria andToAcctEqualTo(String value) {
            addCriterion("to_acct =", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctNotEqualTo(String value) {
            addCriterion("to_acct <>", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctGreaterThan(String value) {
            addCriterion("to_acct >", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctGreaterThanOrEqualTo(String value) {
            addCriterion("to_acct >=", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctLessThan(String value) {
            addCriterion("to_acct <", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctLessThanOrEqualTo(String value) {
            addCriterion("to_acct <=", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctLike(String value) {
            addCriterion("to_acct like", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctNotLike(String value) {
            addCriterion("to_acct not like", value, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctIn(List<String> values) {
            addCriterion("to_acct in", values, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctNotIn(List<String> values) {
            addCriterion("to_acct not in", values, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctBetween(String value1, String value2) {
            addCriterion("to_acct between", value1, value2, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAcctNotBetween(String value1, String value2) {
            addCriterion("to_acct not between", value1, value2, "toAcct");
            return (Criteria) this;
        }

        public Criteria andToAddrIsNull() {
            addCriterion("to_addr is null");
            return (Criteria) this;
        }

        public Criteria andToAddrIsNotNull() {
            addCriterion("to_addr is not null");
            return (Criteria) this;
        }

        public Criteria andToAddrEqualTo(String value) {
            addCriterion("to_addr =", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrNotEqualTo(String value) {
            addCriterion("to_addr <>", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrGreaterThan(String value) {
            addCriterion("to_addr >", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrGreaterThanOrEqualTo(String value) {
            addCriterion("to_addr >=", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrLessThan(String value) {
            addCriterion("to_addr <", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrLessThanOrEqualTo(String value) {
            addCriterion("to_addr <=", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrLike(String value) {
            addCriterion("to_addr like", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrNotLike(String value) {
            addCriterion("to_addr not like", value, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrIn(List<String> values) {
            addCriterion("to_addr in", values, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrNotIn(List<String> values) {
            addCriterion("to_addr not in", values, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrBetween(String value1, String value2) {
            addCriterion("to_addr between", value1, value2, "toAddr");
            return (Criteria) this;
        }

        public Criteria andToAddrNotBetween(String value1, String value2) {
            addCriterion("to_addr not between", value1, value2, "toAddr");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Long> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Long> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(Integer value) {
            addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(Integer value) {
            addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(Integer value) {
            addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(Integer value) {
            addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(Integer value) {
            addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<Integer> values) {
            addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<Integer> values) {
            addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(Integer value1, Integer value2) {
            addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(Integer value1, Integer value2) {
            addCriterion("fee not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andTrxTimeIsNull() {
            addCriterion("trx_time is null");
            return (Criteria) this;
        }

        public Criteria andTrxTimeIsNotNull() {
            addCriterion("trx_time is not null");
            return (Criteria) this;
        }

        public Criteria andTrxTimeEqualTo(Date value) {
            addCriterion("trx_time =", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeNotEqualTo(Date value) {
            addCriterion("trx_time <>", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeGreaterThan(Date value) {
            addCriterion("trx_time >", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("trx_time >=", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeLessThan(Date value) {
            addCriterion("trx_time <", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeLessThanOrEqualTo(Date value) {
            addCriterion("trx_time <=", value, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeIn(List<Date> values) {
            addCriterion("trx_time in", values, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeNotIn(List<Date> values) {
            addCriterion("trx_time not in", values, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeBetween(Date value1, Date value2) {
            addCriterion("trx_time between", value1, value2, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTimeNotBetween(Date value1, Date value2) {
            addCriterion("trx_time not between", value1, value2, "trxTime");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIsNull() {
            addCriterion("trx_type is null");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIsNotNull() {
            addCriterion("trx_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrxTypeEqualTo(Byte value) {
            addCriterion("trx_type =", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotEqualTo(Byte value) {
            addCriterion("trx_type <>", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeGreaterThan(Byte value) {
            addCriterion("trx_type >", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("trx_type >=", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeLessThan(Byte value) {
            addCriterion("trx_type <", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeLessThanOrEqualTo(Byte value) {
            addCriterion("trx_type <=", value, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeIn(List<Byte> values) {
            addCriterion("trx_type in", values, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotIn(List<Byte> values) {
            addCriterion("trx_type not in", values, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeBetween(Byte value1, Byte value2) {
            addCriterion("trx_type between", value1, value2, "trxType");
            return (Criteria) this;
        }

        public Criteria andTrxTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("trx_type not between", value1, value2, "trxType");
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