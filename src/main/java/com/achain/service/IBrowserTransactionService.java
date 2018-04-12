package com.achain.service;

import com.achain.domain.dto.*;
import com.achain.domain.entity.SscTransaction;
import com.achain.domain.vo.SscTransactionExPageVO;
import com.achain.domain.vo.SscTransactionExVO;

import java.util.List;

public interface IBrowserTransactionService {

    /**
     * 根据交易单号查询交易详细信息
     *
     * @param trx_id
     * @return
     */

    SscTransaction transactionInfoQuery(String trx_id);

    /**
     * 查询制定区块上交易的详细信息
     *
     * @param block_num
     * @param page
     * @param per_page
     * @return
     */

    PageResult<SscTransactionDto> transactionListQuery(long block_num, String acctAddress, Integer page, Integer per_page);

//    PageResult<KcashActTransactionDto> transactionListQueryWithEx(long blockNum, String coinType, String acctAddress, Integer page, Integer perPage);



    /**
     * 查询是否为交易单号
     * @param trx_id
     * @return
     */
    boolean isTrxId(String trx_id);

    /**
     * 统计子账户一个时间总额度
     * @param sscStatisticsParamDto
     * @return
     */
    List<SscTransactionForSubDto> statisticsFromSub(SscStatisticsParamDto sscStatisticsParamDto);

    /**
     * 查询子交易
     * @param extraTrxId　trxId
     * @param page 第几页
     * @param pageSize　每页多少条
     * @return
     */
    SscTransactionExPageVO transactionInfoExQuery(String extraTrxId, Integer page, Integer pageSize);


    /**
     * 查询子交易
     * @param extraTrxId trxId
     * @return 全有相关的子交易
     */
    List<SscTransactionExVO> transactionInfoExQueryAll(String extraTrxId);

}
