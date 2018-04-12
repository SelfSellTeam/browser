package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by qiangkz on 2017/8/2.
 */
@Data
public class DataDealDto implements Serializable {

  private String data;

  private String extData;
}
