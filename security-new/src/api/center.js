import { baseDel, baseEdit, baseList, post } from '@/api/api'

const boundaryBaseUrl = '/boundary'
const commandBaseUrl = '/command'

export const listBoundary = (data) => {
  return baseList(boundaryBaseUrl, data)
}

export const editBoundary = (data) => {
  return baseEdit(boundaryBaseUrl, data)
}

export const listUsedBoundary = () => {
  return post(boundaryBaseUrl + '/listUsed')
}

export const delBoundary = (ids) => {
  return baseDel(boundaryBaseUrl, ids)
}

export function judgeBoundaryField (id, field, value) {
  return post(boundaryBaseUrl + '/judge', {
    id: id,
    field: field,
    value: value
  })
}

export const listCommand = (data) => {
  return baseList(commandBaseUrl, data)
}

export const listUsedCommand = () => {
  return post(commandBaseUrl + '/listUsed')
}

export const editCommand = (data) => {
  return baseEdit(commandBaseUrl, data)
}

export const delCommand = (ids) => {
  return baseDel(commandBaseUrl, ids)
}

export function judgeCommandField (id, field, value) {
  return post(commandBaseUrl + '/judge', {
    id: id,
    field: field,
    value: value
  })
}
