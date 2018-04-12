package com.achain.domain.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString(callSuper=true)
public class SscTransactionEx implements Serializable {
    /**唯一id*/
    protected Long id;

    /**结果交易id*/
    protected String trxId;

    /**原始交易id*/
    protected String origTrxId;

    /**发起账户*/
    protected String fromAcct;

    /**发起地址*/
    protected String fromAddr;

    /**接收账户*/
    protected String toAcct;

    /**接收地址*/
    protected String toAddr;

    /**金额*/
    protected Long amount;

    /**手续费*/
    protected Integer fee;

    /**备注*/
    protected String memo;

    /***/
    protected Date trxTime;

    /***/
    protected Byte trxType;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}