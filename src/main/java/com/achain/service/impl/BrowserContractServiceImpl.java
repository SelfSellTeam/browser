package com.achain.service.impl;

import com.achain.utils.DataUtils;
import com.achain.utils.DateUtils;
import com.google.common.base.Strings;
import com.achain.service.IBrowserContractService;
import com.achain.mapper.*;
import com.achain.domain.dto.*;
import com.achain.domain.entity.*;
import com.achain.domain.enums.ContractCoinType;
import com.achain.domain.enums.TrxType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrowserContractServiceImpl implements IBrowserContractService {

    @Autowired
    private SscContractInfoMapper sscContractInfoMapper;

    @Autowired
    private SscContractAbiMapper sscContractAbiMapper;

    @Autowired
    private SscContractEventMapper sscContractEventMapper;

    @Autowired
    private SscTransactionExMapper sscTransactionExMapper;

    @Autowired
    private SscTransactionMapper sscTransactionMapper;

    @Autowired
    SscBlockMapper sscBlockMapper;

    @Override
    public SscContractInfo queryContractInfoByContractId(String contract_id) {
        SscContractInfoCriteria sscContractInfoCriteria = new SscContractInfoCriteria();
        sscContractInfoCriteria.createCriteria().andContractIdEqualTo(contract_id);
        sscContractInfoCriteria.setOffSet(0);
        sscContractInfoCriteria.setPageSize(1);
        List<SscContractInfo> sscContractInfoList = sscContractInfoMapper.selectByExample(sscContractInfoCriteria);
        if (!CollectionUtils.isEmpty(sscContractInfoList)) {
            SscContractInfo sscContractInfo = sscContractInfoList.get(0);
            SscContractAbiCriteria sscContractAbiCriteria = new SscContractAbiCriteria();
            sscContractAbiCriteria.createCriteria().andContractIdEqualTo(contract_id);
            List<SscContractAbi> sscContractAbiList = sscContractAbiMapper.selectByExample(sscContractAbiCriteria);
            SscContractEventCriteria sscContractEventCriteria = new SscContractEventCriteria();
            sscContractEventCriteria.createCriteria().andContractIdEqualTo(contract_id);
            List<SscContractEvent> sscContractEventList = sscContractEventMapper.selectByExample(sscContractEventCriteria);
            sscContractInfo.setSscContractAbis(sscContractAbiList);
            sscContractInfo.setSscContractEvents(sscContractEventList);
            return sscContractInfo;
        } else {
            return null;
        }
    }

    @Override
    public PageResult<SscContractInfoDto> ContractListQuery(String keyword, Integer page, Integer per_page) {
        SscContractInfoCriteria sscContractInfoCriteria = new SscContractInfoCriteria();
        if (!Strings.isNullOrEmpty(keyword)) {
            sscContractInfoCriteria.or().andNameEqualTo(keyword);
            sscContractInfoCriteria.or().andContractIdEqualTo(keyword);
            sscContractInfoCriteria.or().andOwnerNameEqualTo(keyword);
            sscContractInfoCriteria.or().andOwnerAddressEqualTo(keyword);
            sscContractInfoCriteria.or().andDescriptionLike(keyword);
        }
        int size = sscContractInfoMapper.countByExample(sscContractInfoCriteria);
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        sscContractInfoCriteria.setOffSet(pageInfo.getOffset());
        sscContractInfoCriteria.setPageSize(per_page);
        List<SscContractInfo> sscContractInfoList = sscContractInfoMapper.selectByExample(sscContractInfoCriteria);
        List<SscContractInfoDto> sscContractInfoDtoList = new ArrayList<>();
        sscContractInfoList.stream().forEach(s -> sscContractInfoDtoList.add(
                SscContractInfoDto.Builder.anActContractInfoDto()
                        .id(s.getId())
                        .contract_id(s.getContractId())
                        .name(s.getName())
                        .status(s.getStatus())
                        .owner_address(s.getOwnerAddress())
                        .owner_name(s.getOwnerName())
                        .balance(s.getBalance())
                        .build()
        ));
        return new PageResult(page, per_page, sscContractInfoDtoList, size);
    }

    @Override
    public PageResult<SscTransactionExDto> transactionExQuery(String orig_trx_id, Integer page, Integer per_page) {
        SscTransactionExCriteria sscTransactionExCriteria = new SscTransactionExCriteria();
        if (!Strings.isNullOrEmpty(orig_trx_id)) {
            sscTransactionExCriteria.createCriteria().andOrigTrxIdEqualTo(orig_trx_id);
        }
        int size = sscTransactionExMapper.countByExample(sscTransactionExCriteria);
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        sscTransactionExCriteria.setOffSet(pageInfo.getOffset());
        sscTransactionExCriteria.setPageSize(per_page);
        List<SscTransactionEx> sscTransactionExList = sscTransactionExMapper.selectByExample(sscTransactionExCriteria);
        List<SscTransactionExDto> sscTransactionExDtoList = new ArrayList<>();
        sscTransactionExList.stream().forEach(s -> sscTransactionExDtoList.add(
                SscTransactionExDto.Builder.anActTransactionExDto()
                        .id(s.getId())
                        .to_addr(s.getToAddr())
                        .to_acct(s.getToAcct())
                        .amount(DataUtils.getActualAmount(s.getAmount() + ""))
                        .fee(DataUtils.getActualAmount(String.valueOf(s.getFee())) + "")
                        .build()
        ));
        return new PageResult(page, per_page, sscTransactionExDtoList, size);
    }

    @Override
    public PageResult<SscTransactionForWalletDto> contractTransaction(String contract_id, String acct_address, int type, Integer page, Integer per_page) {
        SscTransactionCriteria sscTransactionCriteria = new SscTransactionCriteria();
        SscTransactionCriteria.Criteria criteria = sscTransactionCriteria.createCriteria();
        if (!Strings.isNullOrEmpty(acct_address)) {
            if (type == 0) {
                if (acct_address.length() > 64) {
                    criteria.andSubAddressEqualTo(acct_address).andContractIdEqualTo(contract_id);
                } else {
                    sscTransactionCriteria.or().andFromAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                    sscTransactionCriteria.or().andToAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                }
            } else if (type == 1) {
                sscTransactionCriteria.or().andFromAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
            } else if (type == 2) {
                if (acct_address.length() > 64) {
                    criteria.andSubAddressEqualTo(acct_address).andContractIdEqualTo(contract_id);
                } else {
                    sscTransactionCriteria.or().andToAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                }
            } else {
                return null;
            }
        }
        int size = sscTransactionMapper.countByExample(sscTransactionCriteria);
        sscTransactionCriteria.setOrderByClause("id desc");
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        sscTransactionCriteria.setOffSet(pageInfo.getOffset());
        sscTransactionCriteria.setPageSize(per_page);
        List<SscTransaction> sscTransactionList = sscTransactionMapper.selectByExample(sscTransactionCriteria);
        List<SscTransactionForWalletDto> actTransactionDtoList = new ArrayList<>();
        sscTransactionList.forEach(s -> actTransactionDtoList.add(
            SscTransactionForWalletDto.Builder.anActTransactionForWalletDto()
                                              .id(s.getId())
                                              .block_num(s.getBlockNum())
                                              .trx_id(s.getTrxId())
                                              .from_acct(s.getFromAcct())
                                              .from_addr(s.getFromAddr())
                                              .to_acct(s.getToAcct())
                                              .to_addr(StringUtils.isEmpty(s.getToAddr()) ? s.getContractId() :
                                                       (StringUtils.isEmpty(s.getSubAddress()) ? s.getToAddr() :
                                                        s.getSubAddress()))
                                              .called_abi(s.getCalledAbi())
                                              .trx_type(s.getTrxType().toString())
                                              .amount(DataUtils.getActualAmount(s.getAmount() + ""))
                                              .trx_time(DateUtils.getTimeoneEight(s.getTrxTime()))
                                              .is_completed(s.getIsCompleted() + "")
                                              .coinType(s.getCoinType())
                                              .trade_Describe(getTradeDescribe(s))
                                              .eventParam(s.getEventParam())
                                              .eventType(s.getEventType())
                                              .fee(DataUtils.getActualAmount(s.getFee() + ""))
                                              .memo(s.getMemo())
                                              .build()
        ));
        return new PageResult(page, per_page, actTransactionDtoList, size);
    }

    @Override
    public PageResult<SscTransactionForWalletDto> contractTransaction2(String contract_id, String acct_address,
                                                                       int type, Integer page, Integer per_page) {
        SscTransactionCriteria sscTransactionCriteria = new SscTransactionCriteria();
        SscTransactionCriteria.Criteria criteria = sscTransactionCriteria.createCriteria();
        if (!Strings.isNullOrEmpty(acct_address)) {
            if (type == 0) {
                if (acct_address.length() > 64) {
                    if (StringUtils.isEmpty(contract_id)) {
                        criteria.andSubAddressEqualTo(acct_address).andContractIdIsNull();
                    } else {
                        criteria.andSubAddressEqualTo(acct_address).andContractIdEqualTo(contract_id);
                    }
                } else {
                    if (StringUtils.isEmpty(contract_id)) {
                        sscTransactionCriteria.or().andFromAddrEqualTo(acct_address).andContractIdIsNull();
                        sscTransactionCriteria.or().andToAddrEqualTo(acct_address).andContractIdIsNull();
                    } else {
                        sscTransactionCriteria.or().andFromAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                        sscTransactionCriteria.or().andToAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                    }
                }
            } else if (type == 1) {
                sscTransactionCriteria.or().andFromAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
            } else if (type == 2) {
                if (acct_address.length() > 64) {
                    criteria.andSubAddressEqualTo(acct_address).andContractIdEqualTo(contract_id);
                } else {
                    sscTransactionCriteria.or().andToAddrEqualTo(acct_address).andContractIdEqualTo(contract_id);
                }
            } else {
                return null;
            }
        }
        int size = sscTransactionMapper.countByExample(sscTransactionCriteria);
        sscTransactionCriteria.setOrderByClause("id desc");
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        sscTransactionCriteria.setOffSet(pageInfo.getOffset());
        sscTransactionCriteria.setPageSize(per_page);
        List<SscTransaction> sscTransactionList = sscTransactionMapper.selectByExample(sscTransactionCriteria);
        List<SscTransactionForWalletDto> actTransactionDtoList = new ArrayList<>();
        sscTransactionList.forEach(s -> actTransactionDtoList.add(
            SscTransactionForWalletDto.Builder.anActTransactionForWalletDto()
                                              .id(s.getId())
                                              .block_num(s.getBlockNum())
                                              .trx_id(s.getTrxId())
                                              .from_acct(s.getFromAcct())
                                              .from_addr(s.getFromAddr())
                                              .to_acct(s.getToAcct())
                                              .to_addr(StringUtils.isEmpty(s.getToAddr()) ? s.getContractId() :
                                                       (StringUtils.isEmpty(s.getSubAddress()) ? s.getToAddr() :
                                                        s.getSubAddress()))
                                              .called_abi(s.getCalledAbi())
                                              .trx_type(s.getTrxType().toString())
                                              .amount(DataUtils.getActualAmount(s.getAmount() + ""))
                                              .trx_time(DateUtils.getTimeoneEight(s.getTrxTime()))
                                              .is_completed(s.getIsCompleted() + "")
                                              .coinType(s.getCoinType())
                                              .trade_Describe(getTradeDescribe(s))
                                              .eventParam(s.getEventParam())
                                              .eventType(s.getEventType())
                                              .fee(DataUtils.getActualAmount(s.getFee() + ""))
                                              .memo(s.getMemo())
                                              .build()
                                                                 ));
        return new PageResult(page, per_page, actTransactionDtoList, size);
    }

    /**
     * DESTROY_STATE = 0 TEMP_STATE = 1 FOREVER_STATE = 2
     * @param status
     * @return
     */
    @Override
    public List<SscContractInfo> getAllContracts(int status) {
        SscContractInfoCriteria criteria = new SscContractInfoCriteria();
        criteria.createCriteria().andStatusEqualTo(status).andTypeEqualTo((byte) 1);
        List<SscContractInfo> list  = sscContractInfoMapper.selectByExample(criteria);
        List<SscContractInfo> list1 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach(actContractInfo -> {
                actContractInfo.setBytecode("");
                actContractInfo.setName(actContractInfo.getCoinType());
                list1.add(actContractInfo);
            });
        }
        return list1;
    }

    @Override
    public List<SscContractInfo> getAllContracts2(int status) {
        SscContractInfoCriteria criteria = new SscContractInfoCriteria();
        criteria.createCriteria().andStatusEqualTo(status).andTypeEqualTo((byte) 1);
        return sscContractInfoMapper.selectByExample(criteria);
    }

    /**
     * 返回交易描述信息
     *
     * @return
     */
    private String getTradeDescribe(SscTransaction sscTransaction) {
        if (StringUtils.isEmpty(sscTransaction.getCoinType())){
            sscTransaction.setCoinType("ACT");
        }
        if (sscTransaction.getTrxType() == TrxType.TRX_TYPE_TRANSFER.getIntKey()) {
            return sscTransaction.getCoinType() + ContractCoinType.getCoinQueryType("").getDesc();
        }
        if (sscTransaction.getTrxType() == TrxType.TRX_TYPE_CALL_CONTRACT.getIntKey()) {
            return sscTransaction.getCoinType() + ContractCoinType.getCoinQueryType(sscTransaction.getCalledAbi()).getDesc();
        }
        return TrxType.getTrxType(sscTransaction.getTrxType()).getDesc();
    }
}
