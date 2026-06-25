import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: '/auth/register',
    method: 'post',
    data
  })
}

// 获取用户详细信息
export function getUserInfo() {
  return request({
    url: '/users/info',
    method: 'get'
  })
}