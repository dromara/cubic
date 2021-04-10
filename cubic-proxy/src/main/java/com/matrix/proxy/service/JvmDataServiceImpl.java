package com.matrix.proxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.mapper.ThreadPoolMapper;
import com.matrix.proxy.vo.ThreadPoolQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * @param query
     * @return
     */
    @Override
    public IPage<ThreadPoolEntity> threadPoolDataPage(ThreadPoolQuery query) {
        Page<ThreadPoolEntity> page = new Page<>(query.getPageNo(),query.getPageSize());

        IPage<ThreadPoolEntity> iPages =threadPoolMapper.selectPage(page,new QueryWrapper<>());
        return iPages;
    }
}
