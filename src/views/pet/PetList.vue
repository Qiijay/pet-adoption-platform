<template>
  <div class="pet-list-page">
    <h2>宠物列表</h2>
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="关键词">
        <el-input v-model="searchForm.keyword" placeholder="名称" clearable />
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="searchForm.type" clearable>
          <el-option label="狗" value="dog" />
          <el-option label="猫" value="cat" />
          <el-option label="其他" value="other" />
        </el-select>
      </el-form-item>
      <el-form-item label="年龄范围">
        <el-input-number v-model="searchForm.minAge" :min="0" /> -
        <el-input-number v-model="searchForm.maxAge" :min="0" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <div v-loading="loading" class="pet-grid">
      <PetCard v-for="pet in petList" :key="pet.id" :pet="pet" @click="goDetail(pet.id)" />
      <el-empty v-if="!loading && petList.length === 0" />
    </div>
    <el-pagination
      style="text-align:center;margin-top:20px"
      v-model:current-page="page"
      :page-size="size"
      :total="total"
      @current-change="fetchPets"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPetList } from '@/api/pet'
import PetCard from '@/components/PetCard.vue'

const router = useRouter()
const searchForm = reactive({
  keyword: '',
  type: '',
  minAge: null,
  maxAge: null
})
const petList = ref([])
const loading = ref(false)
const page = ref(1)
const size = ref(12)
const total = ref(0)

const fetchPets = async () => {
  loading.value = true
  const params = {
    page: page.value,
    size: size.value,
    ...searchForm
  }
  const res = await getPetList(params)
  petList.value = res.data.records
  total.value = res.data.total
  loading.value = false
}

const search = () => {
  page.value = 1
  fetchPets()
}

const reset = () => {
  Object.assign(searchForm, { keyword: '', type: '', minAge: null, maxAge: null })
  search()
}

const goDetail = (id) => router.push(`/pet/${id}`)

onMounted(fetchPets)
</script>

<style scoped>
.pet-list-page {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}
.search-form {
  margin-bottom: 20px;
}
.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}
</style>