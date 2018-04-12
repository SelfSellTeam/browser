package com.achain.service;

import com.achain.domain.dto.SscContractInfoDto;
import com.achain.domain.dto.SscTransactionExDto;
import com.achain.domain.dto.SscTransactionForWalletDto;
import com.achain.domain.dto.PageResult;
import com.achain.domain.entity.SscContractInfo;

import java.util.List;

public interface IBrowserContractService {

    /**
     * 根据合约号查询合约详细信息
     * @param contract_id
     * @return
     */

    SscContractInfo queryContractInfoByContractId(String contract_id);

    /**
     * 查询合约相关信息
     * @param keyword
     * @param page
     * @param per_page
     * @return
     */

    PageResult<SscContractInfoDto> ContractListQuery(String keyword, Integer page, Integer per_page);

    /**
     * 查询合约出账相关信息
     * @param orig_trx_id
     * @param page
     * @param per_page
     * @return
     */

    PageResult<SscTransactionExDto> transactionExQuery(String orig_trx_id, Integer page, Integer per_page);

    /**
     * 根据块号，地址和资产类型对交易进行分页查询
     * @param contract_id
     * @param acct_address
     * @param page
     * @param per_page
     * @return
     */
    PageResult<SscTransactionForWalletDto> contractTransaction(String contract_id, String acct_address, int type, Integer page, Integer per_page);


    /**
     * 查询永久合约
     * @return
     */
    List<SscContractInfo> getAllContracts(int status);


    /**
     * 只供果仁宝调用 查询永久合约
     */
    List<SscContractInfo> getAllContracts2(int status);


    PageResult<SscTransactionForWalletDto> contractTransaction2(String contract_id, String acct_address, int type,
                                                                Integer page, Integer per_page);
}
