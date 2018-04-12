package com.achain.domain.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
@Data
public class UserAddress implements Serializable {
    /***/
    protected Integer id;

    /***/
    protected String accountName;

    /***/
    protected String userAddress;

    /***/
    protected Long balance;

    /***/
    protected String coinType;

    /***/
    protected String contractId;

    /**交易笔数*/
    protected Integer transNum;

    /***/
    protected Date lastTrxTime;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;

    private Long version;

    private Long trxTime;
}