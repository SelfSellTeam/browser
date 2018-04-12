package com.achain.service.impl;

import com.achain.domain.dto.SscStatisticsDto;
import com.achain.mapper.*;
import com.achain.domain.dto.SscStatisticsParamDto;
import com.achain.domain.entity.*;
import com.achain.domain.enums.*;
import com.achain.domain.vo.ContractInfoPageVO;
import com.achain.service.ISscBrowserService;
import com.achain.utils.DataUtils;
import com.achain.utils.SDKHttpClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qiangkz on 2017/8/1.
 */
@Service
public class SscBrowserServiceImpl implements ISscBrowserService {

  private static Logger logger = LoggerFactory.getLogger(SscBrowserServiceImpl.class);

  @Autowired
  private SscBlockMapper sscBlockMapper;

  @Autowired
  private SscTransactionMapper sscTransactionMapper;

  @Autowired
  private SscTransactionExMapper sscTransactionExMapper;

  @Autowired
  private UserAddressMapper userAddressMapper;

  @Autowired
  private SDKHttpClient sdkHttpClient;

  @Value("${wallet_name}")
  private String walletName;

  @Value("${wallet_rpc_url}")
  private String walletRpcUrl;

  @Value("${wallet_rpc_userauth}")
  private String walletRpcUserauth;

  @Autowired
  private SscContractInfoMapper sscContractInfoMapper;

  @Autowired
  private RedisTemplate<String, SscStatisticsDto> redisTemplate;

  @Autowired
  private SscContractAbiMapper sscContractAbiMapper;

  @Autowired
  private SscContractEventMapper sscContractEventMapper;

  @Autowired
  private SscContractStorageMapper sscContractStorageMapper;

  @Autowired
  private SscContractConfigMapper sscContractConfigMapper;

  @Override
  public Map<String, JSONArray> saveActBlock(String blocknum) {
    JSONArray jsonArray = new JSONArray();
    Map<String, JSONArray> map = new HashMap<>();
    try {
      jsonArray.add(blocknum);
      String result = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_block", jsonArray);
      String resultSignee = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_block_signee", jsonArray);
      if (StringUtils.isEmpty(result) || StringUtils.isEmpty(resultSignee)) {
        return null;
      }
      JSONObject createTaskJson = JSONObject.parseObject(result);
      createTaskJson = createTaskJson.getJSONObject("result");
      JSONObject resultSigneeJ = JSONObject.parseObject(resultSignee);
      SscBlock sscBlock = new SscBlock();
      sscBlock.setSignee(resultSigneeJ.getString("result"));
      sscBlock.setBlockId(createTaskJson.getString("id"));
      sscBlock.setBlockNum(createTaskJson.getLong("block_num"));
      sscBlock.setBlockSize(createTaskJson.getLong("block_size"));
      sscBlock.setBlockTime(dealTime(createTaskJson.getString("timestamp")));
      sscBlock.setNextSecretHash(createTaskJson.getString("next_secret_hash"));
      sscBlock.setPrevious(createTaskJson.getString("previous"));
      sscBlock.setPrevSecret(createTaskJson.getString("previous_secret"));
      sscBlock.setRandomSeed(createTaskJson.getString("random_seed"));
      jsonArray = JSONObject.parseArray(createTaskJson.getString("user_transaction_ids"));
      map.put(sscBlock.getBlockId(), jsonArray);
      sscBlock.setTransNum(JSONObject.parseArray(createTaskJson.getString("user_transaction_ids")).size());
      sscBlock.setTrxDigest(createTaskJson.getString("transaction_digest"));
      sscBlock.setTransFee(
          createTaskJson.getLong("signee_shares_issued") + createTaskJson.getLong("signee_fees_collected"));
      sscBlock.setTransAmount(0L);
      sscBlock.setStatus(TaskDealStatus.TASK_INI);
      sscBlockMapper.insert(sscBlock);
    } catch (Exception er) {
      logger.error("SscBrowserServiceImpl|saveActBlock|[blocknum={}]出现异常", blocknum, er);
      return null;
    }
    return map;
  }


  @Override
  public Long getBlockCount() {
    JSONObject jsonObject = new JSONObject();
    String method = "blockchain_get_block_count";
    JSONArray jsonArray = new JSONArray();
    jsonObject.put("method", method);
    jsonObject.put("params", jsonArray);
    String result = dataDeal(jsonObject);
    JSONObject createTaskJson = JSONObject.parseObject(result);
    return createTaskJson.getLong("result");
  }

