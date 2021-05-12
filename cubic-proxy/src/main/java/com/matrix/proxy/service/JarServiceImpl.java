package com.matrix.proxy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.mapper.InformationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    @Override
    public Map<String, Integer> getJarList(String Appid) {
        Information information = informationMapper.selectJarsByAppId(Appid);
        if(information == null){return null;}

        List jarList = JSONObject.parseArray(information.getJars());
        Map<String, Integer> jarmap = (Map<String, Integer>) jarList.stream()
                .filter(item -> NUMBER_PATTERN.matcher(item.toString()).find() && !item.toString().contains("-"))
                .collect(Collectors.groupingBy(item -> item.toString()
                        ,Collectors.counting()));

        Map<String, Integer> jarmapN = (Map<String, Integer>) jarList.stream()
                .filter(item -> !NUMBER_PATTERN.matcher(item.toString()).find())
                .collect(Collectors.groupingBy(item -> item.toString()
                        ,Collectors.counting()));

        Map<String, Integer> jarmapW = (Map<String, Integer>) jarList.stream()
                .filter(item -> NUMBER_PATTERN.matcher(item.toString()).find() && item.toString().contains("-"))
                .collect(Collectors.groupingBy(item -> item.toString()
                        ,Collectors.counting()));

        Map<Object, List<Object>> jarmapV = JSONArray.parseArray(information.getJars())
                .stream()
                .filter(item -> NUMBER_PATTERN.matcher(item.toString()).find() && item.toString().contains("-"))
                .collect(Collectors.groupingBy(item -> item.toString().substring(0,item.toString().lastIndexOf("-"))));

        for (List<Object> value : jarmapV.values()) {
            for (Object item : value) {
                jarmap.put(item.toString(),value.size());
            }
        }
        jarmap.putAll(jarmapN);
        return jarmap;
    }
}
