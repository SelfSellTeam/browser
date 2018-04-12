package com.achain.domain.enums;



import com.ms.common.base.BaseEnum;

import java.util.Arrays;

/**
 * 奖券审核枚举
 *
 * @author qiangkezhen
 */
public enum ResultRedisStatus implements BaseEnum {

  APPLY_NO_STOCK(-1, "库存为0"),

  APPLY_NO_EXIST(-2, "资源库存key不存在"),

  INIT_FAIL(-3, "资源初始化失败"),

  APPLY_SUCCESS(0, "申请资源成功"),

  INIT_SUCCESS(1, "资源初始化成功"),

  CAPTCHA_APPLY_NEED_IMG(2, "需要图片验证码"),

  CAPTCHA_APPLY_LIMITED(3, "验证码频繁申请"),;

  private final int key;

  private final String desc;

  ResultRedisStatus(int key) {
    this.key = key;
    this.desc = "";
  }

  ResultRedisStatus(int key, String desc) {
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


  public static ResultRedisStatus getEnum(Long key) {
    return Arrays.stream(ResultRedisStatus.values()).filter(temp -> temp.getIntKey() == key).findAny().orElse(null);
  }

}
