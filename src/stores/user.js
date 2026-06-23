import { defineStore } from 'pinia'
import { login, register, getUserInfo } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: null,
    role: localStorage.getItem('role') || ''
  }),
  actions: {
    async loginAction(credentials) {
      const { data } = await login(credentials)
      this.token = data.token
      this.role = data.role
      localStorage.setItem('token', data.token)
      localStorage.setItem('role', data.role)
    },
    async registerAction(form) {
      await register(form)
    },
    async fetchUserInfo() {
      const { data } = await getUserInfo()
      this.userInfo = data
    },
    logout() {
      this.token = ''
      this.role = ''
      this.userInfo = null
      localStorage.clear()
    }
  }
})