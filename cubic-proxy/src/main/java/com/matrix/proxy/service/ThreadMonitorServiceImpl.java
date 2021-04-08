package com.matrix.proxy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Criteria;
import com.matrix.proxy.constant.MonitorCacheType;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import com.matrix.proxy.module.ChartModel;
import com.matrix.proxy.module.ThreadDetailsLog;
import com.matrix.proxy.module.ThreadPoolLog;
import com.matrix.proxy.module.ThreadStackLog;
import com.matrix.proxy.util.DateUtils;
import com.matrix.proxy.util.GzipUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理线程栈信息展示
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Service
public class ThreadMonitorServiceImpl implements ThreadMonitorService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadMonitorServiceImpl.class);

	@Resource
	private ThreadDumpMapper threadDumpMapper;

	private static ExecutorService executorService;

	public ThreadMonitorServiceImpl() {
		executorService = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000), new ExwarnThreadFactory("ThreadMonitorService"), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 获取应用实例线程栈信息
	 *
	 * @param time
	 * @param serviceName
	 * @param uid
	 * @return
	 */
	@Override
	public String getThreadsDetailsByUid(String time, String serviceName, String uid) {
		long start = System.currentTimeMillis();
		LocalDateTime localDate = StringUtils.contains(time, "-") ? DateUtils.getTimeByPattern(time, "yyyy-MM-dd HH:mm") : DateUtils.getTimeByPattern(time, "yyyyMMddHHmm");
		Object serviceId = MatrixRedisClusterUtils.opsForHash().get(MonitorCacheType.MATRIX_MONITOR_ALL, serviceName);

		String minTime = localDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		ThreadStackLog log = getDetails(String.valueOf(serviceId), uid, minTime);

		threadDumpMapper.selectThreadDumpByAppId();
		LOGGER.info("获取应用实例线程栈信息ThreadMonitorService-getThreadsDetailsByUid 耗时:{}ms", System.currentTimeMillis() - start);
		return log == null ? "" : GzipUtils.decompress(log.getStack());
	}

//	public ThreadStackLog getDetails(String serviceId, String uid, String date) {
//		Criteria criteria = Criteria.where("serviceId").is(serviceId).and("uuid").is(uid).and("timeBucket").is(date);
//		Query query = new Query(criteria);
//		ThreadStackLog log = mongoTemplate.findOne(query, ThreadStackLog.class, MongoConstant.THREAD_STACK);
//		return log;
//	}

	public ThreadStackLog getDetails() {

	}

	/**
	 * 根据 uid查询 threadpool
	 *
	 * @param uid
	 * @param pageIndex
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public Page<ThreadPoolLog> getThreadPoolByUid(String uid, int pageIndex, int pageSize, String startDate, String endDate) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		Criteria criteria = Criteria.where("uuid").is(uid);
		if (StringUtils.isNotEmpty(startDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime end = DateUtils.getTime(endDate);
			LocalDateTime start = StringUtils.contains(startDate, "-") ? DateUtils.str2Date(startDate, formatter) : end.minusMinutes(Long.valueOf(startDate));
			criteria.andOperator(Criteria.where("createDate").lte(end), Criteria.where("createDate").gte(start));
		}
		Query query = new Query(criteria);
		long count = mongoTemplate.count(query, ThreadPoolLog.class, MongoConstant.THREAD_POOLS);
		query.with(pageable);
		List<ThreadPoolLog> logs = mongoTemplate.find(query, ThreadPoolLog.class, MongoConstant.THREAD_POOLS);
		List<String> result = new LinkedList<>();
		logs.forEach(log -> {
			result.add(log.getDetails());
		});
		return new PageImpl(result, pageable, count);
	}
}
