import storage from 'store'
import { login, getInfo, logout } from '@/api/user'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'
import sha1 from 'sha1'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    hasGetInfo: false,
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_HAS_GET_INFO: (state, hasGetInfo) => {
      state.hasGetInfo = hasGetInfo
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      const { username, password, keepLogin } = userInfo
      return new Promise((resolve, reject) => {
        const pass = sha1(password)
        login({
          username: username,
          password: pass,
          keepLogin: keepLogin
        }).then(response => {
          const { data } = response
          console.log('data', data)
          storage.set(ACCESS_TOKEN, data['token'], 7 * 24 * 60 * 60 * 1000)
          commit('SET_TOKEN', data['token'])
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const { data } = response
          commit('SET_HAS_GET_INFO', true)
          commit('SET_NAME', { name: data.username, welcome: welcome() })
          // commit('SET_AVATAR', data.avatar)
          commit('SET_INFO', data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          storage.remove(ACCESS_TOKEN)
        })
      })
    },

    // 信息失效
    ResetToken ({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_HAS_GET_INFO', false)
        commit('SET_PERMISSIONS', [])
        storage.remove(ACCESS_TOKEN)
        resolve()
      })
    }

  }
}

export default user
