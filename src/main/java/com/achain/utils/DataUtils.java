package com.achain.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by qiangkz on 2017/8/7.
 */
public class DataUtils {

  public static String getActualAmount(String amount) {
    return new BigDecimal(StringUtils.isEmpty(amount) ? "0" : amount).divide(new BigDecimal(100000))
                                                                     .setScale(5, BigDecimal.ROUND_HALF_UP).toString();
  }
}
