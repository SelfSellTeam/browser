package com.achain.domain.entity;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
public class SscAccount implements Serializable {
    /***/
    protected Integer id;

    /***/
    protected String walletName;

    /***/
    protected String accountName;

    /***/
    protected String userAddress;

    /***/
    protected String publicKey;

    /***/
    protected String offlineWallet;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}