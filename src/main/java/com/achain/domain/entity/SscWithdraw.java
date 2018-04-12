package com.achain.domain.entity;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
public class SscWithdraw implements Serializable {
    /***/
    protected Long id;

    /**交易id*/
    protected String trxId;

    /**钱包名*/
    protected String walletName;

    /**资产类型*/
    protected String assetSymbol;

    /**发起账号*/
    protected String fromAcct;

    /**发起地址*/
    protected String fromAddr;

    /**接收账号*/
    protected String toAcct;

    /**接收地址*/
    protected String toAddr;

    /**金额*/
    protected String amount;

    /**备注*/
    protected String memo;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}