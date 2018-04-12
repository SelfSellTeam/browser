package com.achain.domain.entity;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
public class SscStatTotal implements Serializable {
    /**唯一id*/
    protected Long id;

    /**统计项名称
     totalTransNum：总的交易数量
     totalTransAmount： 总的交易金额
     totalAcctNum： 总的账户数*/
    protected String totalStatName;

    /***/
    protected Long totalStatValue;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}