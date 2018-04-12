package com.achain.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yujianjian
 * @since 2017-11-21 上午11:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SscTransactionExPageVO {

    private List<SscTransactionExVO> actTransactionExList;

    private Integer totalPage;

    private Integer totalRecords;

    private Integer currentPage;

    private Integer pageSize;
}
