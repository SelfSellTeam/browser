package com.achain.service.impl;

import com.achain.domain.dto.SscStatisticsDto;
import com.achain.domain.entity.SscBlock;
import com.achain.domain.entity.SscBlockCriteria;
import com.achain.domain.entity.UserAddressCriteria;
import com.achain.mapper.SscBlockMapper;

import com.achain.domain.dto.SscBlockDto;
import com.achain.domain.dto.PageInfo;
import com.achain.domain.dto.PageResult;
import com.achain.domain.entity.UserAddress;
import com.achain.mapper.UserAddressMapper;
import com.achain.service.IBrowserBlockService;
import com.achain.utils.DataUtils;
import com.achain.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxiaohui
 */
@Service
public class BrowserBlockServiceImpl implements IBrowserBlockService {

    @Autowired
    private SscBlockMapper sscBlockMapper;

    @Autowired
    private RedisTemplate<String, SscStatisticsDto> redisTemplate;

  @Autowired
  private UserAddressMapper userAddressMapper;

    /**
     * 查询区块信息
     *
     * @param per_page 每页显示
     */
    @Override
    public PageResult<SscBlockDto> blockQuery(String signee, Integer page, Integer per_page) {
        SscBlockCriteria sscBlockCriteria = new SscBlockCriteria();
        if (!StringUtils.isEmpty(signee)) {
            sscBlockCriteria.createCriteria().andSigneeEqualTo(signee);
        }
        int size = sscBlockMapper.countByExample(sscBlockCriteria);
        sscBlockCriteria.setOrderByClause("id desc");
        PageInfo pageInfo = PageInfo.fromPageAndSize(page, per_page);
        sscBlockCriteria.setOffSet(pageInfo.getOffset());
        sscBlockCriteria.setPageSize(per_page);
        List<SscBlock> sscBlockList = sscBlockMapper.selectByExample(sscBlockCriteria);
        List<SscBlockDto> sscBlockDtoList = new ArrayList<>();
        sscBlockList.stream().forEach(s -> sscBlockDtoList.add(SscBlockDto.Builder.anActBlockDto()
                .id(s.getId())
                .blockId(s.getBlockId())
                .block_num(s.getBlockNum())
                .block_time(DateUtils.getTimeoneEight(s.getBlockTime()))
                .trans_num(s.getTransNum())
                .trans_amount(DataUtils.getActualAmount(s.getTransAmount() + ""))
                .signee(s.getSignee())
                .block_size(s.getBlockSize())
                .blockElapsedTime(DateUtils.calcElapsedTime(s.getBlockTime()))
                .build()));

        return new PageResult(page, per_page, sscBlockDtoList, size);
    }

    /**
     * 根据块的hash值查询块信息
     */
    @Override
    public SscBlock blockQueryByBlockId(String block_id) {
        SscBlockCriteria sscBlockCriteria = new SscBlockCriteria();
        sscBlockCriteria.createCriteria().andBlockIdEqualTo(block_id);
        sscBlockCriteria.setOffSet(0);
        sscBlockCriteria.setPageSize(1);
        List<SscBlock> sscBlock = sscBlockMapper.selectByExample(sscBlockCriteria);
        return CollectionUtils.isEmpty(sscBlock) ? null : sscBlock.get(0);
    }

    /**
     * 根据块号查询块信息
     */
    @Override
    public SscBlock blockQueryByBlockNum(Long block_num) {
        SscBlockCriteria sscBlockCriteria = new SscBlockCriteria();
        sscBlockCriteria.setOffSet(0);
        sscBlockCriteria.setPageSize(1);
        sscBlockCriteria.createCriteria().andBlockNumEqualTo(block_num);
        List<SscBlock> sscBlock = sscBlockMapper.selectByExample(sscBlockCriteria);
        return CollectionUtils.isEmpty(sscBlock) ? null : sscBlock.get(0);
    }

