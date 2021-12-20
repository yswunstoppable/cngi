import { baseList, post } from '@/api/api.js'
import { baseDel, baseEdit } from '@/api/api'

const baseUrl = '/shreshold'

export function getShreshold () {
  return post(baseUrl + '/get')
}

export function listShreshold (data) {
  return baseList(baseUrl, data)
}

export function editShreshold (data) {
  return baseEdit(baseUrl, data)
}

export function delShreshold (ids) {
  return baseDel(baseUrl, ids)
}

export function judgeShresholdField (id, field, value) {
  return post(baseUrl + '/judge', {
    id: id,
    field: field,
    value: value
  })
}
