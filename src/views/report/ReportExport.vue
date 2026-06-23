<template>
  <div>
    <h3>报表导出</h3>
    <el-date-picker
      v-model="dateRange"
      type="daterange"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
    />
    <div style="margin-top:20px">
      <el-button type="success" @click="exportAdoptReport" :loading="loading1">导出领养统计报表</el-button>
      <el-button type="warning" @click="exportAbnormalReport" :loading="loading2">导出异常明细报表</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { getAdoptReport, getAbnormalReport } from '@/api/dashboard'
import { exportExcel } from '@/utils/exportExcel'

const dateRange = ref([])
const loading1 = ref(false)
const loading2 = ref(false)

const exportAdoptReport = async () => {
  loading1.value = true
  const [start, end] = dateRange.value
  const res = await getAdoptReport({ start, end })
  exportExcel(res.data, '领养统计报表')
  loading1.value = false
}

const exportAbnormalReport = async () => {
  loading2.value = true
  const [start, end] = dateRange.value
  const res = await getAbnormalReport({ start, end })
  exportExcel(res.data, '异常明细报表')
  loading2.value = false
}
</script>