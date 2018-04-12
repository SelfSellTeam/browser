package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SscStatisticsDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long transNum;

    private String transAmount;

    private Long accountNum;

    private Long contractNum;

    private Long transactionHourly;

    private Long transactionPeak;
}
