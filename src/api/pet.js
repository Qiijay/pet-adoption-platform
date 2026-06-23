import request from '@/utils/request'

export function getPetList(params) {
  return request({
    url: '/pets',
    method: 'get',
    params
  })
}

export function getPetDetail(id) {
  return request({
    url: `/pets/${id}`,
    method: 'get'
  })
}

export function createPet(data) {
  return request({
    url: '/pets',
    method: 'post',
    data
  })
}

export function updatePet(id, data) {
  return request({
    url: `/pets/${id}`,
    method: 'put',
    data
  })
}

export function deletePet(id) {
  return request({
    url: `/pets/${id}`,
    method: 'delete'
  })
}