package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SscTransactionForSubDto implements Serializable{

    private String subAddr;

    private Long transNum;

    private String transAmount;

}
