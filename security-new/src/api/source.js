import { baseDel, baseEdit, baseList, post } from '@/api/api'

const baseUrl = '/source'

export const listPerceptionSource = (data) => {
  return baseList(baseUrl, data)
}

export const judgeSourceName = (id, name) => {
  return post(baseUrl + '/judgeName', {
    id: id,
    name: name
  })
}

export const editPerceptionSource = (data) => {
  return baseEdit(baseUrl, data)
}

export const delPerceptionSource = (ids) => {
  return baseDel(baseUrl, ids)
}

export const connectSource = (id, captcha) => {
  return post(baseUrl + '/connect', { id: id, captcha: captcha })
}

export const getPerceptionSource = (id) => {
  return post('/source/get', { id: id })
}

export const listValidatedSource = () => {
  return post('/source/listValidated')
}
