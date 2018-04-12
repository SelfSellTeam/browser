package com.achain.domain.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class SscContractInfo implements Serializable {

  /**唯一id*/
  protected Integer id;

  /**合约ID
   */
  protected String contractId;

  /**合约名称*/
  protected String name;

  /***/
  protected String coinType;

  /**字节码*/
  protected String bytecode;

  /**字节码hash*/
  protected String hash;

  /**合约拥有者公钥*/
  protected String owner;

  /**合约拥有者地址*/
  protected String ownerAddress;

  /**合约拥有者名称*/
  protected String ownerName;

  /**0是其他 1是资产*/
  protected Byte type;

  /**合约描述*/
  protected String description;

  /**注册时间*/
  protected Date regTime;

  /**注册合约的交易id*/
  protected String regTrxId;

  /***/
  protected Long balance;

  /**合约币发行总量*/
  protected Long circulation;

  /**注册状态
   0 - 销毁
   1 - 临时
   2 - 永久*/
  protected Integer status;

  /***/
  protected Date createTime;

  /***/
  protected Date updateTime;

  private static final long serialVersionUID = 1L;

  private List<SscContractAbi> sscContractAbis;

  private List<SscContractEvent> sscContractEvents;

  private List<SscContractStorage> sscContractStorages;
}