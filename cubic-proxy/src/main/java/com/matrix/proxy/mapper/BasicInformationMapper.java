package com.matrix.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.entity.BasicInformation;

import java.util.Date;
import java.util.List;

/**
 * @ClassName BasicInformationRepository
 * @Author QIANGLU
 * @Date 2020/6/10 11:22 上午
 * @Version 1.0
 */
public interface BasicInformationMapper extends BaseMapper<BasicInformation> {


    /**
     * 根据lastHeartbeat查询应用实例列表信息
     * @param lastHeartbeat
     * @return
     */
    List<BasicInformation> selectInstanceByLastHeartbeat( Date lastHeartbeat);
}
