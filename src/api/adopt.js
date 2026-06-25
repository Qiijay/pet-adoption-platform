import request from '@/utils/request'

export function submitAdoption(data) {
  return request({
    url: '/adoptions/apply',
    method: 'post',
    data
  })
}

export function getMyAdoptions() {
  return request({
    url: '/adoptions/my',
    method: 'get'
  })
}