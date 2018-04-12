package com.achain.service;

import com.achain.domain.dto.SscStatisticsDto;
import com.achain.domain.entity.SscBlock;
import com.achain.domain.entity.SscContractConfig;
import com.achain.domain.entity.UserAddress;
import com.achain.domain.enums.ContractStatus;
import com.achain.domain.enums.TaskDealStatus;
import com.achain.domain.vo.ContractInfoPageVO;
import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * Created by qiangkz on 2017/8/1.
 */
public interface ISscBrowserService {

  /**
   * 查询总块数
   * @return
   */
  Long getBlockCount();

  /**
   * 保存指定块的基本信息
   * @param blocknum
   * @return
   */
  Map<String, JSONArray> saveActBlock(String blocknum);


  /**
   * 保存一个块上数据
   * @param map
   */
  void saveTransactions(Map<String, JSONArray> map);


  /**
   * 查询系统处理过最大块数
   * @return
   */
  Long getMaxBlockNum();

  /**
   * 统计总体数据
   * @return
   */
  SscStatisticsDto statisticsAllData();

  /**
   * 统计总体数据
   * @return
   */
  SscStatisticsDto statisticsAllDataForQuery();

  /**
   * 获取交易数不为0。切状态为1的
   * @return
   */
  List<SscBlock> getActBlockForUserInfo();


  /**
   * 计算块上数据
   * @return
   */
  void calculateActBlockForUserInfo(List<SscBlock> sscBlocks);


  /**
   * 根据状态获取块
   * @param taskDealStatus
   * @return
   */
  Map<String, JSONArray> getActBlockByStatus(TaskDealStatus taskDealStatus);

  /**
   * 根据用户地址查询账户信息
   * @param address　　用户地址
   * @return 该地址对应币种的所有信息
   */
  List<UserAddress>  listByAddress(String address);


  /**
   * 根据关键字(合约名称,合约名称关键字,合约地址),以及合约状态查询合约的信息
   * @param key 关键词
   * @param contractStatus  合约状态
   * @param page  第几页
   * @param  perPage  每页多少条
   * @param  queryType 0-按地址查询,1-按合约名称模糊查询
   * @return
   */
  ContractInfoPageVO listContractInfoByKey(String key, ContractStatus contractStatus, Integer page, Integer perPage, Integer queryType);

  /**
   * 根据状态列出所有币种的信息
   * @param contractStatus 状态
   * @return 所有符合条件的币种
   */
  ContractInfoPageVO listAllForeverCoinType(ContractStatus contractStatus);


  /**
   * 根据合约id查询合约相关的url
   * @param contractId 合约id
   * @return url的集合
   */
  List<SscContractConfig> listUrlsByContractId(String contractId);

}