  @Transactional
  @Override
  public void saveTransactions(Map<String, JSONArray> map) {
    logger.info("SscBrowserServiceImpl|saveTransactions 开始处理[map={}]", map);
    List<SscTransaction> sscTransactions = new ArrayList<>();
    List<SscTransactionEx> sscTransactionExes = new ArrayList<>();
    List<SscBlock> sscBlocks = new ArrayList<>();
    map.keySet().stream().forEach(s -> {
      SscBlock sscBlock = new SscBlock();
      sscBlock.setBlockId(s);
      sscBlock.setTransFee(0L);
      sscBlock.setTransAmount(0L);
      map.get(s).stream().forEach(j -> {
        SscTransaction sscTransaction = getTransactions(s, j.toString());
        sscTransactions.add(sscTransaction);
        if ("ACT".equals(sscTransaction.getCoinType())) {
          sscBlock.setTransAmount(sscBlock.getTransAmount() + sscTransaction.getAmount());
        }
        sscBlock.setTransFee(sscBlock.getTransFee() + sscTransaction.getFee());
        if (!CollectionUtils.isEmpty(sscTransaction.getSscTransactionExList())) {
          sscTransactionExes.addAll(sscTransaction.getSscTransactionExList());
          sscTransaction.getSscTransactionExList().stream().forEach(actTransactionEx -> {
            sscBlock.setTransAmount(sscBlock.getTransAmount() + actTransactionEx.getAmount());
            sscBlock.setTransFee(sscBlock.getTransFee() + actTransactionEx.getFee());
          });
        }
      });
      sscBlocks.add(sscBlock);
    });
    if (!CollectionUtils.isEmpty(sscTransactions)) {
      sscTransactionMapper.insertRecordBatch(sscTransactions);
    }
    if (!CollectionUtils.isEmpty(sscTransactionExes)) {
      sscTransactionExMapper.insertRecordBatch(sscTransactionExes);
    }
    if (!CollectionUtils.isEmpty(sscBlocks)) {
      sscBlocks.stream().forEach(actBlock -> {
        SscBlockCriteria temp = new SscBlockCriteria();
        temp.createCriteria().andBlockIdEqualTo(actBlock.getBlockId()).andStatusEqualTo(TaskDealStatus.TASK_INI);
        List<SscBlock> list = sscBlockMapper.selectByExample(temp);
        actBlock.setStatus(TaskDealStatus.TASK_TRX_CREATE);
        actBlock.setTransAmount(actBlock.getTransAmount() + list.get(0).getTransAmount());
        actBlock.setTransFee(actBlock.getTransFee() + list.get(0).getTransFee());
        actBlock.setId(list.get(0).getId());
        sscBlockMapper.updateByPrimaryKeySelective(actBlock);
      });
    }
  }

  @Override
  public Long getMaxBlockNum() {
    try {
      SscBlockCriteria temp = new SscBlockCriteria();
      temp.setOrderByClause(" id desc");
      temp.setOffSet(0);
      temp.setPageSize(1);
      List<SscBlock> list = sscBlockMapper.selectByExample(temp);
      if (CollectionUtils.isEmpty(list)) {
        return 0L;
      }
      return list.get(0).getBlockNum();
    } catch (Exception e) {
      logger.error("getMaxBlockNum|获取最大区块出现异常", e);
      return -1L;
    }
  }

