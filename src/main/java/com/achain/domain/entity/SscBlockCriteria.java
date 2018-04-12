package com.achain.domain.entity;

import com.achain.domain.enums.TaskDealStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SscBlockCriteria implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offSet;

    protected Integer pageSize;

    private static final long serialVersionUID = 1L;

    public SscBlockCriteria() {
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
        protected List<Criterion> statusCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        private static final long serialVersionUID = 1L;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            statusCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getStatusCriteria() {
            return statusCriteria;
        }

        protected void addStatusCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            statusCriteria.add(new Criterion(condition, value, "com.ms.common.mybatis.typehandler.NullableEnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        protected void addStatusCriterion(String condition, TaskDealStatus value1, TaskDealStatus value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            statusCriteria.add(new Criterion(condition, value1, value2, "com.ms.common.mybatis.typehandler.NullableEnumOrdinalTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || statusCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(statusCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
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

        public Criteria andBlockIdIsNull() {
            addCriterion("block_id is null");
            return (Criteria) this;
        }

        public Criteria andBlockIdIsNotNull() {
            addCriterion("block_id is not null");
            return (Criteria) this;
        }

        public Criteria andBlockIdEqualTo(String value) {
            addCriterion("block_id =", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotEqualTo(String value) {
            addCriterion("block_id <>", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdGreaterThan(String value) {
            addCriterion("block_id >", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdGreaterThanOrEqualTo(String value) {
            addCriterion("block_id >=", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLessThan(String value) {
            addCriterion("block_id <", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLessThanOrEqualTo(String value) {
            addCriterion("block_id <=", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdLike(String value) {
            addCriterion("block_id like", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotLike(String value) {
            addCriterion("block_id not like", value, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdIn(List<String> values) {
            addCriterion("block_id in", values, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotIn(List<String> values) {
            addCriterion("block_id not in", values, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdBetween(String value1, String value2) {
            addCriterion("block_id between", value1, value2, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockIdNotBetween(String value1, String value2) {
            addCriterion("block_id not between", value1, value2, "blockId");
            return (Criteria) this;
        }

        public Criteria andBlockNumIsNull() {
            addCriterion("block_num is null");
            return (Criteria) this;
        }

        public Criteria andBlockNumIsNotNull() {
            addCriterion("block_num is not null");
            return (Criteria) this;
        }

        public Criteria andBlockNumEqualTo(Long value) {
            addCriterion("block_num =", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumNotEqualTo(Long value) {
            addCriterion("block_num <>", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumGreaterThan(Long value) {
            addCriterion("block_num >", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumGreaterThanOrEqualTo(Long value) {
            addCriterion("block_num >=", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumLessThan(Long value) {
            addCriterion("block_num <", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumLessThanOrEqualTo(Long value) {
            addCriterion("block_num <=", value, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumIn(List<Long> values) {
            addCriterion("block_num in", values, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumNotIn(List<Long> values) {
            addCriterion("block_num not in", values, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumBetween(Long value1, Long value2) {
            addCriterion("block_num between", value1, value2, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockNumNotBetween(Long value1, Long value2) {
            addCriterion("block_num not between", value1, value2, "blockNum");
            return (Criteria) this;
        }

        public Criteria andBlockSizeIsNull() {
            addCriterion("block_size is null");
            return (Criteria) this;
        }

        public Criteria andBlockSizeIsNotNull() {
            addCriterion("block_size is not null");
            return (Criteria) this;
        }

        public Criteria andBlockSizeEqualTo(Long value) {
            addCriterion("block_size =", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeNotEqualTo(Long value) {
            addCriterion("block_size <>", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeGreaterThan(Long value) {
            addCriterion("block_size >", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("block_size >=", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeLessThan(Long value) {
            addCriterion("block_size <", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeLessThanOrEqualTo(Long value) {
            addCriterion("block_size <=", value, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeIn(List<Long> values) {
            addCriterion("block_size in", values, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeNotIn(List<Long> values) {
            addCriterion("block_size not in", values, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeBetween(Long value1, Long value2) {
            addCriterion("block_size between", value1, value2, "blockSize");
            return (Criteria) this;
        }

        public Criteria andBlockSizeNotBetween(Long value1, Long value2) {
            addCriterion("block_size not between", value1, value2, "blockSize");
            return (Criteria) this;
        }

        public Criteria andPreviousIsNull() {
            addCriterion("previous is null");
            return (Criteria) this;
        }

        public Criteria andPreviousIsNotNull() {
            addCriterion("previous is not null");
            return (Criteria) this;
        }

        public Criteria andPreviousEqualTo(String value) {
            addCriterion("previous =", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotEqualTo(String value) {
            addCriterion("previous <>", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousGreaterThan(String value) {
            addCriterion("previous >", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousGreaterThanOrEqualTo(String value) {
            addCriterion("previous >=", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousLessThan(String value) {
            addCriterion("previous <", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousLessThanOrEqualTo(String value) {
            addCriterion("previous <=", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousLike(String value) {
            addCriterion("previous like", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotLike(String value) {
            addCriterion("previous not like", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousIn(List<String> values) {
            addCriterion("previous in", values, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotIn(List<String> values) {
            addCriterion("previous not in", values, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousBetween(String value1, String value2) {
            addCriterion("previous between", value1, value2, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotBetween(String value1, String value2) {
            addCriterion("previous not between", value1, value2, "previous");
            return (Criteria) this;
        }

        public Criteria andTrxDigestIsNull() {
            addCriterion("trx_digest is null");
            return (Criteria) this;
        }

        public Criteria andTrxDigestIsNotNull() {
            addCriterion("trx_digest is not null");
            return (Criteria) this;
        }

        public Criteria andTrxDigestEqualTo(String value) {
            addCriterion("trx_digest =", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestNotEqualTo(String value) {
            addCriterion("trx_digest <>", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestGreaterThan(String value) {
            addCriterion("trx_digest >", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestGreaterThanOrEqualTo(String value) {
            addCriterion("trx_digest >=", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestLessThan(String value) {
            addCriterion("trx_digest <", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestLessThanOrEqualTo(String value) {
            addCriterion("trx_digest <=", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestLike(String value) {
            addCriterion("trx_digest like", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestNotLike(String value) {
            addCriterion("trx_digest not like", value, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestIn(List<String> values) {
            addCriterion("trx_digest in", values, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestNotIn(List<String> values) {
            addCriterion("trx_digest not in", values, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestBetween(String value1, String value2) {
            addCriterion("trx_digest between", value1, value2, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andTrxDigestNotBetween(String value1, String value2) {
            addCriterion("trx_digest not between", value1, value2, "trxDigest");
            return (Criteria) this;
        }

        public Criteria andPrevSecretIsNull() {
            addCriterion("prev_secret is null");
            return (Criteria) this;
        }

        public Criteria andPrevSecretIsNotNull() {
            addCriterion("prev_secret is not null");
            return (Criteria) this;
        }

        public Criteria andPrevSecretEqualTo(String value) {
            addCriterion("prev_secret =", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretNotEqualTo(String value) {
            addCriterion("prev_secret <>", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretGreaterThan(String value) {
            addCriterion("prev_secret >", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretGreaterThanOrEqualTo(String value) {
            addCriterion("prev_secret >=", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretLessThan(String value) {
            addCriterion("prev_secret <", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretLessThanOrEqualTo(String value) {
            addCriterion("prev_secret <=", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretLike(String value) {
            addCriterion("prev_secret like", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretNotLike(String value) {
            addCriterion("prev_secret not like", value, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretIn(List<String> values) {
            addCriterion("prev_secret in", values, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretNotIn(List<String> values) {
            addCriterion("prev_secret not in", values, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretBetween(String value1, String value2) {
            addCriterion("prev_secret between", value1, value2, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andPrevSecretNotBetween(String value1, String value2) {
            addCriterion("prev_secret not between", value1, value2, "prevSecret");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashIsNull() {
            addCriterion("next_secret_hash is null");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashIsNotNull() {
            addCriterion("next_secret_hash is not null");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashEqualTo(String value) {
            addCriterion("next_secret_hash =", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashNotEqualTo(String value) {
            addCriterion("next_secret_hash <>", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashGreaterThan(String value) {
            addCriterion("next_secret_hash >", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashGreaterThanOrEqualTo(String value) {
            addCriterion("next_secret_hash >=", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashLessThan(String value) {
            addCriterion("next_secret_hash <", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashLessThanOrEqualTo(String value) {
            addCriterion("next_secret_hash <=", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashLike(String value) {
            addCriterion("next_secret_hash like", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashNotLike(String value) {
            addCriterion("next_secret_hash not like", value, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashIn(List<String> values) {
            addCriterion("next_secret_hash in", values, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashNotIn(List<String> values) {
            addCriterion("next_secret_hash not in", values, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashBetween(String value1, String value2) {
            addCriterion("next_secret_hash between", value1, value2, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andNextSecretHashNotBetween(String value1, String value2) {
            addCriterion("next_secret_hash not between", value1, value2, "nextSecretHash");
            return (Criteria) this;
        }

        public Criteria andRandomSeedIsNull() {
            addCriterion("random_seed is null");
            return (Criteria) this;
        }

        public Criteria andRandomSeedIsNotNull() {
            addCriterion("random_seed is not null");
            return (Criteria) this;
        }

        public Criteria andRandomSeedEqualTo(String value) {
            addCriterion("random_seed =", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedNotEqualTo(String value) {
            addCriterion("random_seed <>", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedGreaterThan(String value) {
            addCriterion("random_seed >", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedGreaterThanOrEqualTo(String value) {
            addCriterion("random_seed >=", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedLessThan(String value) {
            addCriterion("random_seed <", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedLessThanOrEqualTo(String value) {
            addCriterion("random_seed <=", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedLike(String value) {
            addCriterion("random_seed like", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedNotLike(String value) {
            addCriterion("random_seed not like", value, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedIn(List<String> values) {
            addCriterion("random_seed in", values, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedNotIn(List<String> values) {
            addCriterion("random_seed not in", values, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedBetween(String value1, String value2) {
            addCriterion("random_seed between", value1, value2, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andRandomSeedNotBetween(String value1, String value2) {
            addCriterion("random_seed not between", value1, value2, "randomSeed");
            return (Criteria) this;
        }

        public Criteria andSigneeIsNull() {
            addCriterion("signee is null");
            return (Criteria) this;
        }

        public Criteria andSigneeIsNotNull() {
            addCriterion("signee is not null");
            return (Criteria) this;
        }

        public Criteria andSigneeEqualTo(String value) {
            addCriterion("signee =", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeNotEqualTo(String value) {
            addCriterion("signee <>", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeGreaterThan(String value) {
            addCriterion("signee >", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeGreaterThanOrEqualTo(String value) {
            addCriterion("signee >=", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeLessThan(String value) {
            addCriterion("signee <", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeLessThanOrEqualTo(String value) {
            addCriterion("signee <=", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeLike(String value) {
            addCriterion("signee like", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeNotLike(String value) {
            addCriterion("signee not like", value, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeIn(List<String> values) {
            addCriterion("signee in", values, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeNotIn(List<String> values) {
            addCriterion("signee not in", values, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeBetween(String value1, String value2) {
            addCriterion("signee between", value1, value2, "signee");
            return (Criteria) this;
        }

        public Criteria andSigneeNotBetween(String value1, String value2) {
            addCriterion("signee not between", value1, value2, "signee");
            return (Criteria) this;
        }

        public Criteria andBlockTimeIsNull() {
            addCriterion("block_time is null");
            return (Criteria) this;
        }

        public Criteria andBlockTimeIsNotNull() {
            addCriterion("block_time is not null");
            return (Criteria) this;
        }

        public Criteria andBlockTimeEqualTo(Date value) {
            addCriterion("block_time =", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeNotEqualTo(Date value) {
            addCriterion("block_time <>", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeGreaterThan(Date value) {
            addCriterion("block_time >", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("block_time >=", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeLessThan(Date value) {
            addCriterion("block_time <", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeLessThanOrEqualTo(Date value) {
            addCriterion("block_time <=", value, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeIn(List<Date> values) {
            addCriterion("block_time in", values, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeNotIn(List<Date> values) {
            addCriterion("block_time not in", values, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeBetween(Date value1, Date value2) {
            addCriterion("block_time between", value1, value2, "blockTime");
            return (Criteria) this;
        }

        public Criteria andBlockTimeNotBetween(Date value1, Date value2) {
            addCriterion("block_time not between", value1, value2, "blockTime");
            return (Criteria) this;
        }

        public Criteria andTransNumIsNull() {
            addCriterion("trans_num is null");
            return (Criteria) this;
        }

        public Criteria andTransNumIsNotNull() {
            addCriterion("trans_num is not null");
            return (Criteria) this;
        }

        public Criteria andTransNumEqualTo(Integer value) {
            addCriterion("trans_num =", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumNotEqualTo(Integer value) {
            addCriterion("trans_num <>", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumGreaterThan(Integer value) {
            addCriterion("trans_num >", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("trans_num >=", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumLessThan(Integer value) {
            addCriterion("trans_num <", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumLessThanOrEqualTo(Integer value) {
            addCriterion("trans_num <=", value, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumIn(List<Integer> values) {
            addCriterion("trans_num in", values, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumNotIn(List<Integer> values) {
            addCriterion("trans_num not in", values, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumBetween(Integer value1, Integer value2) {
            addCriterion("trans_num between", value1, value2, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransNumNotBetween(Integer value1, Integer value2) {
            addCriterion("trans_num not between", value1, value2, "transNum");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNull() {
            addCriterion("trans_amount is null");
            return (Criteria) this;
        }

        public Criteria andTransAmountIsNotNull() {
            addCriterion("trans_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTransAmountEqualTo(Long value) {
            addCriterion("trans_amount =", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotEqualTo(Long value) {
            addCriterion("trans_amount <>", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThan(Long value) {
            addCriterion("trans_amount >", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("trans_amount >=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThan(Long value) {
            addCriterion("trans_amount <", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountLessThanOrEqualTo(Long value) {
            addCriterion("trans_amount <=", value, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountIn(List<Long> values) {
            addCriterion("trans_amount in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotIn(List<Long> values) {
            addCriterion("trans_amount not in", values, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountBetween(Long value1, Long value2) {
            addCriterion("trans_amount between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransAmountNotBetween(Long value1, Long value2) {
            addCriterion("trans_amount not between", value1, value2, "transAmount");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNull() {
            addCriterion("trans_fee is null");
            return (Criteria) this;
        }

        public Criteria andTransFeeIsNotNull() {
            addCriterion("trans_fee is not null");
            return (Criteria) this;
        }

        public Criteria andTransFeeEqualTo(Long value) {
            addCriterion("trans_fee =", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotEqualTo(Long value) {
            addCriterion("trans_fee <>", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThan(Long value) {
            addCriterion("trans_fee >", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeGreaterThanOrEqualTo(Long value) {
            addCriterion("trans_fee >=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThan(Long value) {
            addCriterion("trans_fee <", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeLessThanOrEqualTo(Long value) {
            addCriterion("trans_fee <=", value, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeIn(List<Long> values) {
            addCriterion("trans_fee in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotIn(List<Long> values) {
            addCriterion("trans_fee not in", values, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeBetween(Long value1, Long value2) {
            addCriterion("trans_fee between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andTransFeeNotBetween(Long value1, Long value2) {
            addCriterion("trans_fee not between", value1, value2, "transFee");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(TaskDealStatus value) {
            addStatusCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(TaskDealStatus value) {
            addStatusCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(TaskDealStatus value) {
            addStatusCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(TaskDealStatus value) {
            addStatusCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(TaskDealStatus value) {
            addStatusCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(TaskDealStatus value) {
            addStatusCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<TaskDealStatus> values) {
            addStatusCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<TaskDealStatus> values) {
            addStatusCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(TaskDealStatus value1, TaskDealStatus value2) {
            addStatusCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(TaskDealStatus value1, TaskDealStatus value2) {
            addStatusCriterion("status not between", value1, value2, "status");
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