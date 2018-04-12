package com.achain.mapper;


import com.achain.domain.entity.SscContractStorage;
import com.achain.domain.entity.SscContractStorageCriteria;
import com.ms.common.mybatis.BaseMapper;
import java.util.List;

public interface SscContractStorageMapper extends BaseMapper<Integer, SscContractStorage, SscContractStorageCriteria> {
  void insertRecordBatch(List<SscContractStorage> list);

}