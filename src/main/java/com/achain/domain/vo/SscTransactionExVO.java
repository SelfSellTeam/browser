package com.achain.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yujianjian
 * @since 2017-11-21 上午11:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SscTransactionExVO {

    private String trxId;

    private String fromAddr;

    private String toAddr;

    private String amount;


}
