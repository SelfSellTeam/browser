package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SscTransactionAddrDto implements Serializable{

    private Long blockNum;

    private String address;

    private String contractId;

    private String subAddress;

    private Integer pageSize;

    private Integer offSet;

    private String coinType;
}
