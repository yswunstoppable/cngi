import { post } from '@/api/api.js'
import { baseDel, baseEdit } from '@/api/api'

const baseUrl = '/user'

export function login (data) {
  return post(baseUrl + '/login', data)
}

export function getInfo () {
  return post(baseUrl + '/getInfo')
}

export function logout () {
  return post(baseUrl + '/logout')
}

export function listUser (data) {
  return post(baseUrl + '/list', data)
}

export function judgeUserField (id, field, value) {
  return post(baseUrl + '/judge', {
    id: id,
    field: field,
    value: value
  })
}

export function editUser (data) {
  return baseEdit(baseUrl, data)
}

export function delUser (ids) {
  return baseDel(baseUrl, ids)
}

export const resetPass = (id) => {
  return post(baseUrl + '/resetPass', {
    id: id
  })
}
