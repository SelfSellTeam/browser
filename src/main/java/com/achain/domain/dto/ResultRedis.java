package com.achain.domain.dto;



import com.achain.domain.enums.ResultRedisStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by qiangkz on 2017/2/22.
 */
@Data
public class ResultRedis implements Serializable {

  private Boolean result;

  private Long data;

  private ResultRedisStatus resultRedisStatus;

  public static final class ResultRedisBuilder {
    private Boolean result;
    private Long data;
    private ResultRedisStatus resultRedisStatus;

    private ResultRedisBuilder() {
    }

    public static ResultRedisBuilder aResultRedis() {
      return new ResultRedisBuilder();
    }

    public ResultRedisBuilder result(Boolean result) {
      this.result = result;
      return this;
    }

    public ResultRedisBuilder data(Long data) {
      this.data = data;
      return this;
    }

    public ResultRedisBuilder resultRedisStatus(ResultRedisStatus resultRedisStatus) {
      this.resultRedisStatus = resultRedisStatus;
      return this;
    }

    public ResultRedis build() {
      ResultRedis resultRedis = new ResultRedis();
      resultRedis.result = this.result;
      resultRedis.data = this.data;
      resultRedis.resultRedisStatus = this.resultRedisStatus;
      return resultRedis;
    }
  }
}
