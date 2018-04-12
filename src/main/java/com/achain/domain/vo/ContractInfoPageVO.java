package com.achain.domain.vo;

import com.achain.domain.entity.SscContractInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yujianjian
 * @since 2017-11-08 下午3:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfoPageVO {

    List<SscContractInfo> sscContractInfoList;

    List<ContractInfoVO> contractInfoVOList;
    /**
     * 当前是第几页
     */
    Integer currentPage;
    /**
     * 每页多少条
     */
    Integer pageSize;
    /**
     * 总共多少页
     */
    Integer totalPage;
    /**
     * 总共多少条
     */
    Integer totalRecords;
}
