package com.zhw.ms.common.contract.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页数据封装Map
 * Created by ZHW on 2015/5/11.
 */
public class PageMap implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> rows;
    private int total;
    private int page;
    private int size;
    private List<Map<String, Object>> footer;

    public static PageMap getPageData(List<Map<String, Object>> rows, int total) {
        PageMap data = new PageMap();
        data.setRows(rows);
        data.setTotal(total);
        return data;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
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

    public List<Map<String, Object>> getFooter() {
        return footer;
    }

    public void setFooter(List<Map<String, Object>> footer) {
        this.footer = footer;
    }


}
