package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.module.Response;
import com.matrix.proxy.constant.MonitorCacheType;
import com.matrix.proxy.module.ChartModel;
import com.matrix.proxy.module.ThreadPoolLog;
import com.matrix.proxy.service.ThreadMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 应用数据监控
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@RestController
@RequestMapping("/thread")
@CrossOrigin(maxAge = 3600)
public class ThreadMonitorController {

	private static Logger log = LoggerFactory.getLogger(ThreadMonitorController.class);

	@Resource
	private ThreadMonitorService threadMonitorService;

	@RequestMapping("/getThreadCharts")
	public String getThreadCharts(@RequestParam String serviceName, @RequestParam String uuid, @RequestParam(defaultValue = "30", required = false) Integer timePeriod, @RequestParam(required = false) String endTime) {
		Response<List<?>> response = new Response<>(0, "SUCCESS");
		List<ChartModel> models = threadMonitorService.chartData(uuid, endTime, timePeriod, serviceName);
		response.setData(models);
		return JSONObject.toJSONString(response);
	}

	@RequestMapping("/getThreadDetails")
	public Response<List> getThreadDetails(@RequestParam String uid, @RequestParam String serviceName, @RequestParam(defaultValue = "30", required = false) Integer timePeriod, @RequestParam(required = false) String endTime) {
		Response<List> response = new Response<>(0, "SUCCESS");
		List models = threadMonitorService.getThreadDetails(endTime, timePeriod, serviceName, uid);
		response.setData(models);
		return response;

	}

	/**
	 * 获取应用实例所有线程详情
	 *
	 * @return
	 */
	@RequestMapping("/getThreadsDetailsByUid")
	public String getThreadsDetailsByUid(@RequestParam String uid, @RequestParam String serviceName, @RequestParam String dayTime) {
		Response<String> response = new Response<>(0, "SUCCESS");
		String models = threadMonitorService.getThreadsDetailsByUid(dayTime, serviceName, uid);
		response.setData(models);
		return JSONObject.toJSONString(response);

	}

	/**
	 * 获取应用实例所有线程详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/getThreadsDetails")
	public ResponseEntity<String> getThreadsDetails(@RequestParam String uid, @RequestParam String serviceName, @RequestParam String dayTime) {
		String models = threadMonitorService.getThreadsDetailsByUid(dayTime, serviceName, uid);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain;charset=UTF-8");
		return ResponseEntity.status(200).headers(headers).body(models);

	}

	/**
	 * 获取实时应用线程栈
	 *
	 * @return
	 */
	@RequestMapping(value = "/getRealThreadsDetails")
	public String getRealThreadsDetails(@RequestParam String uid, @RequestParam String serviceName) {
		Response<String> response = new Response<>(0, "SUCCESS");
		String threadDump = threadMonitorService.getRealThreadsDetails(serviceName, uid);
		response.setData(threadDump);
		return JSONObject.toJSONString(response);

	}

	/**
	 * 获取某一分钟线程详情
	 *
	 * @param uid
	 * @param serviceName
	 * @param dayTime     yyyyMmddHHmmss
	 * @return
	 */
	@RequestMapping(value = "/getThreadsDetailsByMin")
	public String getThreadsDetailsByMin(@RequestParam String uid, @RequestParam String serviceName, @RequestParam String dayTime, @RequestParam(required = false, defaultValue = "RUNNABLE") String threadState) {
		Response<Map> response = new Response<>(0, "SUCCESS");
		Map<String, Object> logs = threadMonitorService.getThreadDetails(dayTime, serviceName, uid, threadState);
		response.setData(logs);
		return JSONObject.toJSONString(response);

	}

	/**
	 * 获取应用所有线程信息
	 *
	 * @return
	 */
	@RequestMapping("/getEndpoints")
	@Deprecated
	public Map<Object, Object> getEndpoints(@RequestParam String serviceId, @RequestParam String uuid, @RequestParam String dayTime) {
		String key = serviceId + ":" + uuid + ":" + dayTime;
		return MatrixRedisClusterUtils.opsForHash().entries(MonitorCacheType.MATRIX_MONITOR_JVM_THREAD_IDS + key);

	}

	/**
	 * 获取应用所有线程信息
	 *
	 * @return
	 */
	@RequestMapping("/getThreads")
	@Deprecated
	public Map<Object, Object> getThreads(@RequestParam String serviceId, @RequestParam String dayTime) {
		String key = serviceId + ":" + dayTime;
		return MatrixRedisClusterUtils.opsForHash().entries(MonitorCacheType.MATRIX_MONITOR_JVM_THREADS + key);

	}

	@RequestMapping("/getThreadPoolByUid")
	public String getThreadPoolByUid(@RequestParam String uid, @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "30") String timePeriod, @RequestParam(required = false) String endDate) {
		Response<ExwarnPage> response = new Response<>(0, "SUCCESS");
		Page<ThreadPoolLog> exwarnPage = threadMonitorService.getThreadPoolByUid(uid, pageIndex != 0 ? pageIndex - 1 : pageIndex, pageSize, timePeriod, endDate);
		ExwarnPage<List> listExwarnPage = new ExwarnPage<>();
		listExwarnPage.setPageIndex(pageIndex);
		listExwarnPage.setTotal(exwarnPage.getTotalElements());
		listExwarnPage.setRecords(exwarnPage.getContent());
		response.setData(listExwarnPage);
		return JSON.toJSONString(response);
	}
}
