package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qiangkz on 2017/8/4.
 */
@Data
public class SscStatisticsParamDto implements Serializable {

  private Date trxTime;

  private String coinType;

  private Date startTrxTime;

  private Date endTrxTime;

  private String address;


  public static final class Builder {
    private Date trxTime;
    private String coinType;
    private Date startTrxTime;
    private Date endTrxTime;
    private String address;

    private Builder() {
    }

    public static Builder anActStatisticsParamDto() {
      return new Builder();
    }

    public Builder trxTime(Date trxTime) {
      this.trxTime = trxTime;
      return this;
    }

    public Builder coinType(String coinType) {
      this.coinType = coinType;
      return this;
    }

    public Builder startTrxTime(Date startTrxTime) {
      this.startTrxTime = startTrxTime;
      return this;
    }

    public Builder endTrxTime(Date endTrxTime) {
      this.endTrxTime = endTrxTime;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public SscStatisticsParamDto build() {
      SscStatisticsParamDto sscStatisticsParamDto = new SscStatisticsParamDto();
      sscStatisticsParamDto.setTrxTime(trxTime);
      sscStatisticsParamDto.setCoinType(coinType);
      sscStatisticsParamDto.setStartTrxTime(startTrxTime);
      sscStatisticsParamDto.setEndTrxTime(endTrxTime);
      sscStatisticsParamDto.setAddress(address);
      return sscStatisticsParamDto;
    }
  }
}
