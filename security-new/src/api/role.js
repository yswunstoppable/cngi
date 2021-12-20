import { baseDel, baseEdit, baseJudge, baseList } from '@/api/api'

const urlPrefix = '/role'

export function listRole (data) {
  return baseList(urlPrefix, data)
}

export function judgeRole (id, name) {
  const data = { id: id, name: name }
  return baseJudge(urlPrefix, data)
}

export function editRole (data) {
  return baseEdit(urlPrefix, data)
}

export function delRole (ids) {
  return baseDel(urlPrefix, ids)
}
