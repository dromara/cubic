package com.matrix.proxy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.mapper.ThreadPoolMapper;
import com.matrix.proxy.module.ThreadPoolVo;
import com.matrix.proxy.vo.ThreadPoolQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
     * @param instanceName
     * @return
     */
    @Override
    public Page threadPoolDataPage(String instanceName) {
        Page<ThreadPoolEntity> page = new Page<>(1, 100);

        IPage<ThreadPoolEntity> iPages = threadPoolMapper.selectPage(page, new QueryWrapper<>());

        List<ThreadPoolVo> vos = new ArrayList<>();
        for (ThreadPoolEntity entity : iPages.getRecords()) {
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
        Page pg = new Page();
        pg.setTotal(iPages.getTotal());
        pg.setRecords(vos);
        pg.setPages(iPages.getPages());
        return pg;
    }
}
