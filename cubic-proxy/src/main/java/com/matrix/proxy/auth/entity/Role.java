package com.matrix.proxy.auth.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Role
 * @Author QIANGLU
 * @Date 2020/11/18 10:01 上午
 * @Version 1.0
 */
@Data
@Builder
public class Role {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 编号
     */
    private String code;
}
