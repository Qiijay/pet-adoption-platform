<template>
  <div>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="6" v-for="item in cards" :key="item.label">
        <el-card>
          <p class="card-label">{{ item.label }}</p>
          <h3 class="card-value">{{ item.value }}</h3>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :xs="24" :md="12">
        <div ref="trendChart" style="height:300px"></div>
      </el-col>
      <el-col :xs="24" :md="12">
        <div ref="pieChart" style="height:300px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboardStats } from '@/api/dashboard'

const trendChart = ref(null)
const pieChart = ref(null)
let trendInstance, pieInstance
const cards = ref([
  { label: '总宠物数', value: 0 },
  { label: '待审核申请', value: 0 },
  { label: '今日领养', value: 0 },
  { label: '异常占比', value: '0%' }
])

const initCharts = (trendData, pieData) => {
  trendInstance = echarts.init(trendChart.value)
  trendInstance.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: trendData.dates },
    yAxis: { type: 'value' },
    series: [{ data: trendData.values, type: 'line', smooth: true, areaStyle: {} }]
  })

  pieInstance = echarts.init(pieChart.value)
  pieInstance.setOption({
    tooltip: { trigger: 'item' },
    series: [{ type: 'pie', radius: '60%', data: pieData }]
  })
}

onMounted(async () => {
  const res = await getDashboardStats()
  const { stats, trend, pie } = res.data
  cards.value[0].value = stats.totalPets
  cards.value[1].value = stats.pendingAudit
  cards.value[2].value = stats.todayAdopt
  cards.value[3].value = stats.abnormalRate + '%'
  initCharts(trend, pie)
})

onUnmounted(() => {
  trendInstance?.dispose()
  pieInstance?.dispose()
})
</script>

<style scoped>
.card-label { color: #666; }
.card-value { font-size: 24px; margin: 10px 0; }
</style>