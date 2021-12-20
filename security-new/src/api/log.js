import { post } from '@/api/api'

export function listAttackSourceLog (data) {
  return post('/log/attack/list', data)
}

export function listExuLog (data) {
  return post('/log/command/list', data)
}

export function getLastAttack () {
  return post('/log/attack/getLast')
}

export function listAttackByAddress () {
  return post('/log/attack/listByAddress')
}

export function listAttackStatics (data) {
  return post('/log/attack/listStatics')
}

export function staticsAttackByMonth () {
  return post('/log/attack/staticsByMonth')
}
