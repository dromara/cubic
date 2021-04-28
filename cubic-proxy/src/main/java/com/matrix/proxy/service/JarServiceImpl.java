package com.matrix.proxy.service;

import com.alibaba.fastjson.JSONArray;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.mapper.InformationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private InformationMapper informationMapper;

    @Override
    public Map<Object, List<Object>> getJarList(String Appid) {
        Information information = informationMapper.selectJarsByAppId(Appid);
        if(information == null){return null;}
        JSONArray jsonArray =JSONArray.parseArray(information.getJars());
        Map<Object, List<Object>> jarmap = null;
//        .substring(0,item.toString().lastIndexOf("-")
        jarmap = jsonArray.stream().collect(Collectors.groupingBy(item -> Arrays.stream(item.toString().split("-")).findFirst().get()));
        return jarmap;
    }
}
