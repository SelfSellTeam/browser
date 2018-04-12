package com.achain.controller;

import com.achain.domain.dto.SscBlockDto;
import com.achain.domain.dto.SscTransactionDto;
import com.achain.domain.dto.CacheWalletDataKeys;
import com.achain.domain.dto.PageResult;
import com.achain.domain.entity.*;
import com.achain.domain.enums.ContractStatus;
import com.achain.domain.vo.SscTransactionExPageVO;
import com.achain.domain.vo.ContractInfoPageVO;
import com.achain.domain.vo.ContractInfoVO;
import com.achain.domain.vo.UserBalanceVO;
import com.achain.service.ISscBrowserService;
import com.achain.service.IBrowserBlockService;
import com.achain.service.IBrowserContractService;
import com.achain.service.IBrowserTransactionService;
import com.achain.utils.DataUtils;
import com.achain.utils.DateUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by qiangkz on 2017/8/1.
 */
@RestController
@RequestMapping("/api/browser/ssc")
@CrossOrigin(origins = "*")
public class SscBrowserController {

    private static Logger logger = LoggerFactory.getLogger(SscBrowserController.class);

    @Autowired
    private IBrowserBlockService iBrowserBlockService;

    @Autowired
    private IBrowserTransactionService iBrowserTransactionService;

    @Autowired
    private ISscBrowserService iSscBrowserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //    @Autowired
//    private IBlockChainDataCallback blockChainDataCallback;
//
    @Autowired
    private IBrowserContractService iBrowserContractService;

    private static final String ACCOUNT_PREFIX = "SSC";
    private static final String CONTRACT_PREFIX = "CON";


//    /**
//     * 主页相关统计信息
//     */
//    @RequestMapping(value = "/Statistic.Transaction", method = RequestMethod.GET)
//    public Map<String, Object> statisticsTransaction() {
//        try {
//            Map<String, Object> map = getWebResultSuccess();
//            map.put("result", iSscBrowserService.statisticsAllDataForQuery());
//            return map;
//        } catch (Exception e) {
//            logger.error("blockAgentQuery|出现异常", e);
//            return getWebResultFail();
//        }
//    }

    /**
     * 区块信息
     */
    @RequestMapping(value = "/Block.Query", method = RequestMethod.GET)
    public Map<String, Object> blockQuery(Integer page, Integer per_page) {
        PageResult<SscBlockDto> pageResult;
        Map<String, Object> map = getWebResultSuccess();
        if (page == 1 && per_page == 10) {
            pageResult = (PageResult<SscBlockDto>) redisTemplate.opsForValue().get(CacheWalletDataKeys.getWalletBlockRedisKey(null));
            if (pageResult != null) {
                map.put("result", pageResult);
            } else {
                pageResult = getBlockQueryFromCache(page, per_page);
                map.put("result", pageResult);
            }
        } else {
            pageResult = getBlockQueryFromCache(page, per_page);
            map.put("result", pageResult);
        }
        return map;
    }

    /**
     * 查询交易列表
     */

    @RequestMapping(value = "/Transaction.Query", method = RequestMethod.GET)
    public Map<String, Object> transactionQuery(Integer page, Integer per_page) {
        Map<String, Object> map = getWebResultSuccess();
        if (page == 1 && per_page == 10) {
            PageResult<SscTransactionDto> pageResult
                    = (PageResult<SscTransactionDto>) redisTemplate.opsForValue().get(CacheWalletDataKeys.getWalletTransactionRedisKey(null));
            if (pageResult != null) {
                map.put("result", pageResult);
            } else {
                pageResult = getTransactionQueryFromCache(page, per_page);
                map.put("result", pageResult);
            }
        } else {
            PageResult<SscTransactionDto> pageResult = getTransactionQueryFromCache(page, per_page);
            map.put("result", pageResult);
        }
        return map;
    }

