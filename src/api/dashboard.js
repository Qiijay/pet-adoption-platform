import request from '@/utils/request'

export function getDashboardStats() {
  return request({
    url: '/admin/dashboard',
    method: 'get'
  })
}

// 详细统计数据（用于DataStats页面）
export function getStatistics() {
  return request({
    url: '/admin/statistics',
    method: 'get'
  })
}

export function getAdoptReport(params) {
  return request({
    url: '/admin/report/adopt',
    method: 'get',
    params
  })
}

export function getAbnormalReport(params) {
  return request({
    url: '/admin/report/abnormal',
    method: 'get',
    params
  })
}