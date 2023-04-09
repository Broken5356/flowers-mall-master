package com.hide.backend.util;

import java.util.List;

/**
 * 工具类
 **/
public class DataUtils {
    // 分页每页数目
    final private static int PAGE_SIZE = 5;

    public static List<Object> getDataByPage(List<Object> data, int page) {
        return data.size() > (page * PAGE_SIZE) ? data.subList((page - 1) * PAGE_SIZE, page * PAGE_SIZE) : data.subList((page - 1) * PAGE_SIZE, data.size());
    }
}
