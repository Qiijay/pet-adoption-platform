import request from '@/utils/request'

// 用户提交领养申请
export function submitAdoption(data) {
  return request({
    url: '/adoptions',
    method: 'post',
    data
  })
}

// 用户查看自己的领养记录
export function getMyAdoptions() {
  return request({
    url: '/adoptions/my',
    method: 'get'
  })
}

// 救助站获取待审核申请
export function getPendingAudit() {
  return request({
    url: '/adoptions/audit',
    method: 'get'
  })
}

// 审核领养申请
export function auditAdoption(id, status, remark) {
  return request({
    url: `/adoptions/${id}/audit`,
    method: 'put',
    data: { status, remark }
  })
}