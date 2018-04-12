package com.achain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * 获取全部内容
   */
  public static final PageInfo ALL = new PageInfo(0, Integer.MAX_VALUE);


  public static final int MAX_PAGE_SIZE = 1000;

  /**
   * offset x means start from number (x + 1)
   */
  private int offset = 0;

  /**
   * page size
   */
  private int size = 10;


  /**
   * 从页码、分页大小构建
   *
   * @param page 页码，从1开始
   */
  public static PageInfo fromPageAndSize(int page, int size) {
    page = filterPage(page);
    size = filterSize(size);
    return new PageInfo((page - 1) * size, size);
  }

  /**
   * 从偏移量、分页大小构建
   *
   * @param start 偏移量，从0开始
   */
  public static PageInfo fromStartAndSize(int start, int size) {
    if (start < 0) {
      throw new RuntimeException("page info invalid offset cannot less than zero: " + start);
    }
    size = filterSize(size);
    return new PageInfo(start, size);
  }

  /**
   * 计算总共页数
   */
  public static int calculatePageNum(int total, int size) {
    if (total <= 0 || size < 1) {
      throw new RuntimeException("page info invalid total and size: " + total + "," + size);
    }
    int pageNum = 0;
    if (total % size > 0) {
      pageNum = total / size + 1;
    } else {
      pageNum = total / size;
    }
    return pageNum;
  }

  public static PageInfo fromPageAndSizeUnLimit(int page, int size) {
    page = filterPage(page);
    if (size < 1) {
      throw new RuntimeException("page info invalid size must greater than zero: " + size);
    }
    return new PageInfo((page - 1) * size, size);
  }

  private static int filterPage(int page) {
    if (page < 1) {
      throw new RuntimeException("page info invalid pageno cannot less than 1: " + page);
    }
    return page;
  }

  private static int filterSize(int size) {
    if (size > MAX_PAGE_SIZE) {
      throw new RuntimeException("page info invalid max size is " + MAX_PAGE_SIZE + " : " + size);
    }
    if (size < 1) {
      throw new RuntimeException("page info invalid size must greater than zero: " + size);
    }
    return size;
  }
}
