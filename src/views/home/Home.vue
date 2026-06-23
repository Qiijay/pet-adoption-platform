<template>
  <div class="home">
    <!-- 搜索筛选区 -->
    <div class="search-section">
      <el-input
        v-model="searchQuery"
        placeholder="搜索宠物名称"
        clearable
        @change="fetchPets"
        class="search-input"
      >
        <template #prefix><el-icon><Search /></el-icon></template>
      </el-input>
      <el-select v-model="filterType" placeholder="宠物类型" clearable @change="fetchPets" class="filter-select">
        <el-option label="狗" value="dog" />
        <el-option label="猫" value="cat" />
        <el-option label="其他" value="other" />
      </el-select>
    </div>

    <!-- 宠物卡片网格 -->
    <div class="pet-grid" v-loading="loading">
      <PetCard v-for="pet in petList" :key="pet.id" :pet="pet" @click="goDetail(pet.id)" />
      <el-empty v-if="!loading && petList.length === 0" description="暂无宠物数据" />
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="fetchPets"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPetList } from '@/api/pet'
import PetCard from '@/components/PetCard.vue'

const router = useRouter()
const searchQuery = ref('')
const filterType = ref('')
const petList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

const fetchPets = async () => {
  loading.value = true
  try {
    const res = await getPetList({
      page: currentPage.value,
      size: pageSize.value,
      keyword: searchQuery.value,
      type: filterType.value
    })
    petList.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const goDetail = (id) => {
  router.push(`/pet/${id}`)
}

onMounted(() => {
  fetchPets()
})
</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
.search-section {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}
.search-input {
  width: 300px;
}
.filter-select {
  width: 150px;
}
.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}
.pagination {
  text-align: center;
  margin-top: 30px;
}
</style>