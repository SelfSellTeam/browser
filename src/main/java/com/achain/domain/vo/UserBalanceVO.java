package com.achain.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author yujianjian
 * @since 2017-11-06 上午10:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceVO {

    /**
     * 币种类型
     */
    private String coinType;

    /**
     * 余额
     */
    private BigDecimal balance;

}
