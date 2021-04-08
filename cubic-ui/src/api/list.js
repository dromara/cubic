import request from '@/utils/request'

export function appList(query) {
  return request({
    url: '/app/getList',
    method: 'get',
    params: query
  })
}

export function getInstanceInfo(query) {
  return request({
    url: '/app/getInstanceInfo',
    method: 'get',
    params: query
  })
}

export function getInstanceNames(query) {
  return request({
    url: '/app/getInstanceNames',
    method: 'get',
    params: query
  })
}

export function fetchArticle(id) {
  return request({
    url: '/vue-element-admin/article/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/article/pv',
    method: 'get',
    params: { pv }
  })
}

export function createArticle(data) {
  return request({
    url: '/vue-element-admin/article/create',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/vue-element-admin/article/update',
    method: 'post',
    data
  })
}
