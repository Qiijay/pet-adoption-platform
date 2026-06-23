<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <div ref="abnormalChart" style="height:350px"></div>
      </el-col>
      <el-col :span="12">
        <div ref="trendChart" style="height:350px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getStatistics } from '@/api/dashboard'

const abnormalChart = ref(null)
const trendChart = ref(null)
let abnormalInstance, trendInstance

onMounted(async () => {
  const res = await getStatistics()
  const { abnormalData, trendData } = res.data

  abnormalInstance = echarts.init(abnormalChart.value)
  abnormalInstance.setOption({
    title: { text: '异常占比柱状图' },
    xAxis: { type: 'category', data: abnormalData.categories },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: abnormalData.values }]
  })

  trendInstance = echarts.init(trendChart.value)
  trendInstance.setOption({
    title: { text: '领养趋势' },
    xAxis: { type: 'category', data: trendData.dates },
    yAxis: { type: 'value' },
    series: [{ type: 'line', data: trendData.values }]
  })
})

onUnmounted(() => {
  abnormalInstance?.dispose()
  trendInstance?.dispose()
})
</script>