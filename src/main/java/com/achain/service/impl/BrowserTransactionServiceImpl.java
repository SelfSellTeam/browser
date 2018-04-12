package com.achain.service.impl;

import com.achain.domain.entity.SscTransaction;
import com.achain.domain.entity.SscTransactionEx;
import com.achain.service.IBrowserTransactionService;
import com.achain.utils.DataUtils;
import com.achain.utils.DateUtils;
import com.achain.mapper.SscTransactionExMapper;
import com.achain.mapper.SscTransactionMapper;
import com.achain.domain.dto.*;
import com.achain.domain.entity.SscTransactionCriteria;
import com.achain.domain.entity.SscTransactionExCriteria;
import com.achain.domain.enums.ContractCoinType;
import com.achain.domain.enums.TrxType;
import com.achain.domain.vo.SscTransactionExPageVO;
import com.achain.domain.vo.SscTransactionExVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrowserTransactionServiceImpl implements IBrowserTransactionService {


    private final SscTransactionMapper sscTransactionMapper;

    private final SscTransactionExMapper sscTransactionExMapper;


    @Autowired
    public BrowserTransactionServiceImpl(SscTransactionMapper sscTransactionMapper,
                                         SscTransactionExMapper sscTransactionExMapper) {
        this.sscTransactionMapper = sscTransactionMapper;
        this.sscTransactionExMapper = sscTransactionExMapper;
    }

    /**
     * 交易相关信息查询
     */

    @Override
    public PageResult<SscTransactionDto> transactionListQuery(long block_num, String acctAddress, Integer page, Integer per_page) {
        SscTransactionAddrDto actTransactionAddr = new SscTransactionAddrDto();
        if (block_num != 0) {
            actTransactionAddr.setBlockNum(block_num);
        }
        if (!StringUtils.isEmpty(acctAddress)) {
            if (acctAddress.length() > 64) {
                actTransactionAddr.setSubAddress(acctAddress);
            } else if (acctAddress.startsWith("CON")) {
                actTransactionAddr.setContractId(acctAddress);
            } else {
                actTransactionAddr.setAddress(acctAddress);
            }
        }
        int size = sscTransactionMapper.countByExampleFromAddr(actTransactionAddr);
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        actTransactionAddr.setPageSize(per_page);
        actTransactionAddr.setOffSet(pageInfo.getOffset());
        List<SscTransaction> sscTransactionList = sscTransactionMapper.selectByExampleFromAddr(actTransactionAddr);

        List<SscTransactionDto> sscTransactionDtoList = new ArrayList<>();
        sscTransactionList.forEach(s -> {
            SscTransactionDto dto = SscTransactionDto.Builder.anActTransactionDto()
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
                    .trx_type(s.getTrxType() + "")
                    .amount(DataUtils.getActualAmount(s.getAmount() + ""))
                    .trx_time(DateUtils.getTimeoneEight(s.getTrxTime()))
                    .is_completed(s.getIsCompleted() + "")
                    .coinType(!StringUtils.isEmpty(s.getCoinType()) &&
                            s.getCoinType().length() > 6 ? "" : s.getCoinType())
                    .trade_Describe(getTradeDescribe(s))
                    .contractId(s.getContractId())
                    .eventType(s.getEventType())
                    .eventParam(s.getEventParam())
                    .trx_elapsed_time(DateUtils.calcElapsedTime(s.getTrxTime()))
                    .build();
            sscTransactionDtoList.add(dto);
        });
        return new PageResult(page, per_page, sscTransactionDtoList, size);
    }

//  @Override
//  public PageResult<KcashActTransactionDto> transactionListQueryWithEx(long blockNum, String coinType,String acctAddress,
//                                                                       Integer page, Integer perPage) {
//    SscTransactionAddrDto actTransactionAddr = new SscTransactionAddrDto();
//    if (blockNum != 0) {
//      actTransactionAddr.setBlockNum(blockNum);
//    }
//    if (!StringUtils.isEmpty(acctAddress)) {
//      if (acctAddress.length() > 64) {
//        actTransactionAddr.setSubAddress(acctAddress);
//      }else if (acctAddress.startsWith("CON")) {
//        actTransactionAddr.setContractId(acctAddress);
//      }else {
//        actTransactionAddr.setAddress(acctAddress);
//      }
//    }
//
//    if(StringUtils.isNotEmpty(coinType)){
//      actTransactionAddr.setCoinType(coinType);
//    }
//    int size = sscTransactionMapper.countByExampleFromAddrAndCoinType(actTransactionAddr);
//    PageInfo pageInfo = PageInfo.fromPageAndSize(page, perPage);
//    actTransactionAddr.setPageSize(perPage);
//    actTransactionAddr.setOffSet(pageInfo.getOffset());
//    List<SscTransaction> actTransactionList = sscTransactionMapper.selectByExampleFromAddrAndCoinType(actTransactionAddr);
//
//    List<KcashActTransactionDto> actTransactionDtoList = new ArrayList<>();
//    actTransactionList.forEach(s -> {
//      KcashActTransactionDto dto = KcashActTransactionDto.builder()
//                                                         .id(s.getId())
//                                                         .block_num(s.getBlockNum())
//                                                         .trx_id(s.getTrxId())
//                                                         .from_acct(s.getFromAcct())
//                                                         .from_addr(s.getFromAddr())
//                                                         .to_acct(s.getToAcct())
//                                                         .to_addr(StringUtils.isEmpty(s.getToAddr()) ? s.getContractId() :
//                                                        (StringUtils.isEmpty(s.getSubAddress()) ? s.getToAddr() :
//                                                         s.getSubAddress()))
//                                                         .called_abi(s.getCalledAbi())
//                                                         .trx_type(s.getTrxType() + "")
//                                                         .amount(DataUtils.getActualAmount(s.getAmount() + ""))
//                                                         .trx_time(DateUtils.getTimeoneEight(s.getTrxTime()))
//                                                         .is_completed(s.getIsCompleted() + "")
//                                                         .coinType(!StringUtils.isEmpty(s.getCoinType()) && s.getCoinType().length() > 6 ? "" : s.getCoinType())
//                                                         .trade_Describe(getTradeDescribe(s))
//                                                         .contractId(s.getContractId())
//                                                         .eventType(s.getEventType())
//                                                         .eventParam(s.getEventParam())
//                                                         .fee(DataUtils.getActualAmount(String.valueOf(s.getFee())) + "")
//                                                         .build();
//      if (StringUtils.isNotEmpty(s.getExtraTrxId())) {
//        List<SscTransactionExVO> actTransactionExVOList = transactionInfoExQueryAll(s.getExtraTrxId());
//        dto.setActTransactionExVOList(actTransactionExVOList);
//      }
//      actTransactionDtoList.add(dto);
//    });
//    return  new PageResult(page, perPage, actTransactionDtoList, size);
//  }

    /**
     * 返回交易描述信息
     *
     * @return
     */
    private String getTradeDescribe(SscTransaction sscTransaction) {
        if (StringUtils.isEmpty(sscTransaction.getCoinType())) {
            sscTransaction.setCoinType("ACT");
        }
        String coinType = sscTransaction.getCoinType().length() > 6 ? sscTransaction.getCoinType().substring(0, 5) : sscTransaction.getCoinType();
        if (sscTransaction.getTrxType() == TrxType.TRX_TYPE_TRANSFER.getIntKey()) {
            return coinType + ContractCoinType.getCoinQueryType("").getDesc();
        }
        if (sscTransaction.getTrxType() == TrxType.TRX_TYPE_CALL_CONTRACT.getIntKey()) {
            return coinType + ContractCoinType.getCoinQueryType(sscTransaction.getCalledAbi()).getDesc();
        }
        return TrxType.getTrxType(sscTransaction.getTrxType()).getDesc();
    }


    /**
     * 根据交易单号查询交易详细信息
     */

    @Override
    public SscTransaction transactionInfoQuery(String trx_id) {
        SscTransactionCriteria actCriteria = new SscTransactionCriteria();
        actCriteria.setOffSet(0);
        actCriteria.setPageSize(1);
        actCriteria.createCriteria().andTrxIdEqualTo(trx_id);
        List<SscTransaction> actBlock = sscTransactionMapper.selectByExample(actCriteria);
        return CollectionUtils.isEmpty(actBlock) ? null : actBlock.get(0);
    }

    @Override
    public SscTransactionExPageVO transactionInfoExQuery(String extraTrxId, Integer page, Integer pageSize) {
        SscTransactionExCriteria actCriteria = new SscTransactionExCriteria();
        actCriteria.setOffSet(page - 1);
        actCriteria.setPageSize(pageSize);
        actCriteria.createCriteria().andTrxIdEqualTo(extraTrxId);
        int totalRecords = sscTransactionExMapper.countByExample(actCriteria);
        SscTransactionExPageVO sscTransactionExPageVO = new SscTransactionExPageVO();
        sscTransactionExPageVO.setCurrentPage(page);
        sscTransactionExPageVO.setPageSize(pageSize);
        sscTransactionExPageVO.setTotalRecords(totalRecords);
        Integer totalPage = totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;
        sscTransactionExPageVO.setTotalPage(totalPage);
        if (totalRecords == 0) {
            sscTransactionExPageVO.setActTransactionExList(new ArrayList<>());
            return sscTransactionExPageVO;
        }
        List<SscTransactionEx> sscTransactionExList = sscTransactionExMapper.selectByExample(actCriteria);
        List<SscTransactionExVO> sscTransactionExVOList = new ArrayList<>(sscTransactionExList.size());
        sscTransactionExList.forEach(actTransactionEx -> {
            SscTransactionExVO sscTransactionExVO = SscTransactionExVO.builder()
                    .fromAddr(actTransactionEx.getFromAddr())
                    .toAddr(actTransactionEx.getToAddr())
                    .trxId(actTransactionEx.getTrxId())
                    .amount(DataUtils.getActualAmount(actTransactionEx.getAmount() + ""))
                    .build();
            sscTransactionExVOList.add(sscTransactionExVO);
        });
        sscTransactionExPageVO.setActTransactionExList(sscTransactionExVOList);
        return sscTransactionExPageVO;

    }

    @Override
    public List<SscTransactionExVO> transactionInfoExQueryAll(String extraTrxId) {
        SscTransactionExCriteria actCriteria = new SscTransactionExCriteria();
        actCriteria.createCriteria().andTrxIdEqualTo(extraTrxId);
        List<SscTransactionEx> sscTransactionExList = sscTransactionExMapper.selectByExample(actCriteria);
        List<SscTransactionExVO> sscTransactionExVOList = new ArrayList<>(sscTransactionExList.size());
        sscTransactionExList.forEach(actTransactionEx -> {
            SscTransactionExVO sscTransactionExVO = SscTransactionExVO.builder()
                    .fromAddr(actTransactionEx.getFromAddr())
                    .toAddr(actTransactionEx.getToAddr())
                    .trxId(actTransactionEx.getTrxId())
                    .amount(DataUtils.getActualAmount(actTransactionEx.getAmount() + ""))
                    .build();
            sscTransactionExVOList.add(sscTransactionExVO);
        });
        return sscTransactionExVOList;
    }

    /**
     * 查询是否为交易单号
     */

    @Override
    public boolean isTrxId(String trx_id) {
        SscTransactionCriteria sscTransactionCriteria = new SscTransactionCriteria();
        sscTransactionCriteria.setOffSet(0);
        sscTransactionCriteria.setPageSize(1);
        sscTransactionCriteria.createCriteria().andTrxIdEqualTo(trx_id);
        List<SscTransaction> sscTransactionList = sscTransactionMapper.selectByExample(sscTransactionCriteria);
        return sscTransactionList.isEmpty();
    }

    @Override
    public List<SscTransactionForSubDto> statisticsFromSub(SscStatisticsParamDto sscStatisticsParamDto) {
        return sscTransactionMapper.statisticsFromSub(sscStatisticsParamDto);
    }
}
