package com.achain.domain.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString(callSuper=true)
public class SscContractEvent implements Serializable {
    /**唯一id*/
    protected Integer id;

    /**合约id*/
    protected String contractId;

    /**事件名称*/
    protected String event;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}