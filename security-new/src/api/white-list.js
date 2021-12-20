import { baseDel, baseEdit, baseList, post } from '@/api/api'

const baseUrl = '/whitelist'

export const listWhiteList = (data) => {
  return baseList(baseUrl, data)
}

export const editWhiteList = (data) => {
  return baseEdit(baseUrl, data)
}

export const delWhiteList = (ids) => {
  return baseDel(baseUrl, ids)
}

export function judgeWhitelistField (id, field, value) {
  return post(baseUrl + '/judge', {
    id: id,
    field: field,
    value: value
  })
}
