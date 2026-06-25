<template>
  <div v-loading="loading">
    <el-table :data="adoptions" stripe style="width:100%">
      <el-table-column prop="petId" label="宠物ID" />
      <el-table-column prop="createdAt" label="申请时间" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button size="small" @click="$router.push(`/pets/${row.petId}`)">查看宠物</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-empty v-if="!loading && adoptions.length === 0" description="暂无领养记录" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyAdoptions } from '@/api/adopt'

const adoptions = ref([])
const loading = ref(false)

const statusType = (status) => {
  const map = { PENDING: 'warning', APPROVED: 'success', REJECTED: 'danger' }
  return map[status] || 'info'
}
const statusText = (status) => {
  const map = { PENDING: '审核中', APPROVED: '已通过', REJECTED: '已拒绝' }
  return map[status] || status
}

onMounted(async () => {
  loading.value = true
  try {
    const res = await getMyAdoptions()
    if (res.code === 200) adoptions.value = res.data
  } catch (err) { console.error('加载失败', err) }
  loading.value = false
})
</script>