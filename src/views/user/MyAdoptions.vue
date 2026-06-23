<template>
  <div v-loading="loading">
    <el-table :data="adoptions" stripe style="width:100%">
      <el-table-column prop="petName" label="宠物名" />
      <el-table-column prop="petType" label="类型" />
      <el-table-column prop="applyTime" label="申请时间" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button size="small" @click="$router.push(`/pet/${row.petId}`)">查看宠物</el-button>
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
  const map = { pending: 'warning', approved: 'success', rejected: 'danger' }
  return map[status] || 'info'
}
const statusText = (status) => {
  const map = { pending: '审核中', approved: '已通过', rejected: '已拒绝' }
  return map[status] || status
}

onMounted(async () => {
  loading.value = true
  const res = await getMyAdoptions()
  adoptions.value = res.data
  loading.value = false
})
</script>