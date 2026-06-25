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

export function getPendingAudit(params) {
  return request({
    url: '/adoptions/audit',
    method: 'get',
    params
  })
}

export function auditAdoption(id, status, remark) {
  return request({
    url: `/adoptions/${id}/audit`,
    method: 'put',
    data: { status, remark }
  })
}