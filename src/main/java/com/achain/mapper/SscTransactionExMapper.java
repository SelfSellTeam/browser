package com.achain.mapper;


import com.achain.domain.entity.SscTransactionEx;
import com.achain.domain.entity.SscTransactionExCriteria;
import com.ms.common.mybatis.BaseMapper;

import java.util.List;

public interface SscTransactionExMapper extends BaseMapper<Long, SscTransactionEx, SscTransactionExCriteria> {

  void insertRecordBatch(List<SscTransactionEx> list);
}