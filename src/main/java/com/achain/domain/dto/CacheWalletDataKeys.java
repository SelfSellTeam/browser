package com.achain.domain.dto;


import com.achain.domain.enums.RedisWalletDataConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by qiangkz on 2017/2/23.
 */
public class CacheWalletDataKeys {


  private static final String separator = ":";

  /**
   * 获取处理key
   */
  public static String getWalletDataLockRedisKey(String uniqueKey) {
    return new StringBuilder().append(RedisWalletDataConstants.WALLET_STATISTICS_ALL_TASK.name()).append(separator)
                              .append(StringUtils.stripToEmpty(uniqueKey)).toString();
  }

  public static String getWalletBlockRedisKey(String uniqueKey) {
    return new StringBuilder().append(RedisWalletDataConstants.WALLET_BLOCK_ALL_DATA.name()).append(separator)
                              .append(StringUtils.stripToEmpty(uniqueKey)).toString();
  }

  public static String getWalletTransactionRedisKey(String uniqueKey) {
    return new StringBuilder().append(RedisWalletDataConstants.WALLET_TRANSACTION_ALL_DATA.name()).append(separator)
                              .append(StringUtils.stripToEmpty(uniqueKey)).toString();
  }
}
