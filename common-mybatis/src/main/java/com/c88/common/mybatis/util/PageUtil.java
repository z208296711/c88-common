package com.c88.common.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


public interface PageUtil {
    public static <T> IPage<T> toIPage(List<T> inX, int pageNum, int pageSize){
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = (pageNum * pageSize);
        int total = inX.size();
        if(endIndex > total) endIndex = total;
        IPage pages = new Page<>(pageNum,pageSize);
        pages.setRecords(inX.subList(startIndex,endIndex));
        pages.setTotal(inX.size());
        return pages;
    }
}
