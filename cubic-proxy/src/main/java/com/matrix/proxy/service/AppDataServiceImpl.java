package com.matrix.proxy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.entity.RelyInformation;
import com.matrix.proxy.vo.BasicInformationVo;
import com.matrix.proxy.mapper.InformationMapper;
import com.matrix.proxy.util.DateUtils;
import com.matrix.proxy.vo.InstanceInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于处理基础数据
 *
 * @ClassName BasicDataServiceImpl
 * @Author QIANGLU
 * @Date 2020/6/10 11:29 上午
 * @Version 1.0
 */
@Service("appDataService")
@Slf4j
public class AppDataServiceImpl implements AppDataService {

    @Resource
    private InformationMapper informationMapper;

    /**
     * 获取应用实例列表
     *
     * @return
     */
    @Override
    public Map getAppList(String date) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -5);
        Date curr = nowTime.getTime();

        if (StringUtils.isNotEmpty(date)) {
            curr = DateUtils.stringToDate(date);
        }

        Map<String, Object> result = new HashMap<>(16);
        List<Information> datas = informationMapper.selectInstanceByLastHeartbeat(curr);
        List<BasicInformationVo> informationVos = dispose(datas);
        result.put("informations", informationVos);

        Set<String> services = new HashSet<>();
        AtomicInteger instances = new AtomicInteger();
        informationVos.forEach(information -> {
            services.add(information.getInstanceName());
            instances.incrementAndGet();
        });
        result.put("services", services.size());
        result.put("instances", instances.intValue());
        return result;
    }

    /**
     * 处理实例数据
     *
     * @param datas
     * @return
     */
    private List<BasicInformationVo> dispose(List<Information> datas) {
        List<BasicInformationVo> result = new LinkedList<>();

        datas.forEach(base -> {
            BasicInformationVo vo = BasicInformationVo.builder().build();
            BeanUtils.copyProperties(base, vo, "state", "online", "startDate", "lastHeartbeat");
            LocalDateTime start = LocalDateTime.ofInstant(base.getStartDate().toInstant(), DateUtils.ASIA_SHANGHAI);
            LocalDateTime lastHeart = LocalDateTime.ofInstant(base.getLastHeartbeat().toInstant(), DateUtils.ASIA_SHANGHAI);
            String startTime = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            vo.setStartDate(startTime);
            String heart = DateUtils.calculateTime(lastHeart);
            vo.setOnLine(DateUtils.calculateTimeDifference(start));
            vo.setLastHeartbeat(heart);
            result.add(vo);
        });
        return result;
    }

    /**
     * 获取应用实例详细信息
     *
     * @param appId
     * @return
     */
    @Override
    public InstanceInfoVo getInstanceInfo(String appId) {
        InstanceInfoVo.InstanceInfoVoBuilder builder = InstanceInfoVo.builder();

        try {

            Information information = informationMapper.selectInstanceByAppId(appId);
            if (information != null) {
                builder.jdkDir(information.getJdkDir()).jdkVersion(information.getJdkVersion()).userDir(information.getUserDir()).
                        initMemory(information.getInitMemory()).maxMemory(information.getMaxMemory()).processorNum(information.getProcessorNum())
                        .ips(information.getIp()).hostname(information.getHost())
                        .progress(information.getProgress()).os(information.getOs()).osArch(information.getOsArch()).osVersion(information.getOsVersion())
                        .arguments(JSON.parseArray(information.getArguments()).toJavaList(String.class));
                String jars = information.getJars();
                List<String> libs = JSON.parseArray(jars,String.class);
                builder.libs(libs);
            }
            String jars = information.getJars();
            List array = JSON.parseArray(jars, String.class);
            builder.libs(array);
        } catch (Exception e) {
            log.error("处理InstanceInfoVo 数据异常", e);
        }
        return builder.build();
    }

    /**
     * 根据应用名称获取实例列表
     *
     * @param name
     * @return
     */
    @Override
    public List<String> getInstanceNames(String name) {

        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, -5);
            Date curr = nowTime.getTime();

            List<String> informationList = informationMapper.selectInstancesByName(name,curr);
            return informationList;
        } catch (Exception e) {
            log.error("处理InstanceInfoVo 数据异常", e);
        }
        return new ArrayList<>();
    }
}
