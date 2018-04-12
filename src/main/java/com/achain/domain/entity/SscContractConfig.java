package com.achain.domain.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString(callSuper=true)
public class SscContractConfig implements Serializable {
    /***/
    protected Integer id;

    /**合约id*/
    protected String contractId;

    /**合约名称*/
    protected String contractName;

    /**url代表的含义*/
    protected Byte urlIndex;

    /**url地址*/
    protected String url;

    /**url名称*/
    protected String urlName;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}