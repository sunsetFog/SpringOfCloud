package com.stars.common.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页返回结果
 * PageHelper.startPage(1,10);只对该语句以后的第一个查询语句得到的数据进行分页
 */
public class PageResult {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;

//    public PageResult(Integer pageNum, Integer pageSize, List<T> data) {
//        PageHelper.startPage(pageNum, pageSize);
//        PageInfo<T> pageInfo = new PageInfo<T>(data);
//        this.pageNum = pageInfo.getPageNum();
//        this.pageSize = pageInfo.getPageSize();
//        this.totalSize = pageInfo.getTotal();
//        this.totalPages = pageInfo.getPages();
//        this.content = pageInfo.getList();
//    }

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public long getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<?> getContent() {
        return content;
    }
    public void setContent(List<?> content) {
        this.content = content;
    }


    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
