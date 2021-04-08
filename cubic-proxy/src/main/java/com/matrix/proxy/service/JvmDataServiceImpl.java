package com.matrix.proxy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<ThreadPoolEntity> threadPoolDataPage(ThreadPoolQuery query) {
        return PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(() -> threadPoolMapper.selectByQuery(query));
    }
}
