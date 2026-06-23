import { defineStore } from 'pinia'
import { getPetList, getPetDetail } from '@/api/pet'

export const usePetStore = defineStore('pet', {
  state: () => ({
    list: [],
    currentPet: null,
    total: 0,
    loading: false
  }),
  actions: {
    async fetchList(params) {
      this.loading = true
      const res = await getPetList(params)
      this.list = res.data.records
      this.total = res.data.total
      this.loading = false
    },
    async fetchDetail(id) {
      const res = await getPetDetail(id)
      this.currentPet = res.data
    }
  }
})