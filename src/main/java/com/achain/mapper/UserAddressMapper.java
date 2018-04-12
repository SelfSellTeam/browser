package com.achain.mapper;


import com.achain.domain.entity.UserAddress;
import com.achain.domain.entity.UserAddressCriteria;
import com.ms.common.mybatis.BaseMapper;

import java.util.List;

public interface UserAddressMapper extends BaseMapper<Integer, UserAddress, UserAddressCriteria> {

  void insertRecordBatch(List<UserAddress> list);

  int countByExampleDistinct(UserAddressCriteria userAddressCriteria);

}