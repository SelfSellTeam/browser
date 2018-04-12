package com.achain.mapper;


import com.achain.domain.entity.SscContractEvent;
import com.achain.domain.entity.SscContractEventCriteria;
import com.ms.common.mybatis.BaseMapper;
import java.util.List;

public interface SscContractEventMapper extends BaseMapper<Integer, SscContractEvent, SscContractEventCriteria> {

  void insertRecordBatch(List<SscContractEvent> list);

}