    /**
     * 返回查询相关信息 0-区块hash 1-区块高度 2-交易id 3-账户地址 4-账户名
     * 返回0时需返回区块高度
     */

    @RequestMapping(value = "/Query.Type", method = RequestMethod.GET)
    public Map<String, Object> queryType(String keyword) {

        Map<String, Object> map = getWebResultSuccess();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("record_num", 1);
        if (StringUtils.isNumeric(keyword) && keyword.length() < 11) {
            long key = Long.parseLong(keyword);
            SscBlock sscBlock = getQueryTypeWithNumericKeywordFromCache(key);
            if (Objects.nonNull(sscBlock)) {
                jsonObject.put("query_type", 1);
                map.put("result", jsonObject);
                return map;
            }
        }
        if (keyword.startsWith(ACCOUNT_PREFIX)) {
            jsonObject.put("query_type", 3);
            map.put("result", jsonObject);
            return map;
        }
        if (keyword.startsWith(CONTRACT_PREFIX)) {
            jsonObject.put("query_type", 5);
            map.put("result", jsonObject);
            return map;
        }
        if (!iBrowserTransactionService.isTrxId(keyword)) {
            jsonObject.put("query_type", 2);
            map.put("result", jsonObject);
            return map;
        }
        SscBlock actblock = getQueryTypeWithBlockIdKeywordFromCache(keyword);
        if (Objects.nonNull(actblock)) {
            jsonObject.put("query_type", 0);
            jsonObject.put("block_num", actblock.getBlockNum());
            map.put("result", jsonObject);
            return map;
        }
        UserAddress userAddress = getQueryTypeWithAccountNameKeywordFromCache(keyword);
        if (!StringUtils.isEmpty(keyword) && Objects.nonNull(userAddress)) {
            jsonObject.put("query_type", 4);
            jsonObject.put("user_address", userAddress.getUserAddress());
            map.put("result", jsonObject);
            return map;
        }
        jsonObject.replace("record_num", 0);
        map.put("result", jsonObject);
        return map;

    }

    /**
     * 根据合约查询区块信息
     */
    @RequestMapping(value = "/BlockAgent.Query", method = RequestMethod.GET)
    public Map<String, Object> blockAgentQuery(@RequestParam("signee") String signee,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("per_page") Integer per_page) {

        PageResult<SscBlockDto> result;

        Map<String, Object> map = getWebResultSuccess();
        result = getBlockAgentQueryFromCache(signee, page, per_page);
        map.put("result", result);
        return map;

    }

    /**
     * 根据单号查询交易详细信息
     */

    @RequestMapping(value = "/TransactionInfo.Query", method = RequestMethod.GET)
    public Map<String, Object> transactionInfoQuery(@RequestParam("trx_id") String trx_id, Integer page,
                                                    Integer per_page) {
        if (null == page) {
            page = 1;
        }
        if (null == per_page) {
            per_page = 10;
        }
        if (page < 1 || per_page < 1) {
            return getWebResultFail();
        }

        Map<String, Object> map = getWebResultSuccess();
        SscTransaction sscTransaction = getTransactionInfoTrxQueryFromCache(trx_id);
        Map<String, Object> map1 = new HashMap<>(17);
        if (sscTransaction != null) {
            map1.put("from_acct", sscTransaction.getFromAcct());
            map1.put("from_addr", sscTransaction.getFromAddr());
            map1.put("trx_type", sscTransaction.getTrxType());
            map1.put("trx_time", DateUtils.getTimeoneEight(sscTransaction.getTrxTime()));
            map1.put("fee", DataUtils.getActualAmount(sscTransaction.getFee() + ""));
            map1.put("called_abi", sscTransaction.getCalledAbi());
            map1.put("to_acct", sscTransaction.getToAcct());
            map1.put("to_addr", sscTransaction.getToAddr());
            map1.put("amount", DataUtils.getActualAmount(sscTransaction.getAmount() + ""));
            map1.put("block_num", sscTransaction.getBlockNum());
            map1.put("memo", sscTransaction.getMemo());
            map1.put("abi_params", sscTransaction.getAbiParams());
            map1.put("contract_id", sscTransaction.getContractId());
            map1.put("event_type", sscTransaction.getEventType());
            map1.put("coin_type", !StringUtils.isEmpty(sscTransaction.getCoinType()) &&
                    sscTransaction.getCoinType().length() > 6 ? "" : sscTransaction.getCoinType());
            map1.put("event_params", sscTransaction.getEventParam());
            if (StringUtils.isNotEmpty(sscTransaction.getExtraTrxId())) {
                SscTransactionExPageVO sscTransactionExPageVO =
                        getTransactionInfoExTrxQueryFromCache(page, per_page, sscTransaction);
                map1.put("ActTransactionExs", sscTransactionExPageVO);
            }
        }
        map.put("result", map1);
        return map;

    }

