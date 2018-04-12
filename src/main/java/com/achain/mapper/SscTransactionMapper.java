package com.achain.mapper;

import com.achain.domain.dto.SscStatisticsDto;
import com.achain.domain.dto.SscStatisticsParamDto;
import com.achain.domain.dto.SscTransactionAddrDto;
import com.achain.domain.dto.SscTransactionForSubDto;
import com.achain.domain.entity.SscTransaction;
import com.achain.domain.entity.SscTransactionCriteria;
import com.ms.common.mybatis.BaseMapper;

import java.util.List;

public interface SscTransactionMapper extends BaseMapper<Long, SscTransaction, SscTransactionCriteria> {

    void insertRecordBatch(List<SscTransaction> list);

    SscStatisticsDto statisticsAllData(SscStatisticsParamDto sscStatisticsParamDto);

    List<SscTransactionForSubDto> statisticsFromSub(SscStatisticsParamDto sscStatisticsParamDto);

    Long countBySubAddress();

    List<SscTransaction> selectByExampleFromAddr(SscTransactionAddrDto sscTransactionAddrDto);

    int countByExampleFromAddr(SscTransactionAddrDto sscTransactionAddrDto);


    List<SscTransaction> selectByExampleFromAddrAndCoinType(SscTransactionAddrDto sscTransactionAddrDto);

    int countByExampleFromAddrAndCoinType(SscTransactionAddrDto sscTransactionAddrDto);
}