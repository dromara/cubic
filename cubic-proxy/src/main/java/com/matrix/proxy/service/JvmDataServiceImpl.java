package com.matrix.proxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cubic.serialization.agent.v1.ThreadPoolInfo;
import com.google.protobuf.TextFormat;
import com.matrix.proxy.entity.ThreadPool;
import com.matrix.proxy.mapper.ThreadPoolMapper;
import com.matrix.proxy.module.ThreadPoolVo;
import com.matrix.proxy.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * JVM采集数据服务
 *
 * @author zhanghao
 * @date 2021/4/75:59 下午
 */
@Slf4j
@Service
public class JvmDataServiceImpl implements JvmDataService {
    @Resource
    private ThreadPoolMapper threadPoolMapper;

    /**
     * 查询线程池数据分页
     *
     * @param instanceUid
     * @return
     */
    @Override
    public Map<String, Object> threadPoolDataPage(String instanceUid, String dayTime) {

        String[] appId = instanceUid.split("_");

        String time =dayTime;
        if(StringUtils.isEmpty(dayTime)){
            time = DateUtils.getDateFormat(new Date(),"yyyy-MM-dd HH:mm");
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instance_name", appId[0]);
        queryWrapper.eq("instance_id", appId[1]);
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d %H:%i') = '" + time + "'");
        List<ThreadPool> poolEntities = threadPoolMapper.selectList(queryWrapper);

        List<ThreadPoolVo> vos = new ArrayList<>();
        for (ThreadPool entity : poolEntities) {
            ThreadPoolVo.ThreadPoolVoBuilder builder = ThreadPoolVo.builder().threadPoolKey(entity.getThreadPoolKey()).createTime(entity.getCreateTime());
            ThreadPoolInfo.Builder threadPoolBuild = ThreadPoolInfo.newBuilder();
            try {
                TextFormat.merge(entity.getThreadPoolParams(), threadPoolBuild);
            } catch (TextFormat.ParseException e) {
                log.error("线程池监控 ThreadPoolInfo.Builder 解析出错 {} {}", entity.getThreadPoolParams(), e.getStackTrace());
            }
            Map<String, Long> params = threadPoolBuild.build().getThreadPoolMetricInfoMap();
            builder.coreSize(params.get("CORE_POOL_SIZE").toString())
                    .activeCount(params.get("ACTIVE_COUNT").toString())
                    .completedTaskCount(params.get("COMPLETED_TASK_COUNT").toString())
                    .largestPoolSize(params.get("LARGEST_POOL_SIZE").toString())
                    .poolSize(params.get("POOL_SIZE").toString())
                    .taskCount(params.get("TASK_COUNT").toString())
                    .maximumPoolSize(params.get("MAXIMUM_POOL_SIZE").toString())
                    .keepAliveTime(params.get("KEEP_ALIVE_TIME").toString());
            vos.add(builder.build());
        }
        Map<String, Object> rs = new HashMap<>(16);
        rs.put("total", poolEntities.size());
        rs.put("records", vos);
        return rs;
    }
}
