import { baseEdit, baseList, post } from '@/api/api'

const baseUrl = '/timer'

export function listTimer (data) {
  return baseList(baseUrl, data)
}

export function editTimer (data) {
  return baseEdit(baseUrl, data)
}

export function changeStatus (id, status) {
  return post(baseUrl + '/changeStatus', { id: id, status: status })
}
