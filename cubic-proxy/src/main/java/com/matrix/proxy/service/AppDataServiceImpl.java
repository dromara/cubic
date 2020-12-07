package com.matrix.proxy.service;

import com.matrix.proxy.entity.Information;
import com.matrix.proxy.vo.BasicInformationVo;
import com.matrix.proxy.mapper.formationMapper;
import com.matrix.proxy.util.DateUtils;
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
    private formationMapper repository;

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
        List<Information> datas = repository.selectInstanceByLastHeartbeat(curr);
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
}
