package com.achain.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private int totalRecords = 0; // 总记录数
  /**
   * 当前页码
   */
  private int currentPage;
  /**
   * 总页数
   */
  private int totalPage;
  /**
   * 每页显示条数
   */
  private int pageSize = 10;


  private List<T> dataList = new ArrayList<T>();

  public PageResult(int currentPage, int pageSize, List<T> list, int totalRecords) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.dataList = list;
    this.totalRecords = totalRecords;
    this.totalPage = (this.totalRecords - 1) / this.pageSize + 1;
  }

  public PageResult(List<T> dataList, int totalRecords) {
    this.dataList = dataList;
    this.totalRecords = totalRecords;
  }

  public void setPageResult(int totalRecords, List<T> dataList) {
    if (totalRecords < 0) {
      throw new RuntimeException("总记录数不能小于0!");
    }
    // 设置总记录数
    this.totalRecords = totalRecords;
    this.dataList = dataList;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
    this.totalPage = (this.totalRecords - 1) / this.pageSize + 1;
  }
}
