package com.zhw.ms.common.contract.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页处理共通
 * Created by ZHW on 2015/5/11.
 */
public class PageUtil {

    /**
     * 获取封装的分页数据
     *
     * @param <E>   the type parameter
     * @param rows  the rows
     * @param total the total
     * @param page  the page
     * @param size  the size
     * @return page
     */
    public static <E> PageData<E> getPage(List<E> rows, int total, int page,
                                          int size) {
        PageData<E> data = new PageData<E>();
        if (rows == null) {
            data.setRows(new ArrayList<E>());
        } else {
            data.setRows(rows);
        }
        data.setTotal(total);
        data.setSize(size);
        data.setPage(page);
        return data;
    }

    /**
     * 获取封装的分页数据
     *
     * @param rows  the rows
     * @param total the total
     * @param page  the page
     * @param size  the size
     * @return page map
     */
    public static PageMap getPageMap(List<Map<String, Object>> rows, int total, int page,
                                     int size) {
        PageMap data = new PageMap();
        if (rows == null) {
            data.setRows(new ArrayList<Map<String, Object>>());
        } else {
            data.setRows(rows);
        }
        data.setTotal(total);
        data.setSize(size);
        data.setPage(page);
        return data;
    }

    /**
     * 获取封装的分页数据
     *
     * @param <E>    the type parameter
     * @param rows   the rows
     * @param total  the total
     * @param page   the page
     * @param size   the size
     * @param footer the footer
     * @return page
     */
    public static <E> PageData<E> getPage(List<E> rows, int total, int page,
                                          int size, List<E> footer) {
        PageData<E> data = new PageData<E>();
        if (rows == null) {
            data.setRows(new ArrayList<E>());
        } else {
            data.setRows(rows);
        }
        data.setTotal(total);
        data.setSize(size);
        data.setPage(page);
        data.setFooter(footer);
        return data;
    }

    /**
     * 封装查询条件
     *
     * @param page   the page
     * @param rows   the rows
     * @param total  the total
     * @param params the params
     * @return search
     */
    public static Map<String, Object> getSearch(int page, int rows, int total,
                                                Map<String, Object> params) {

        params.put("start", getStart(page, rows, total));
        params.put("rows", rows);

        return params;
    }

    /**
     * 获取第一条数据的索引
     *
     * @param page  the page
     * @param rows  the rows
     * @param total the total
     * @return start
     */
    public static int getStart(int page, int rows, int total) {

        if (page < 1) {
            page = 1;
        }

        if (rows < 10) {
            rows = 10;
        }

        int start = (page - 1) * rows;

        if (start >= total) {
            if (total % rows == 0) {
                start = (total / rows - 1) * rows;
            } else {
                start = (total / rows) * rows;
            }
        }

        return start;
    }
}
