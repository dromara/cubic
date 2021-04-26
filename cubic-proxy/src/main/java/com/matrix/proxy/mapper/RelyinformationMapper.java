package com.matrix.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.entity.RelyInformation;
import java.util.List;

/**
 * @ClassName JarController
 * @Author 李家山竹
 * @Date 2021/4/23 9:16 下午
 * @Version 1.0
 */
public interface RelyinformationMapper extends BaseMapper<RelyInformation> {


    /**
     * 获取应用JAR列表信息
     * @return
     */
    List<RelyInformation> getJarList(String id);
}
