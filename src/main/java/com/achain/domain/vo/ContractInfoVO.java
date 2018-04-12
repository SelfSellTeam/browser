package com.achain.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author yujianjian
 * @since 2017-11-06 上午10:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfoVO {


    /**
     * 合约名称
     */
    private String contractName;

    /**
     * 合约id
     */
    private String contractId;

    /**
     * 状态
     */
    private String coinType;

    /**
     * 类型
     */
    private Integer status;

    /**
     * 合约地址
     */
    private String coinAddress;

    /**
     * 发行量
     */
    private BigDecimal circulation;

    /**
     * 注册时间
     */
    private String registerTime;

    /**
     * 是不是coin
     */
    private Integer coin;

}
