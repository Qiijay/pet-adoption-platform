<template>
  <div>
    <el-table :data="audits" v-loading="loading">
      <el-table-column prop="applicantName" label="申请人" />
      <el-table-column prop="petName" label="宠物名" />
      <el-table-column prop="applyTime" label="申请时间" />
      <el-table-column prop="reason" label="理由" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="success" size="small" @click="audit(row.id, 'approved')">通过</el-button>
          <el-button type="danger" size="small" @click="audit(row.id, 'rejected')">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPendingAudit, auditAdoption } from '@/api/adopt'
import { ElMessage } from 'element-plus'

const audits = ref([])
const loading = ref(false)

const fetch = async () => {
  loading.value = true
  const res = await getPendingAudit()
  audits.value = res.data
  loading.value = false
}

const audit = async (id, status) => {
  await auditAdoption(id, status)
  ElMessage.success('操作成功')
  fetch()
}

onMounted(fetch)
</script>