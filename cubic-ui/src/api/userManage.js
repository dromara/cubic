import request from '@/utils/request'

export function userUpdateStatus(data) {
  return request({
    url: '/user/updateStatus',
    method: 'post',
    data
  })
}
export function userUpdate(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}
export function userDelete(data) {
  return request({
    url: '/user/delete',
    method: 'post',
    data
  })
}
export function userCreate(data) {
  return request({
    url: '/user/create',
    method: 'post',
    data
  })
}
export function userView(query) {
  return request({
    url: '/user/view',
    method: 'get',
    params: query
  })
}