    /**
     * 根据地址查询该地址所有币种的余额
     */
    @GetMapping("/contract/balance/query/{address}")
    public Map<String, Object> queryBalanceByAddress(@PathVariable String address) {

        if (StringUtils.isEmpty(address)) {
            return getWebResultFail();
        }

        List<UserBalanceVO> result = new ArrayList<>();
        List<UserAddress> userAddressList = queryBalanceByAddressFromCache(address);
        userAddressList.forEach(userAddress -> {
            UserBalanceVO userBalanceVO = new UserBalanceVO();
            userBalanceVO.setCoinType(userAddress.getCoinType());
            BigDecimal balance = new BigDecimal(DataUtils.getActualAmount(
                    Objects.isNull(userAddress.getBalance()) ? "0" : userAddress.getBalance().toString()));
            //如果不是ACT并且余额为零就不显示
            String coinType = "ACT";
            if (!coinType.equals(userBalanceVO.getCoinType()) && balance.compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }

            //ACT余额为负数显示0
            if (coinType.equals(userBalanceVO.getCoinType()) && balance.compareTo(BigDecimal.ZERO) <= 0) {
                balance = BigDecimal.ZERO;
            }

            userBalanceVO.setBalance(balance);
            result.add(userBalanceVO);
        });

        Map<String, Object> map = getWebResultSuccess();
        map.put("data", result);
        return map;
    }

