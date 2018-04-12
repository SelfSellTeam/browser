package com.achain.domain.enums;

import com.ms.common.base.BaseEnum;

/**
 * 信息来源
 *
 * @author qiangkz
 */
public enum RedisWalletDataConstants implements BaseEnum {


  WALLET_STATISTICS_ALL_TASK(0, "统计任务前缀"),

  WALLET_BLOCK_ALL_DATA(1, "主页区块缓存"),

  WALLET_TRANSACTION_ALL_DATA(2, "主页交易缓存"),
;


  private final int key;

  private final String desc;

  RedisWalletDataConstants(int key) {
    this.key = key;
    this.desc = "";
  }

  RedisWalletDataConstants(int key, String desc) {
    this.key = key;
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
}
