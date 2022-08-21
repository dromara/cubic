package com.matrix.proxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cubic.proxy.common.module.DataResult;
import com.matrix.proxy.entity.ThreadDump;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import com.matrix.proxy.util.GzipUtils;
import com.matrix.proxy.vo.ThreadDumpVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理线程栈信息展示
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Service
public class ThreadDumpServiceImpl implements ThreadDumpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadDumpServiceImpl.class);

    @Resource
    private ThreadDumpMapper threadDumpMapper;


    /**
     * 获取应用实例线程栈信息
     *
     * @param time
     * @param appId
     * @return
     */
    @Override
    public String getThreadDumpByAppId(String appId, String time) {
        QueryWrapper<ThreadDump> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id",appId).apply("date_format(create_time,'%Y-%m-%d %H:%i') = '" + time + " '") ;
        ThreadDump threadDump = threadDumpMapper.selectOne(wrapper);
        return threadDump == null ? "" : GzipUtils.decompress(threadDump.getThreadDump());
    }

    /**
     * 获取应用线程栈历史信息
     *
     * @return
     */
    @Override
    public DataResult getHistoryByAppId(ThreadDumpVo dumpVo ) {

        Page<ThreadDump> page = new Page<>(dumpVo.getPageNum(), dumpVo.getPageSize());

        QueryWrapper<ThreadDump> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id",dumpVo.getAppId()) ;
        wrapper.select("app_id","instance_name","instance_id","create_time");
        wrapper.orderByDesc("create_time");

        IPage<ThreadDump> datas = threadDumpMapper.selectPage(page, wrapper);
        if (CollectionUtils.isEmpty(datas.getRecords())) {
            return DataResult.success();
        }
        List<ThreadDumpVo> result = new ArrayList<>();
        BeanCopier copier = BeanCopier.create(ThreadDump.class, ThreadDumpVo.class, false);

        datas.getRecords().forEach(user -> {
            ThreadDumpVo dto = new ThreadDumpVo();
            copier.copy(user, dto, null);
            result.add(dto);
        });

        return DataResult.success(result, datas.getTotal());

    }

//	public ThreadStackLog getDetails(String serviceId, String uid, String date) {
//		Criteria criteria = Criteria.where("serviceId").is(serviceId).and("uuid").is(uid).and("timeBucket").is(date);
//		Query query = new Query(criteria);
//		ThreadStackLog log = mongoTemplate.findOne(query, ThreadStackLog.class, MongoConstant.THREAD_STACK);
//		return log;
//	}

//
//    /**
//     * 根据 uid查询 threadpool
//     *
//     * @param uid
//     * @param pageIndex
//     * @param pageSize
//     * @param startDate
//     * @param endDate
//     * @return
//     */
//    @Override
//    public Page<ThreadPoolLog> getThreadPoolByUid(String uid, int pageIndex, int pageSize, String startDate, String endDate) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
//        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
//        Criteria criteria = Criteria.where("uuid").is(uid);
//        if (StringUtils.isNotEmpty(startDate)) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime end = DateUtils.getTime(endDate);
//            LocalDateTime start = StringUtils.contains(startDate, "-") ? DateUtils.str2Date(startDate, formatter) : end.minusMinutes(Long.valueOf(startDate));
////			criteria.andOperator(Criteria.where("createDate").lte(end), Criteria.where("createDate").gte(start));
//        }
////		Query query = new Query(criteria);
////		long count = mongoTemplate.count(query, ThreadPoolLog.class, MongoConstant.THREAD_POOLS);
////		query.with(pageable);
////		List<ThreadPoolLog> logs = mongoTemplate.find(query, ThreadPoolLog.class, MongoConstant.THREAD_POOLS);
//        List<String> result = new LinkedList<>();
////		logs.forEach(log -> {
////			result.add(log.getDetails());
////		});
////		return new PageImpl(result, pageable, count);
//        return null;
//    }
}
