package com.cubic.proxy.common.module;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PageQuery
 * @Author QIANGLU
 * @Date 2022/8/19 20:03
 * @Version 1.0
 */
@Data
public class PageQuery implements Serializable {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 条数
     */
    private Integer pageSize = 20;

    public PageQuery() {

    }

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