    /**
     * /contract/balance/query/{address} 接口缓存
     */
    private List<UserAddress> queryBalanceByAddressFromCache(String address) {
        String redisKey = Joiner.on(":").join("queryBalanceByAddress", address);
        List<UserAddress> userAddressList = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), new TypeReference<List<UserAddress>>() {
        });
        if (Objects.isNull(userAddressList)) {
            userAddressList = iSscBrowserService.listByAddress(address);
            if (Objects.nonNull(userAddressList)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(userAddressList), 10L, TimeUnit.SECONDS);
            }
        }
        return userAddressList;
    }

    /**
     * 查询制定区块上交易的详细信息
     */
    @RequestMapping(value = "/TransactionList.Query", method = RequestMethod.GET)
    public Map<String, Object> transactionListQuery(@RequestParam("block_num") long block_num,
                                                    @RequestParam("acct_address") String acct_address,
                                                    @RequestParam("page") Integer page,
                                                    @RequestParam("per_page") Integer per_page) {
        String key = Joiner.on(":").join(block_num, acct_address, page, per_page);
        Map<String, Object> map = getWebResultSuccess();
        PageResult<SscTransactionDto> pageResult = JSONObject.parseObject(
                stringRedisTemplate.opsForValue().get(CacheWalletDataKeys.getWalletTransactionRedisKey(key)),
                new TypeReference<PageResult<SscTransactionDto>>() {
                });
        if (pageResult == null) {
            pageResult = iBrowserTransactionService.transactionListQuery(block_num, acct_address, page, per_page);
            if (Objects.nonNull(pageResult)) {
                stringRedisTemplate.opsForValue().set(CacheWalletDataKeys.getWalletTransactionRedisKey(key),
                        JSONObject.toJSONString(pageResult), 10, TimeUnit.SECONDS);
            }
        }
        map.put("result", pageResult);
        return map;
    }

    /**
     * 根据block_id或者block_num查询区块信息
     */

    @RequestMapping(value = "/BlockInfo.Query", method = RequestMethod.GET)
    public Map<String, Object> blockInfoQuery(String block_id, Long block_num) {
        Map<String, Object> map = getWebResultSuccess();
        SscBlock sscBlock;
        if (StringUtils.isNotEmpty(block_id)) {
            sscBlock = blockInfoQueryWithBlockIdFromCache(block_id);
        } else {
            sscBlock = blockInfoQueryWithBlockNumFromCache(block_num);
        }
        Map<String, Object> map1 = new HashMap<>();
        if (sscBlock != null) {
            map1.put("block_id", sscBlock.getBlockId());
            map1.put("block_num", sscBlock.getBlockNum());
            map1.put("block_size", sscBlock.getBlockSize());
            map1.put("signee", sscBlock.getSignee());
            map1.put("trans_amount", DataUtils.getActualAmount(sscBlock.getTransAmount() + ""));
            map1.put("trans_num", sscBlock.getTransNum());
            map1.put("block_bonus", "3.5");
            map1.put("trans_fee", DataUtils.getActualAmount(sscBlock.getTransFee() + ""));
            map1.put("block_time", DateUtils.getTimeoneEight(sscBlock.getBlockTime()));
        }
        map.put("result", map1);
        return map;
    }

    /**
     * 根据合约id查询合约具体信息
     *
     * @param contract_id 　合约id
     * @return data
     */

    @RequestMapping(value = "/ContractInfo.Query", method = RequestMethod.GET)
    public Map<String, Object> contractInfoQuery(String contract_id) {
        Map<String, Object> map = getWebResultSuccess();
        SscContractInfo sscContractInfo = getContractInfoQueryFromCache(contract_id);
        Map<String, Object> map1 = new HashMap<>(16);
        if (Objects.nonNull(sscContractInfo)) {
            List<String> abis = new ArrayList<>();
            Set<String> events = new HashSet<>();
            sscContractInfo.getSscContractAbis().forEach(contractAbi -> abis.add(contractAbi.getAbiName()));
            sscContractInfo.getSscContractEvents().forEach(contractEvent -> events.add(contractEvent.getEvent()));
            map1.put("contract_id", sscContractInfo.getContractId());
            map1.put("name", sscContractInfo.getName());
            map1.put("status", sscContractInfo.getStatus());
            String balance = DataUtils.getActualAmount(
                    Objects.isNull(sscContractInfo.getBalance()) ? "0" : sscContractInfo.getBalance().toString());
            map1.put("balance", balance);
            map1.put("reg_time", DateUtils.getTimeoneEight(sscContractInfo.getRegTime()));
            map1.put("owner_address", sscContractInfo.getOwnerAddress());
            map1.put("owner_name", sscContractInfo.getOwnerName());
            map1.put("hash", sscContractInfo.getHash());
            map1.put("description", sscContractInfo.getDescription());
            map1.put("coin", sscContractInfo.getType());
            map1.put("abis", abis);
            map1.put("events", events);
            String circulation = Objects.isNull(sscContractInfo.getCirculation()) ? "0" :
                    sscContractInfo.getCirculation().toString();
            map1.put("circulation", DataUtils.getActualAmount(circulation));
            //合约url配置
            List<SscContractConfig> list = getContractInfoQueryListUrlsFromCache(sscContractInfo);
            map1.put("contract_urls", list);
        }
        map.put("result", map1);
        return map;

    }

    /**
     * 合约模糊分页查询,地址查询余额接口,增加发行量字段
     */

    @GetMapping("/contract/query/{page}/{perPage}")
    public Map<String, Object> queryContractByKey(@RequestParam(required = false) String keyword,
                                                  @PathVariable Integer page, @PathVariable Integer perPage) {
        if (page < 1 || perPage < 1) {
            return getWebResultFail();
        }

        Integer contractLength = 30;
        //0-按合约地址查询,1-按合约名称模糊查询
        Integer queryType = 1;
        if (StringUtils.isNotEmpty(keyword) && keyword.startsWith(CONTRACT_PREFIX) &&
                keyword.length() > contractLength) {
            queryType = 0;
        }
        ContractInfoPageVO contractInfoPageVO = getContractByKeyFromCache(keyword, page, perPage, queryType);

        List<ContractInfoVO> result = new ArrayList<>();
        contractInfoPageVO.getSscContractInfoList().forEach(actContractInfo -> {
            String circulation =
                    Objects.isNull(actContractInfo.getCirculation()) ? "0" : actContractInfo.getCirculation().toString();
            ContractInfoVO contractInfoVO = ContractInfoVO.builder()
                    .coinType(actContractInfo.getCoinType())
                    .circulation(
                            new BigDecimal(DataUtils.getActualAmount(circulation)))
                    .coinAddress(actContractInfo.getOwnerAddress())
                    .contractName(actContractInfo.getName())
                    .registerTime(
                            DateUtils.getTimeoneEight(actContractInfo.getRegTime()))
                    .status(actContractInfo.getStatus())
                    .contractId(actContractInfo.getContractId())
                    .coin(Integer.parseInt(actContractInfo.getType().toString()))
                    .build();
            result.add(contractInfoVO);
        });

        ContractInfoPageVO resultPage = ContractInfoPageVO.builder()
                .contractInfoVOList(result)
                .currentPage(contractInfoPageVO.getCurrentPage())
                .pageSize(contractInfoPageVO.getPageSize())
                .totalPage(contractInfoPageVO.getTotalPage())
                .totalRecords(contractInfoPageVO.getTotalRecords())
                .build();
        Map<String, Object> map = getWebResultSuccess();
        map.put("data", resultPage);
        return map;
    }

    @GetMapping("/contract/query/forever")
    public Map<String, Object> queryForeverContractCoin() {
        String redisKey = Joiner.on(":").join("contractQueryForever", "coin");
        ContractInfoPageVO contractInfoPageVO = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), ContractInfoPageVO.class);
        if (Objects.isNull(contractInfoPageVO)) {
            contractInfoPageVO = iSscBrowserService.listAllForeverCoinType(ContractStatus.COIN_TRANSFER_FOREVER);
            stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(contractInfoPageVO), 10L, TimeUnit.SECONDS);
        }
        Map<String, Object> map = getWebResultSuccess();
        map.put("data", contractInfoPageVO);
        return map;
    }

    /**
     * /contract/query/{page}/{perPage}　接口缓存
     */
    private ContractInfoPageVO getContractByKeyFromCache(String keyword, Integer page, Integer perPage,
                                                         Integer queryType) {
        String redisKey = Joiner.on(":").join("queryContractByKey", keyword, ContractStatus.COIN_TRANSFER_FOREVER, page, perPage, queryType);
        ContractInfoPageVO contractInfoPageVO = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), ContractInfoPageVO.class);
        if (Objects.isNull(contractInfoPageVO)) {
            contractInfoPageVO = iSscBrowserService.listContractInfoByKey(keyword, ContractStatus.COIN_TRANSFER_FOREVER, page, perPage, queryType);
            if (Objects.nonNull(contractInfoPageVO)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(contractInfoPageVO), 10L, TimeUnit.SECONDS);
            }
        }
        return contractInfoPageVO;
    }

    /**
     * /ContractInfo.Query 接口缓存
     */
    private List<SscContractConfig> getContractInfoQueryListUrlsFromCache(SscContractInfo sscContractInfo) {
        String redisKey = Joiner.on(":").join("contractInfoQuery", "listUrlsByContractId", sscContractInfo.getContractId());
        List<SscContractConfig> list = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), new TypeReference<List<SscContractConfig>>() {
        });
        if (Objects.isNull(list) || list.size() == 0) {
            list = iSscBrowserService.listUrlsByContractId(sscContractInfo.getContractId());
            if (Objects.nonNull(list) && list.size() > 0) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(list), 10L, TimeUnit.SECONDS);
            }
        }
        return list;
    }

    /**
     * /ContractInfo.Query 接口缓存
     */
    private SscContractInfo getContractInfoQueryFromCache(String contractId) {
        String redisKey = Joiner.on(":").join("contractInfoQuery", "queryContractInfoByContractId", contractId);
        SscContractInfo sscContractInfo = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscContractInfo.class);
        if (Objects.isNull(sscContractInfo)) {
            sscContractInfo = iBrowserContractService.queryContractInfoByContractId(contractId);
            if (Objects.nonNull(sscContractInfo)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscContractInfo), 10L, TimeUnit.SECONDS);
            }
        }
        return sscContractInfo;
    }

    /**
     * /BlockInfo.Query 接口缓存
     */
    private SscBlock blockInfoQueryWithBlockNumFromCache(Long blockNum) {
        SscBlock sscBlock;
        String redisKey = Joiner.on(":").join("blockInfoQuery", blockNum);
        sscBlock = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscBlock.class);
        if (Objects.isNull(sscBlock)) {
            sscBlock = iBrowserBlockService.blockQueryByBlockNum(blockNum);
            if (Objects.nonNull(sscBlock)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscBlock), 10, TimeUnit.SECONDS);
            }
        }
        return sscBlock;
    }


    /**
     * /BlockInfo.Query 接口缓存
     */
    private SscBlock blockInfoQueryWithBlockIdFromCache(String blockId) {
        SscBlock sscBlock;
        String redisKey = Joiner.on(":").join("blockInfoQuery", blockId);
        sscBlock = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscBlock.class);
        if (Objects.isNull(sscBlock)) {
            sscBlock = iBrowserBlockService.blockQueryByBlockId(blockId);
            if (Objects.nonNull(sscBlock)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscBlock), 10, TimeUnit.SECONDS);
            }
        }
        return sscBlock;
    }

    /**
     * /TransactionInfo.Query 接口缓存 trx
     */
    private SscTransactionExPageVO getTransactionInfoExTrxQueryFromCache(Integer page, Integer perPage,
                                                                         SscTransaction sscTransaction) {
        String redisKey = Joiner.on(":").join("TransactionInfoQuery", "extraTransaction", sscTransaction.getExtraTrxId(), page, perPage);
        SscTransactionExPageVO sscTransactionExPageVO = JSONObject
                .parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscTransactionExPageVO.class);
        if (Objects.isNull(sscTransactionExPageVO)) {
            sscTransactionExPageVO = iBrowserTransactionService.transactionInfoExQuery(sscTransaction.getExtraTrxId(), page, perPage);
            if (Objects.nonNull(sscTransactionExPageVO)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscTransactionExPageVO), 10L, TimeUnit.SECONDS);
            }

        }
        return sscTransactionExPageVO;
    }

    /**
     * /TransactionInfo.Query 接口缓存 ExTrx
     */
    private SscTransaction getTransactionInfoTrxQueryFromCache(String trxId) {
        String redisKey = Joiner.on(":").join("TransactionInfoQuery", "transaction", trxId);
        SscTransaction sscTransaction = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscTransaction.class);
        if (Objects.isNull(sscTransaction)) {
            sscTransaction = iBrowserTransactionService.transactionInfoQuery(trxId);
            if (Objects.nonNull(sscTransaction)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscTransaction), 10L, TimeUnit.SECONDS);
            }
        }
        return sscTransaction;
    }

    /**
     * /BlockAgent.Query 接口缓存
     */
    private PageResult<SscBlockDto> getBlockAgentQueryFromCache(String signee, Integer page, Integer perPage) {
        PageResult<SscBlockDto> result;
        String redisKey = Joiner.on(":").join("BlockAgentQuery", signee, page, perPage);
        result = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), new TypeReference<PageResult<SscBlockDto>>() {
        });
        if (Objects.isNull(result)) {
            result = iBrowserBlockService.blockQuery(signee, page, perPage);
            if (Objects.nonNull(result)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(result), 10L, TimeUnit.SECONDS);
            }
        }
        return result;
    }

    /**
     * /Query.Type　 接口缓存
     * accountName
     */
    private UserAddress getQueryTypeWithAccountNameKeywordFromCache(String keyword) {
        String redisKey = Joiner.on(":").join("QueryType", "getUserAddressByAccountName", keyword);
        UserAddress userAddress = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), UserAddress.class);
        if (Objects.isNull(userAddress)) {
            userAddress = iBrowserBlockService.getUserAddressByAccountName(keyword);
            if (Objects.nonNull(userAddress)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(userAddress), 10L, TimeUnit.SECONDS);
            }
        }
        return userAddress;
    }

    /**
     * /Query.Type　 接口缓存
     * BlockId
     */
    private SscBlock getQueryTypeWithBlockIdKeywordFromCache(String keyword) {
        String redisKey = Joiner.on(":").join("QueryType", "getBlockNumByBlockId", keyword);
        SscBlock actblock = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscBlock.class);
        if (Objects.isNull(actblock)) {
            actblock = iBrowserBlockService.getBlockNumByBlockId(keyword);
            if (Objects.nonNull(actblock)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(actblock), 10L, TimeUnit.SECONDS);
            }
        }
        return actblock;
    }

    /**
     * /Query.Type　 接口缓存
     * NumericKeyword
     */
    private SscBlock getQueryTypeWithNumericKeywordFromCache(long key) {
        String redisKey = Joiner.on(":").join("QueryType", "Numeric", key);
        SscBlock sscBlock = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), SscBlock.class);
        if (Objects.isNull(sscBlock)) {
            sscBlock = iBrowserBlockService.blockQueryByBlockNum(key);
            if (Objects.nonNull(sscBlock)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(sscBlock), 10L, TimeUnit.SECONDS);
            }
        }
        return sscBlock;
    }

    /**
     * /Block.Query 接口缓存
     */
    private PageResult<SscBlockDto> getBlockQueryFromCache(Integer page, Integer perPage) {
        String redisKey = Joiner.on(":").join("blockQuery", page, perPage);
        PageResult<SscBlockDto> pageResult;
        pageResult = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), new TypeReference<PageResult<SscBlockDto>>() {
        });
        if (Objects.isNull(pageResult)) {
            pageResult = iBrowserBlockService.blockQuery(null, page, perPage);
            if (Objects.nonNull(pageResult)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(pageResult), 10, TimeUnit.SECONDS);
            }
        }
        return pageResult;
    }

    private Map<String, Object> getWebResultFail() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 1001);
        map.put("msg", "系统错误，请稍后再试");
        return map;
    }

    private Map<String, Object> getWebResultSuccess() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 200);
        map.put("msg", "OK");
        return map;
    }

    /**
     * /Transaction.Query 接口缓存
     */
    private PageResult<SscTransactionDto> getTransactionQueryFromCache(Integer page, Integer perPage) {
        String redisKey = Joiner.on(":").join("TransactionQuery", page, perPage);
        PageResult<SscTransactionDto> pageResult;
        pageResult = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(redisKey), new TypeReference<PageResult<SscTransactionDto>>() {
        });
        if (Objects.isNull(pageResult)) {
            pageResult = iBrowserTransactionService.transactionListQuery(0, null, page, perPage);
            if (Objects.nonNull(pageResult)) {
                stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(pageResult), 10L, TimeUnit.SECONDS);
            }
        }
        return pageResult;
    }

}
