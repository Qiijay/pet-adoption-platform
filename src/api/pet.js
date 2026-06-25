import request from '@/utils/request'

export function getPetList() {
  return request({
    url: '/pets/list',
    method: 'get'
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
    url: '/pets/add',
    method: 'post',
    data
  })
}