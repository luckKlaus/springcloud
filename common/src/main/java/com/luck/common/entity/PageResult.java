package com.luck.common.entity;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageResult<T> {
    long pageNum;//当前页
    long pageSize;//每页的数量
    long pages;//总页数
    long total;//总记录数
    List<T> rows;

    public PageResult() {
    }

    public PageResult(long total, List<T> Tlist) {
        this.total = total;
        this.rows = Tlist;
    }

    public PageResult(PageInfo<T> pageinfo) {
        this.total = pageinfo.getTotal();
        this.rows = pageinfo.getList();
        this.pageNum=pageinfo.getPageNum();
        this.pageSize=pageinfo.getPageSize();
        this.pages=pageinfo.getPages();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