    /**
     * 查询字段是否为区块hash值
     */
    @Override
    public SscBlock getBlockNumByBlockId(String hash) {
        SscBlockCriteria sscBlockCriteria = new SscBlockCriteria();
        sscBlockCriteria.setOffSet(0);
        sscBlockCriteria.setPageSize(1);
        sscBlockCriteria.createCriteria().andBlockIdEqualTo(hash);
        List<SscBlock> sscBlock = sscBlockMapper.selectByExample(sscBlockCriteria);
        return CollectionUtils.isEmpty(sscBlock) ? null : sscBlock.get(0);
    }

    @Override
    public SscStatisticsDto getStatisticsAllData() {
        return null;
    }

    @Override
    public UserAddress getUserAddressByAccountName(String accountName) {
        UserAddressCriteria c = new UserAddressCriteria();
        c.createCriteria().andAccountNameEqualTo(accountName);
        c.setOffSet(0);
        c.setPageSize(1);
        List<UserAddress> list = userAddressMapper.selectByExample(c);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<UserAddress> getUserAddressByAddress(String userAddress, String coinType) {
        return null;
    }

//  /**
//   * 根据块的hash值查询块信息
//   */
//
//  @Override
//  public SscBlock blockQueryByBlockId(String block_id) {
//    SscBlockCriteria actBlockCriteria = new SscBlockCriteria();
//    actBlockCriteria.createCriteria().andBlockIdEqualTo(block_id);
//    actBlockCriteria.setOffSet(0);
//    actBlockCriteria.setPageSize(1);
//    List<SscBlock> actBlock = sscBlockMapper.selectByExample(actBlockCriteria);
//    return CollectionUtils.isEmpty(actBlock) ? null : actBlock.get(0);
//  }
//
//  /**
//   * 根据块号查询块信息
//   */
//
//  @Override
//  public SscBlock blockQueryByBlockNum(Long block_num) {
//    SscBlockCriteria actBlockCriteria = new SscBlockCriteria();
//    actBlockCriteria.setOffSet(0);
//    actBlockCriteria.setPageSize(1);
//    actBlockCriteria.createCriteria().andBlockNumEqualTo(block_num);
//    List<SscBlock> actBlock = sscBlockMapper.selectByExample(actBlockCriteria);
//    return CollectionUtils.isEmpty(actBlock) ? null : actBlock.get(0);
//  }
//
//  /**
//   * 查询字段是否为区块hash值
//   */
//
//  @Override
//  public SscBlock getBlockNumByBlockId(String hash) {
//    SscBlockCriteria actBlockCriteria = new SscBlockCriteria();
//    actBlockCriteria.setOffSet(0);
//    actBlockCriteria.setPageSize(1);
//    actBlockCriteria.createCriteria().andBlockIdEqualTo(hash);
//    List<SscBlock> actBlock = sscBlockMapper.selectByExample(actBlockCriteria);
//    return CollectionUtils.isEmpty(actBlock) ? null : actBlock.get(0);
//  }
//
//  @Override
//  public UserAddress getUserAddressByAccountName(String accountName) {
//    UserAddressCriteria c = new UserAddressCriteria();
//    c.createCriteria().andAccountNameEqualTo(accountName);
//    c.setOffSet(0);
//    c.setPageSize(1);
//    List<UserAddress> list = userAddressMapper.selectByExample(c);
//    return CollectionUtils.isEmpty(list) ? null : list.get(0);
//  }
//
//  @Override
//  public SscStatisticsDto getStatisticsAllData() {
//    return redisTemplate.opsForValue().get(RedisWalletDataConstants.WALLET_STATISTICS_ALL_TASK.name());
//  }
//
//  @Override
//  public List<UserAddress> getUserAddressByAddress(String userAddress, String coinType) {
//    UserAddressCriteria c = new UserAddressCriteria();
//    UserAddressCriteria.Criteria criteria = c.createCriteria();
//    criteria.andUserAddressEqualTo(userAddress);
//    if (StringUtils.isNotEmpty(coinType)) {
//      criteria.andCoinTypeEqualTo(coinType);
//    }
//    return userAddressMapper.selectByExample(c);
//  }
}
