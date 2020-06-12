package com.matrix.proxy.service;

import com.matrix.proxy.db.entity.BasicInformation;
import com.matrix.proxy.db.repository.BasicInformationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于处理基础数据
 * @ClassName BasicDataServiceImpl
 * @Author QIANGLU
 * @Date 2020/6/10 11:29 上午
 * @Version 1.0
 */
@Service("appDataService")
@Slf4j
public class AppDataServiceImpl implements AppDataService {

    @Resource
    private BasicInformationRepository repository;
    /**
     * 获取应用实例列表
     * @return
     */
    @Override
    public List<BasicInformation> getAppList(){

        return repository.findAll();
    }
}
