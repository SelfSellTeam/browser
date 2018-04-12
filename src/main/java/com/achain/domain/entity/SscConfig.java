package com.achain.domain.entity;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString(callSuper=true)
public class SscConfig implements Serializable {
    /**唯一id*/
    protected Integer id;

    /**配置关键字*/
    protected String confKey;

    /**配置值*/
    protected String confValue;

    /**存放使用该配置的模块名*/
    protected String module;

    /***/
    protected Date createTime;

    /***/
    protected Date updateTime;

    private static final long serialVersionUID = 1L;
}