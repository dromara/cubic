import request from '@/utils/request'

/**
 * 获取线程折线图
 * @param {*} params
 * /thread/getThreadCharts
 */
export function getThreadCharts(params) {
  return request({
    url: process.env.BASE_ERROR_LOG_API + '/thread/getThreadCharts',
    method: 'get',
    params
  })
}

/**
 * 获取线程列表
 * @param {*} params
 */
export function getThreadDetails(params) {
  return request({
    url: process.env.BASE_ERROR_LOG_API + '/thread/getThreadDetails',
    method: 'get',
    params
  })
}

/**
 * 获取监控图标数据
 * @param {*} params
 * /thread/getThreadsDetailsByUid
 */
export function getThreadsDetailsByUid(params) {
  return request({
    url: '/dump/getThreadDumpByAppId',
    method: 'get',
    params
  })
}

export function getThreadsRealDetailsByUid(params) {
  return request({
    url: '/command/jdkCommand',
    method: 'get',
    params
  })
}

export function getThreadsDetailsByMin(params) {
  return request({
    url: process.env.BASE_ERROR_LOG_API + '/dump/getThreadDumpByAppId1',
    method: 'get',
    params
  })
}
export function getHistoryByAppId(params) {
  return request({
    url: '/dump/getHistoryByAppId',
    method: 'get',
    params
  })
}

export function downloadByAppId(params) {
  return request({
    url: '/dump/downloadByAppId',
    method: 'get',
    params
  })
}
