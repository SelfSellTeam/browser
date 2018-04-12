package com.achain.service;

import com.achain.domain.dto.SscBlockDto;
import com.achain.domain.dto.SscStatisticsDto;
import com.achain.domain.dto.PageResult;
import com.achain.domain.entity.SscBlock;
import com.achain.domain.entity.UserAddress;

import java.util.List;

/**
 * 浏览器相关区块信息
 */

public interface IBrowserBlockService {

    /**
     * 查询区块信息
     * @param per_page 每页显示
     * @return
     */
    PageResult<SscBlockDto> blockQuery(String signee, Integer page, Integer per_page);

    /**
     * 根据块的hash值查询块信息
     * @param block_id
     * @return
     */

    SscBlock blockQueryByBlockId(String block_id);

    /**
     * 根据块号查询块信息
     * @param block_num
     * @return
     */

    SscBlock blockQueryByBlockNum(Long block_num);

    /**
     * 查询字段是否为区块hash值
     * @param hash
     * @return
     */
    SscBlock getBlockNumByBlockId(String hash);


    /**
     * 统计数据
     * @return
     */
    SscStatisticsDto getStatisticsAllData();

    UserAddress getUserAddressByAccountName(String accountName);

    /**
     *
     * @param userAddress
     * @return
     */
    List<UserAddress> getUserAddressByAddress(String userAddress, String coinType);
}
