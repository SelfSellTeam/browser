package com.achain.domain.enums;

import com.ms.common.base.BaseEnum;

import java.util.Arrays;

/**
 * Created by qiangkz on 2017/8/23.
 */
public enum ContractStatus implements BaseEnum {


  COIN_TRANSFER_DESTROY(0, "temp", "合约销毁"),

  CONTRACT_STATUS_TEMP(1, "temp", "临时合约"),

  COIN_TRANSFER_FOREVER(2, "forever", "永久合约"),;


  private final int key;
  private final String value;
  private final String desc;

  ContractStatus(int key, String value, String desc) {
    this.key = key;
    this.value = value;
    this.desc = desc;
  }

  @Override
  public int getIntKey() {
    return key;
  }

  @Override
  public String getDesc() {
    return desc;
  }

  public String getValue() {
    return value;
  }


  public static ContractStatus getContractStatus(String value){
    return Arrays.stream(ContractStatus.values()).filter(contractStatus -> contractStatus.value.equals(value)).findFirst().orElse(CONTRACT_STATUS_TEMP);
  }

}
