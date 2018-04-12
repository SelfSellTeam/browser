package com.achain.mapper;


import com.achain.domain.entity.SscContractAbi;
import com.achain.domain.entity.SscContractAbiCriteria;
import com.ms.common.mybatis.BaseMapper;
import java.util.List;

public interface SscContractAbiMapper extends BaseMapper<Integer, SscContractAbi, SscContractAbiCriteria> {
  void insertRecordBatch(List<SscContractAbi> list);
}