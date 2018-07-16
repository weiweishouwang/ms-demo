package com.zhw.ms.common.contract.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据封装
 * Created by ZHW on 2015/5/11.
 */
public class PageData<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<E> rows;
    private int total;
    private int page;
    private int size;
    private List<E> footer;

    public static <E> PageData<E> getPageData(List<E> rows, int total) {
        PageData<E> data = new PageData<E>();
        data.setRows(rows);
        data.setTotal(total);
        return data;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<E> getFooter() {
        return footer;
    }

    public void setFooter(List<E> footer) {
        this.footer = footer;
    }


}
