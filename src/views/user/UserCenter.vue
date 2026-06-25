<template>
  <div class="user-center">
    <h1>个人中心</h1>
    <el-card v-loading="loading">
      <el-descriptions title="个人信息" :column="1" border>
        <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ userInfo.role }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userInfo.phone || '未设置' }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email || '未设置' }}</el-descriptions-item>
        <el-descriptions-item label="真实姓名">{{ userInfo.realName || '未设置' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
    <div style="margin-top:20px;">
      <h2>我的领养记录</h2>
      <MyAdoptions />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import MyAdoptions from './MyAdoptions.vue'
import { getUserInfo } from '@/api/auth'

const userInfo = ref({ username: '', role: '', phone: '', email: '', realName: '' })
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    const res = await getUserInfo()
    if (res.code === 200) userInfo.value = res.data
  } catch (err) { console.error('获取用户信息失败', err) }
  loading.value = false
})
</script>

<style scoped>
.user-center { max-width: 800px; margin: 40px auto; padding: 20px; }
.user-center h1 { text-align: center; margin-bottom: 30px; color: #2c3e50; }
</style>