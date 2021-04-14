package com.matrix.proxy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.mapper.ThreadPoolMapper;
import com.matrix.proxy.module.ThreadPoolVo;
import com.matrix.proxy.util.DateUtils;
import com.matrix.proxy.vo.ThreadPoolQueryVo;
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
        List<ThreadPoolEntity> poolEntities = threadPoolMapper.selectList(queryWrapper);

        List<ThreadPoolVo> vos = new ArrayList<>();
        for (ThreadPoolEntity entity : poolEntities) {
            ThreadPoolVo.ThreadPoolVoBuilder builder = ThreadPoolVo.builder().threadPoolKey(entity.getThreadPoolKey()).createTime(entity.getCreateTime());

            JSONObject params = JSON.parseObject(entity.getThreadPoolParams());
            builder.coreSize(params.getString("CORE_POOL_SIZE"))
                    .activeCount(params.getString("ACTIVE_COUNT"))
                    .completedTaskCount(params.getString("COMPLETED_TASK_COUNT"))
                    .largestPoolSize(params.getString("LARGEST_POOL_SIZE"))
                    .poolSize(params.getString("POOL_SIZE"))
                    .taskCount(params.getString("TASK_COUNT"))
                    .maximumPoolSize(params.getString("MAXIMUM_POOL_SIZE"))
                    .keepAliveTime(params.getString("KEEP_ALIVE_TIME"));

            vos.add(builder.build());
        }
        Map<String, Object> rs = new HashMap<>(16);
        rs.put("total", poolEntities.size());
        rs.put("records", vos);
        return rs;
    }
}