  @Override
  public SscStatisticsDto statisticsAllData() {
    try {
      //计算总交易笔数
      SscStatisticsParamDto te = new SscStatisticsParamDto();
      SscStatisticsDto sscStatisticsDto = sscTransactionMapper.statisticsAllData(te);
      //计算ACT交易总金额
      te.setCoinType("ACT");
      SscStatisticsDto sscStatisticsDto1 = sscTransactionMapper.statisticsAllData(te);
      sscStatisticsDto.setTransAmount(sscStatisticsDto1.getTransAmount());
      //计算一个小时内的交易总数
      Date date = new Date();
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.HOUR_OF_DAY, -9);//因为是数据是0区
      te.setTrxTime(cal.getTime());
      te.setCoinType(null);
      SscStatisticsDto temp = sscTransactionMapper.statisticsAllData(te);
      sscStatisticsDto.setTransactionHourly(temp.getTransNum() == null ? 0L : temp.getTransNum());
      sscStatisticsDto.setTransactionPeak(temp.getTransNum() == null ? 0L : temp.getTransNum());
      SscStatisticsDto oldDate = redisTemplate.opsForValue().get(RedisWalletDataConstants.WALLET_STATISTICS_ALL_TASK.name());
      if (oldDate != null && (oldDate.getTransactionPeak() > sscStatisticsDto.getTransactionPeak())) {
        sscStatisticsDto.setTransactionPeak(oldDate.getTransactionPeak());
      }
      sscStatisticsDto.setAccountNum(((long) userAddressMapper.countByExampleDistinct(new UserAddressCriteria())) +
                                     sscTransactionMapper.countBySubAddress());
      sscStatisticsDto.setContractNum((long) sscContractInfoMapper.countByExample(new SscContractInfoCriteria()));
      String amount = DataUtils.getActualAmount(sscStatisticsDto.getTransAmount());
      sscStatisticsDto.setTransAmount(amount);
      //统计账户总数和合同总数
      redisTemplate.opsForValue().set(RedisWalletDataConstants.WALLET_STATISTICS_ALL_TASK.name(), sscStatisticsDto);
      return sscStatisticsDto;
    } catch (Exception e) {
      logger.error("statistisAllData|统计出现异常，维护上次数据不变", e);
      return null;
    }
  }

  @Override
  public SscStatisticsDto statisticsAllDataForQuery() {
    return redisTemplate.opsForValue().get(RedisWalletDataConstants.WALLET_STATISTICS_ALL_TASK.name());
  }

  @Override
  public List<SscBlock> getActBlockForUserInfo() {
    SscBlockCriteria criteria = new SscBlockCriteria();
    criteria.createCriteria().andStatusEqualTo(TaskDealStatus.TASK_TRX_CREATE).andTransNumGreaterThan(0);
    return sscBlockMapper.selectByExample(criteria);
  }


  @Override
  public Map<String, JSONArray> getActBlockByStatus(TaskDealStatus taskDealStatus) {
    SscBlockCriteria criteria = new SscBlockCriteria();
    criteria.setOffSet(0);
    criteria.setPageSize(1);
    criteria.setOrderByClause(" id desc");
    criteria.createCriteria().andStatusNotEqualTo(TaskDealStatus.TASK_INI);
    List<SscBlock> list = sscBlockMapper.selectByExample(criteria);
    criteria = new SscBlockCriteria();
    criteria.createCriteria().andStatusEqualTo(taskDealStatus)
            .andIdLessThan(!CollectionUtils.isEmpty(list) ? list.get(0).getId() : 1);
    list = sscBlockMapper.selectByExample(criteria);
    Map<String, JSONArray> map = new HashMap<>();
    if (!CollectionUtils.isEmpty(list)) {
      list.stream().forEach(actBlock -> {
        try {
          JSONArray jsonArray = new JSONArray();
          jsonArray.add(actBlock.getBlockNum());
          String result = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_block", jsonArray);
          if (!StringUtils.isEmpty(result)) {
            JSONObject createTaskJson = JSONObject.parseObject(result);
            createTaskJson = createTaskJson.getJSONObject("result");
            jsonArray = JSONObject.parseArray(createTaskJson.getString("user_transaction_ids"));
            map.put(actBlock.getBlockId(), jsonArray);
          }
        } catch (Exception er) {
          logger.error("SscBrowserServiceImpl|getActBlockByStatus|[actBlock={}]出现异常", actBlock, er);
        }
      });
    }
    return map;
  }



  /**
   * 合约方式计算余额,调用合约才进该方法
   * @param map  key:address+coinType
   * @param sscTransaction　data
   */
  private Map<String,UserAddress>  calculateBalanceByContract(Map<String,UserAddress> map,SscTransaction sscTransaction){
    logger.info("calculateBalanceByContract|[map={}][sscTransaction={}]",map, sscTransaction);
    if("transfer_to_fail".equals(sscTransaction.getEventType())){
         return map;
      }
    String eventParam = sscTransaction.getEventParam();
    if(StringUtils.isEmpty(eventParam)){
      return map;
    }
    logger.info("calculateBalanceByContract|sscTransaction={}", sscTransaction);
    String coinType = sscTransaction.getCoinType();
    String[] params = eventParam.split(",");
    if (params.length < 4){
      logger.info("calculateBalanceByContract|params.length长度小于4不是正常合约转账[map={}][sscTransaction={}]",map, sscTransaction);
      return map;
    }
    String[] address1AndBalance = params[0].split(":");
    String[] address2AndBalance = params[1].split(":");
    Long version = Long.parseLong(params[2]);
    Long trxTime  = Long.parseLong(params[3]);
    String key1 = address1AndBalance[0] + coinType;
    String key2 = address2AndBalance[0] + coinType;

    UserAddress userAddress1 = new UserAddress();
    userAddress1.setVersion(version);
    userAddress1.setTrxTime(trxTime);
    userAddress1.setUserAddress(address1AndBalance[0]);
    userAddress1.setBalance(Long.parseLong(address1AndBalance[1]));
    userAddress1.setCoinType(coinType);
    userAddress1.setLastTrxTime(sscTransaction.getTrxTime());
    userAddress1.setTransNum(1);
    userAddress1.setContractId(sscTransaction.getContractId());

    UserAddress userAddress2 = new UserAddress();
    userAddress2.setVersion(version);
    userAddress2.setTrxTime(trxTime);
    userAddress2.setUserAddress(address2AndBalance[0]);
    userAddress2.setBalance(Long.parseLong(address2AndBalance[1]));
    userAddress2.setCoinType(coinType);
    userAddress2.setLastTrxTime(sscTransaction.getTrxTime());
    userAddress2.setTransNum(1);
    userAddress2.setContractId(sscTransaction.getContractId());

    map = mapLogic(map, key1, userAddress1);
    return mapLogic(map, key2, userAddress2);
  }

  /**
   * 如果对比的userAddress的交易时间比map里面保存的小,则不做处理
   * 如果对比的userAddress的交易时间和map中同key的交易时间相等,并且对比的userAddress的版本号小于map中的,且相差小于等于一个处理周期(1000),则不做处理
   * 其他情况,用新的userAddress替换map中的
   */
  private Map<String, UserAddress> mapLogic(Map<String, UserAddress> map, String key, UserAddress userAddress1) {
    if (map.containsKey(key)) {
      UserAddress oldUserAddress = map.get(key);
      if (userAddress1.getTrxTime() < oldUserAddress.getTrxTime()) {
        return map;
      }
      if (Objects.equals(userAddress1.getTrxTime(), oldUserAddress.getTrxTime())) {
        if (userAddress1.getVersion() < oldUserAddress.getVersion() &&
            (oldUserAddress.getVersion() - userAddress1.getVersion()) <= 1000) {
          return map;
        }
      }
    }
    map.put(key, userAddress1);
    return map;
  }


  @Transactional
  @Override
  public void calculateActBlockForUserInfo(List<SscBlock> sscBlocks) {
    List<String> list = sscBlocks.stream().map(SscBlock::getBlockId).collect(Collectors.toList());
    SscTransactionCriteria criteria = new SscTransactionCriteria();
    criteria.createCriteria().andBlockIdIn(list);
    List<SscTransaction> sscTransactions = sscTransactionMapper.selectByExample(criteria);
    List<UserAddress> userAddresses = new ArrayList<>();
    Map<String,UserAddress> contractProcessMap= new HashMap<>();
    sscTransactions.forEach(actTransaction -> {
      UserAddress fromAcct = new UserAddress();
      fromAcct.setAccountName(actTransaction.getFromAcct());
      fromAcct.setLastTrxTime(actTransaction.getTrxTime());
      fromAcct.setUserAddress(actTransaction.getFromAddr());
      fromAcct.setCoinType("ACT");
      fromAcct.setTransNum(1);
      UserAddress toAcct = new UserAddress();
      toAcct.setUserAddress(actTransaction.getToAddr());
      toAcct.setAccountName(actTransaction.getToAcct());
      toAcct.setLastTrxTime(actTransaction.getTrxTime());
      toAcct.setCoinType("ACT");
      toAcct.setTransNum(1);
      if (StringUtils.isEmpty(actTransaction.getContractId())) {
        userAddresses.add(toAcct);
        userAddresses.add(fromAcct);
      }else {
        if (actTransaction.getTrxType() == TrxType.TRX_TYPE_CALL_CONTRACT.getIntKey()) {
          userAddresses.add(fromAcct);
          if(actTransaction.getCalledAbi().startsWith(ContractCoinType.COIN_TRANSFER_COIN.getValue())){
            contractProcessMap.putAll(calculateBalanceByContract(contractProcessMap, actTransaction));
          }else if (StringUtils.isNotEmpty(actTransaction.getExtraTrxId())){
            SscTransactionExCriteria criteria1 = new SscTransactionExCriteria();
            criteria1.createCriteria().andTrxIdEqualTo(actTransaction.getExtraTrxId());
            List<SscTransactionEx> sscTransactionExList = sscTransactionExMapper.selectByExample(criteria1);
            logger.info("查询到新的子交易|[sscTransactionExList={}]", sscTransactionExList);
            if (!CollectionUtils.isEmpty(sscTransactionExList)) {
              sscTransactionExList.forEach(actTransactionEx -> {
                UserAddress tempUser = new UserAddress();
                tempUser.setCoinType("ACT");
                tempUser.setTransNum(1);
                tempUser.setLastTrxTime(actTransaction.getTrxTime());
                tempUser.setUserAddress(actTransactionEx.getToAddr());
                userAddresses.add(tempUser);
              });
            }
          }
        }else {
          userAddresses.add(fromAcct);
        }
      }
    });
    Map<String, UserAddress> result1 = userAddresses.stream().collect(Collectors.toMap(UserAddress::getUserAddress,
                                                                                       (userAddress) -> userAddress,
                                                                                       (oldValue, newValue) -> {
//                                                                                         newValue.setBalance(
//                                                                                             oldValue.getBalance() +
//                                                                                             newValue.getBalance());
//                                                                                         newValue.setTransNum(
//                                                                                             oldValue.getTransNum() +
//                                                                                             newValue.getTransNum());
                                                                                         return newValue;
                                                                                       }));
    List<UserAddress> userAddressesLast = new ArrayList<>();
    result1.keySet().forEach(s -> {
      UserAddress userAddress = result1.get(s);
      userAddress.setBalance(getAddrBalance(userAddress.getUserAddress()));
      UserAddressCriteria criteria1 = new UserAddressCriteria();
      criteria1.createCriteria().andUserAddressEqualTo(s).andCoinTypeEqualTo(userAddress.getCoinType());
      criteria1.setPageSize(1);
      List<UserAddress> us = userAddressMapper.selectByExample(criteria1);
      if (CollectionUtils.isEmpty(us)) {
        userAddressesLast.add(result1.get(s));
      } else {
        UserAddress temp = new UserAddress();
        temp.setBalance(getAddrBalance(userAddress.getUserAddress()));
        temp.setTransNum(result1.get(s).getTransNum() + us.get(0).getTransNum());
        temp.setLastTrxTime(result1.get(s).getLastTrxTime());
        if (StringUtils.isNotEmpty(userAddress.getAccountName())) {
          temp.setAccountName(userAddress.getAccountName());
        }
        userAddressMapper.updateByExampleSelective(temp, criteria1);
      }
    });
    contractProcessMap.keySet().stream().forEach(key ->{
      UserAddress userAddress = contractProcessMap.get(key);
      UserAddressCriteria criteria1 = new UserAddressCriteria();
      criteria1.createCriteria().andUserAddressEqualTo(userAddress.getUserAddress()).andCoinTypeEqualTo(userAddress.getCoinType());
      criteria1.setPageSize(1);
      List<UserAddress> us = userAddressMapper.selectByExample(criteria1);
      if (CollectionUtils.isEmpty(us)) {
        userAddressesLast.add(userAddress);
      }else{
        userAddress.setTransNum(userAddress.getTransNum() + us.get(0).getTransNum());
        userAddressMapper.updateByExampleSelective(userAddress, criteria1);
      }
    });
    if (!CollectionUtils.isEmpty(userAddressesLast)) {
      userAddressMapper.insertRecordBatch(userAddressesLast);
    }

    SscBlock sscBlock = new SscBlock();
    sscBlock.setStatus(TaskDealStatus.TASK_CALCULATE_USER_INFO);
    SscBlockCriteria actBlockC = new SscBlockCriteria();
    actBlockC.createCriteria()
             .andIdIn(sscBlocks.stream().map(SscBlock::getId).collect(Collectors.toList()))
             .andStatusEqualTo(TaskDealStatus.TASK_TRX_CREATE);
    sscBlockMapper.updateByExampleSelective(sscBlock, actBlockC);
  }

  public long getAddrBalance(String userAddress) {
    try{
      JSONArray tempJson = new JSONArray();
      tempJson.add(userAddress);
      long result1 = 0L;
      String result = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_list_address_balances", tempJson);
      JSONObject jsonObject = JSONObject.parseObject(result);
      JSONArray jsonArray = jsonObject.getJSONArray("result");
      if (jsonArray != null && jsonArray.size() > 0) {
        for (int i = 0; i < jsonArray.size(); i++) {
          logger.info(jsonArray.getJSONArray(i).toJSONString());
          logger.info(jsonArray.getJSONArray(i).getJSONObject(1).toJSONString());
          result1 = result1 + jsonArray.getJSONArray(i).getJSONObject(1).getLong("balance");
        }
        return result1;
      }
    }catch (Exception e){
      logger.error("SscBrowserServiceImpl|getAddrBalance|[userAddress={}]出现异常", userAddress, e);
    }
    return 0L;
  }

  private Date dealTime(String timestamp) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    return format.parse(timestamp);
  }


  private SscTransaction getTransactions(String blockId, String trxId) {
    SscTransaction sscTransaction = new SscTransaction();
    sscTransaction.setBlockId(blockId);
    sscTransaction.setTrxId(trxId);
    try {
      JSONArray jsonArray = new JSONArray();
      jsonArray.add(trxId);
      String result = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_transaction", jsonArray);
      JSONObject resultJson = JSONObject.parseObject(result);
      String firstOpType =
          resultJson.getJSONArray("result").getJSONObject(1).getJSONObject("trx").getJSONArray("operations")
                    .getJSONObject(0).getString("type");

      String alpAccount =
          resultJson.getJSONArray("result").getJSONObject(1).getJSONObject("trx").getString("alp_account");
      sscTransaction.setSubAddress(alpAccount);
      JSONObject createTaskJson;
      sscTransaction.setCoinType("ACT");
      if ("transaction_op_type".equals(firstOpType)) {
        String result_trx_id =
            resultJson.getJSONArray("result").getJSONObject(1).getJSONObject("trx").getString("result_trx_id");
        jsonArray = new JSONArray();
        jsonArray.add(StringUtils.isEmpty(result_trx_id) ? trxId : result_trx_id);
        logger.info("getTransactions|transaction_op_type|[blockId={}][trxId={}][result_trx_id={}]", blockId, trxId, result_trx_id);
        String resultSignee = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_pretty_contract_transaction", jsonArray);
        createTaskJson = JSONObject.parseObject(resultSignee);
        createTaskJson = createTaskJson.getJSONObject("result");
        sscTransaction.setExtraTrxId(StringUtils.isEmpty(result_trx_id) ? trxId : result_trx_id);
        sscTransaction.setTrxId(createTaskJson.getString("orig_trx_id"));
        JSONObject temp = createTaskJson.getJSONObject("to_contract_ledger_entry");
        sscTransaction.setFromAddr(temp.getString("from_account"));
        sscTransaction.setFromAcct(temp.getString("from_account_name"));
        sscTransaction.setContractId(temp.getString("to_account"));
        sscTransaction.setToAcct("");
        sscTransaction.setToAddr("");
        sscTransaction.setAmount(temp.getJSONObject("amount").getLong("amount"));
        sscTransaction.setFee(temp.getJSONObject("fee").getInteger("amount"));
        sscTransaction.setTrxTime(dealTime(createTaskJson.getString("timestamp")));
        sscTransaction.setMemo(temp.getString("memo"));
        if ("false".equals(createTaskJson.getString("is_completed"))) {
          sscTransaction.setIsCompleted((byte) 0);
        }
        JSONArray reserved = createTaskJson.getJSONArray("reserved");
        sscTransaction.setCalledAbi(reserved.size() >= 1 ? reserved.getString(0) : null);
        sscTransaction.setAbiParams(reserved.size() > 1 ? reserved.getString(1) : null);
        JSONArray jsonArray1 = createTaskJson.getJSONArray("from_contract_ledger_entries");
        int trx_type = createTaskJson.getInteger("trx_type");
        JSONObject jsonObject = getEvent(blockId, trxId, sscTransaction);
        sscTransaction.setEventType(jsonObject.getString("event_type"));
        sscTransaction.setEventParam(jsonObject.getString("event_param"));
        if (trx_type == TrxType.TRX_TYPE_CALL_CONTRACT.getIntKey() &&
            sscTransaction.getCalledAbi().startsWith(ContractCoinType.COIN_TRANSFER_COIN.getValue())) {
          if (StringUtils.isEmpty(sscTransaction.getEventType())) {
            logger.error("SscBrowserServiceImpl|saveActBlock|getEvent|[sscTransaction={}][trxId={}],合约调用的交易类型，必须含有event_type", sscTransaction,trxId);
//            throw new Exception();
          }
          logger.info("SscBrowserServiceImpl|saveActBlock|[sscTransaction={}]", sscTransaction);
          if (StringUtils.isNotEmpty(sscTransaction.getAbiParams())) {
            String[] params = sscTransaction.getAbiParams().split("\\|");
            String userAddress = params[0].trim();
            if (userAddress.length() > 50) {
              sscTransaction.setSubAddress(userAddress.length() > 69 ? userAddress.substring(0,70):userAddress);
              userAddress = userAddress.substring(0, userAddress.length() - 32);
            }
            sscTransaction.setToAddr(userAddress);
            if (!StringUtils.isEmpty(sscTransaction.getCalledAbi()) &&
                StringUtils.isNotEmpty(sscTransaction.getEventType()) &&
                sscTransaction.getEventType().contains("transfer_to_success")) {
              boolean flag = true;
              for (int i = 1; i < params.length; i++) {
                logger.info("getTransactions|gettempp[tempp={}]",params.length >= 2 ? params[i] : "");
                if (!StringUtils.isEmpty(params[i])) {
                  if (flag) {
                    String tempp = params.length >= 2 ? params[i] : "0";
                    try {
                      Double d = Double.parseDouble(tempp);
                      sscTransaction.setAmount(new BigDecimal(d < 0 ? "0" : d.toString()).multiply(new BigDecimal(100000)).longValue());
                    } catch (Exception e) {
                      logger.error("getTransactions|gettempp", e);
                      sscTransaction.setMemo("0");
                    }
                    flag = false;
                  }else {
                    sscTransaction.setMemo(params[i]);
                  }
                }
              }
            }
          }
        }
        String type = dealDataByTrxType(sscTransaction.getContractId(), trx_type, sscTransaction);
        if (trx_type == TrxType.TRX_TYPE_CALL_CONTRACT.getIntKey()) {
          sscTransaction.setCoinType(type);
        }
        List<SscTransactionEx> list = new ArrayList<>();
        jsonArray1.stream().forEach(te -> {
          JSONObject teJson = (JSONObject) te;
          SscTransactionEx sscTransactionEx = new SscTransactionEx();
          sscTransactionEx.setTrxId(sscTransaction.getExtraTrxId());
          sscTransactionEx.setOrigTrxId(sscTransaction.getTrxId());
          sscTransactionEx.setTrxTime(sscTransaction.getTrxTime());
          sscTransactionEx.setFromAddr(teJson.getString("from_account"));
          sscTransactionEx.setFromAcct(teJson.getString("from_account_name"));
          sscTransactionEx.setToAcct(teJson.getString("to_account_name"));
          sscTransactionEx.setToAddr(teJson.getString("to_account"));
          sscTransactionEx.setAmount(teJson.getJSONObject("amount").getLong("amount"));
          sscTransactionEx.setFee(teJson.getJSONObject("fee").getInteger("amount"));
          sscTransactionEx.setMemo(teJson.getString("memo"));
          sscTransactionEx.setTrxType((byte) trx_type);
          list.add(sscTransactionEx);
        });
        sscTransaction.setSscTransactionExList(list);
      } else {
        String resultSignee = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_pretty_transaction", jsonArray);
        createTaskJson = JSONObject.parseObject(resultSignee);
        createTaskJson = createTaskJson.getJSONObject("result");
        JSONObject temp = (JSONObject) createTaskJson.getJSONArray("ledger_entries").get(0);
        sscTransaction.setFromAddr(temp.getString("from_account"));
        sscTransaction.setFromAcct(temp.getString("from_account_name"));
        sscTransaction.setToAcct(temp.getString("to_account_name"));
        sscTransaction.setToAddr(temp.getString("to_account"));
        sscTransaction.setAmount(temp.getJSONObject("amount").getLong("amount"));
        sscTransaction.setFee(createTaskJson.getJSONObject("fee").getInteger("amount"));
        sscTransaction.setTrxTime(dealTime(createTaskJson.getString("timestamp")));
        sscTransaction.setMemo(temp.getString("memo"));
        sscTransaction.setIsCompleted((byte) 0);
      }
      sscTransaction.setBlockNum(createTaskJson.getLong("block_num"));
      sscTransaction.setBlockPosition(createTaskJson.getInteger("block_position"));
      sscTransaction.setTrxType(createTaskJson.getInteger("trx_type"));
    } catch (Exception er) {
      logger.error("SscBrowserServiceImpl|saveActBlock|[trx_id={}]出现异常", trxId, er);
    }
    return sscTransaction;
  }

  private JSONObject getEvent(String blockId, String trxId, SscTransaction sscTransaction) throws Exception {
    JSONArray jsonArrayEvent = new JSONArray();
    SscBlockCriteria sscBlockCriteria = new SscBlockCriteria();
    sscBlockCriteria.createCriteria().andBlockIdEqualTo(blockId);
    sscBlockCriteria.setOffSet(0);
    sscBlockCriteria.setPageSize(1);
    List<SscBlock> list = sscBlockMapper.selectByExample(sscBlockCriteria);
    jsonArrayEvent.add(list.get(0).getBlockNum());
    jsonArrayEvent.add(trxId);
    String resultEvent = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "blockchain_get_events", jsonArrayEvent);
    if (StringUtils.isEmpty(resultEvent)) {
      return new JSONObject();
    }
    JSONObject jsonObject = JSONObject.parseObject(resultEvent);
    JSONArray jsonArray = jsonObject.getJSONArray("result");
    logger.info("SscBrowserServiceImpl|getEvent|[blockId={}][trx_id={}][result={}]", blockId,trxId,jsonArray);
    JSONObject result = new JSONObject();
    if (null != jsonArray && jsonArray.size() > 0) {
      StringBuffer eventType = new StringBuffer();
      StringBuffer eventParam = new StringBuffer();
      jsonArray.stream().forEach(json ->{
        JSONObject jso = (JSONObject) json;
        eventType.append(eventType.length() > 0 ? "|" : "").append(jso.getString("event_type"));
        eventParam.append(eventParam.length() > 0 ? "|" : "").append(jso.getString("event_param"));
      });
      result.put("event_type",eventType);
      result.put("event_param",eventParam);
    }
    return result;
  }


  private String dataDeal(JSONObject jsonObject){
    String methodName = jsonObject.getString("method");
    JSONArray params = jsonObject.getJSONArray("params");
    if (StringUtils.isEmpty(methodName)) {
      return null;
    }
    try {
      return sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, methodName, params);
    } catch (Exception e) {
      logger.error("SscBrowserServiceImpl|dataDeal", e);
      return null;
    }
  }

  /**
   *
   * @param trxType
   */
  private String dealDataByTrxType(String contractId, int trxType, SscTransaction sscTransaction) throws Exception {
    JSONArray jsonArray = new JSONArray();
    jsonArray.add(contractId);
    SscContractInfo sscContractInfo = null;
    String contractInfo = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "get_contract_info", jsonArray);
    if (!StringUtils.isEmpty(contractInfo)) {
      sscContractInfo = new SscContractInfo();
      sscContractInfo.setContractId(contractId);
      JSONObject createTaskJson = JSONObject.parseObject(contractInfo);
      createTaskJson = createTaskJson.getJSONObject("result");
      sscContractInfo.setName(createTaskJson.getString("contract_name"));
      sscContractInfo.setOwner(createTaskJson.getString("owner"));
      sscContractInfo.setOwnerAddress(createTaskJson.getString("owner_address"));
      sscContractInfo.setOwnerName(createTaskJson.getString("owner_name"));
      sscContractInfo.setDescription(createTaskJson.getString("description"));
      sscContractInfo.setBytecode(createTaskJson.getJSONObject("code_printable").getString("printable_code"));
      sscContractInfo.setHash(createTaskJson.getJSONObject("code_printable").getString("code_hash"));
      sscContractInfo.setRegTime(sscTransaction.getTrxTime());
      sscContractInfo.setRegTrxId(sscTransaction.getTrxId());
      sscContractInfo.setBalance(0L);
      sscContractInfo.setStatus(ContractStatus.getContractStatus(createTaskJson.getString("level")).getIntKey());
      //  "level": "forever",
      JSONArray abi = createTaskJson.getJSONObject("code_printable").getJSONArray("abi");
      List<SscContractAbi> sscContractAbis = new ArrayList<>();
      abi.stream().forEach(json -> {
        SscContractAbi sscContractAbi = new SscContractAbi();
        sscContractAbi.setContractId(contractId);
        sscContractAbi.setAbiName(json.toString());
        sscContractAbis.add(sscContractAbi);
      });
      abi = createTaskJson.getJSONObject("code_printable").getJSONArray("events");
      List<SscContractEvent> sscContractEvents = new ArrayList<>();
      abi.stream().forEach(json -> {
        SscContractEvent sscContractEvent = new SscContractEvent();
        sscContractEvent.setContractId(contractId);
        sscContractEvent.setEvent(json.toString());
        sscContractEvents.add(sscContractEvent);
      });
      abi = createTaskJson.getJSONObject("code_printable").getJSONArray("printable_storage_properties");
      List<SscContractStorage> sscContractStorages = new ArrayList<>();
      abi.stream().forEach(json -> {
        JSONArray tem = (JSONArray) json;
        SscContractStorage sscContractStorage = new SscContractStorage();
        sscContractStorage.setContractId(contractId);
        sscContractStorage.setName(tem.getString(0));
        sscContractStorage.setType(tem.getString(1));
        sscContractStorages.add(sscContractStorage);
      });
      sscContractInfo.setSscContractAbis(sscContractAbis);
      sscContractInfo.setSscContractStorages(sscContractStorages);
      sscContractInfo.setSscContractEvents(sscContractEvents);
    }
    if (sscContractInfo == null) {
      throw new Exception();
    }
    List<SscContractAbi> list = sscContractInfo.getSscContractAbis();
    if (!CollectionUtils.isEmpty(list)) {
      List<String> tempList = list.stream().map(SscContractAbi::getAbiName).collect(Collectors.toList());
      String type = tempList.stream().filter(string-> string.startsWith("COIN_")).findAny().orElse(null);
      if (tempList.containsAll(ContractCoinType.getCoinTypeMeth()) && !StringUtils.isEmpty(type)) {
        sscContractInfo.setType((byte) 1);
        sscContractInfo.setCoinType(type.replace("COIN_",""));
      } else {
        sscContractInfo.setType((byte) 0);
      }
    }
    SscContractInfoCriteria c = new SscContractInfoCriteria();
    c.createCriteria().andContractIdEqualTo(contractId);
    switch (TrxType.getTrxType(trxType)) {
      case TRX_TYPE_DESTROY_CONTRACT:
        SscContractInfo temp = new SscContractInfo();
        temp.setStatus(ContractStatus.COIN_TRANSFER_DESTROY.getIntKey());
        sscContractInfoMapper.updateByExampleSelective(temp, c);
        break;
      case TRX_TYPE_REGISTER_CONTRACT:
        sscContractInfoMapper.insert(sscContractInfo);
        saveExInfo(contractId, sscContractInfo);
        break;
      case TRX_TYPE_UPGRADE_CONTRACT:
        logger.info("TRX_TYPE_UPGRADE_CONTRACT|[sscContractInfo={}]", sscContractInfo.getStatus());
        sscContractInfo.setStatus(ContractStatus.COIN_TRANSFER_FOREVER.getIntKey());
        sscContractInfo.setRegTime(null);
        sscContractInfoMapper.updateByExampleSelective(sscContractInfo, c);
        saveExInfo(contractId, sscContractInfo);
        break;
      default:
        temp = new SscContractInfo();
        String balance = sdkHttpClient.post(walletRpcUrl, walletRpcUserauth, "get_contract_balance", jsonArray);
        JSONObject createTaskJson = JSONObject.parseObject(balance);
        temp.setBalance(createTaskJson.getJSONArray("result").getJSONObject(0).getLong("balance"));
        if ("query_issue_current_total".equals(sscTransaction.getCalledAbi())){
          temp.setCirculation(Long.parseLong(sscTransaction.getEventParam()));
        }
        sscContractInfoMapper.updateByExampleSelective(temp, c);
    }
    return StringUtils.isEmpty(sscContractInfo.getCoinType())? sscContractInfo.getContractId() : sscContractInfo.getCoinType();
  }

  private void saveExInfo(String contractId, SscContractInfo sscContractInfo) {
    SscContractAbiCriteria c1 = new SscContractAbiCriteria();
    c1.createCriteria().andContractIdEqualTo(contractId);
    sscContractAbiMapper.deleteByExample(c1);
    SscContractEventCriteria c2 = new SscContractEventCriteria();
    c2.createCriteria().andContractIdEqualTo(contractId);
    sscContractEventMapper.deleteByExample(c2);
    SscContractStorageCriteria c3 = new SscContractStorageCriteria();
    c3.createCriteria().andContractIdEqualTo(contractId);
    sscContractStorageMapper.deleteByExample(c3);
    //批量更新
    if (!CollectionUtils.isEmpty(sscContractInfo.getSscContractAbis())) {
      sscContractAbiMapper.insertRecordBatch(sscContractInfo.getSscContractAbis());
    }
    if (!CollectionUtils.isEmpty(sscContractInfo.getSscContractEvents())) {
      sscContractEventMapper.insertRecordBatch(sscContractInfo.getSscContractEvents());
    }
    if (!CollectionUtils.isEmpty(sscContractInfo.getSscContractStorages())) {
      sscContractStorageMapper.insertRecordBatch(sscContractInfo.getSscContractStorages());
    }
  }

  @Override
  public List<UserAddress> listByAddress(String address) {
    UserAddressCriteria criteria = new UserAddressCriteria();
    criteria.createCriteria().andUserAddressEqualTo(address);
    return userAddressMapper.selectByExample(criteria);
  }

  @Override
  public ContractInfoPageVO listContractInfoByKey(String key, ContractStatus contractStatus, Integer page,
                                                  Integer perPage, Integer queryType) {
    ContractInfoPageVO contractInfoPageVO = new ContractInfoPageVO();
    contractInfoPageVO.setCurrentPage(page);
    contractInfoPageVO.setPageSize(perPage);
    SscContractInfoCriteria criteria = new SscContractInfoCriteria();

    criteria.setOffSet((page - 1) * perPage);
    criteria.setPageSize(perPage);
    criteria.setOrderByClause("reg_time DESC");

    //每页关键词查询所有的永久合约
    if(StringUtils.isEmpty(key)){
      criteria.createCriteria().andStatusEqualTo(contractStatus.getIntKey());
    }else if(queryType == 0){
       criteria.createCriteria().andContractIdEqualTo(key).andStatusEqualTo(contractStatus.getIntKey());
    }else if (queryType == 1){
       key = "%" + key + "%";
       criteria.createCriteria().andNameLike(key).andStatusEqualTo(contractStatus.getIntKey());
    }else {
       contractInfoPageVO.setTotalPage(0);
       contractInfoPageVO.setTotalRecords(0);
       contractInfoPageVO.setContractInfoVOList(new ArrayList<>());
       return contractInfoPageVO;
    }
    int totalNum = sscContractInfoMapper.countByExample(criteria);
    int totalPage = totalNum % perPage == 0 ? totalNum / perPage : totalNum / perPage + 1;
    contractInfoPageVO.setTotalPage(totalPage);
    contractInfoPageVO.setTotalRecords(totalNum);
    contractInfoPageVO.setSscContractInfoList(sscContractInfoMapper.selectByExample(criteria));
    return contractInfoPageVO;
  }


  @Override
  public ContractInfoPageVO listAllForeverCoinType(ContractStatus contractStatus) {
    SscContractInfoCriteria criteria = new SscContractInfoCriteria();
    criteria.createCriteria().andStatusEqualTo(contractStatus.getIntKey()).andCoinTypeIsNotNull();
    List<SscContractInfo> list = sscContractInfoMapper.selectByExample(criteria);
    ContractInfoPageVO contractInfoPageVO = new ContractInfoPageVO();
    contractInfoPageVO.setSscContractInfoList(list);
    return contractInfoPageVO;
  }

  @Override
  public List<SscContractConfig> listUrlsByContractId(String contractId) {
    SscContractConfigCriteria criteria = new SscContractConfigCriteria();
    criteria.createCriteria().andContractIdEqualTo(contractId);
    return sscContractConfigMapper.selectByExample(criteria);
  }
}
