package com.matrix.proxy.service;

import com.matrix.proxy.entity.RelyInformation;
import com.matrix.proxy.mapper.RelyinformationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName JarController
 * @Author 李家山竹
 * @Date 2021/4/23 9:16 下午
 * @Version 1.0
 */
@Service("JarService")
@Slf4j
public class JarServiceImpl implements JarService {

    @Resource
    private RelyinformationMapper relyinformationMapper;

    @Override
    public Map<String, List<RelyInformation>> getJarList(String Appid) {
        List<RelyInformation> jarList = relyinformationMapper.getJarList(Appid);
        Map<String, List<RelyInformation>> jarmap = null;
        if(jarList != null){
            jarmap = jarList.stream().collect(Collectors.groupingBy(item ->
                    item.getJarName().substring(0,item.getJarName().indexOf(":"))));
        }
        return jarmap;
    }
}
