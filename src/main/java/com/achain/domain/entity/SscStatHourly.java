package com.achain.domain.entity;


import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
public class SscStatHourly implements Serializable {
    /**唯一id*/
    protected Integer id;

    /**统计数据所在小时*/
    protected Date statTime;

    /**交易数量*/
    protected Integer transactionNumber;

    /**交易金额*/
    protected Long amount;

    /**手续费*/
    protected Long fee;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}