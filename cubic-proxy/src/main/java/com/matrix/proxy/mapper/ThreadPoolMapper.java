package com.matrix.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.entity.ThreadPool;
import com.matrix.proxy.vo.ThreadPoolQueryVo;

import java.util.List;

/**
 * 线程池持久化
 *
 * @author zhanghao
 * @date 2021/4/74:24 下午
 */
public interface ThreadPoolMapper extends BaseMapper<ThreadPool> {
    /**
     * 多条件查询
     *
     * @param query
     * @return
     */
    List<ThreadPool> selectByQuery(ThreadPoolQueryVo query);
}
