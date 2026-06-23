<template>
  <el-menu
    :default-active="activeMenu"
    mode="horizontal"
    router
    background-color="#409EFF"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-menu-item index="/home">首页</el-menu-item>
    <el-menu-item index="/pets">宠物列表</el-menu-item>
    <template v-if="userStore.token">
      <el-menu-item index="/user">个人中心</el-menu-item>
      <el-menu-item index="/user/adoptions">我的领养</el-menu-item>
    </template>
    <template v-if="userStore.role === 'shelter'">
      <el-sub-menu index="/admin">
        <template #title>管理后台</template>
        <el-menu-item index="/admin/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/admin/pets">宠物管理</el-menu-item>
        <el-menu-item index="/admin/audit">审核管理</el-menu-item>
        <el-menu-item index="/admin/stats">详细统计</el-menu-item>
        <el-menu-item index="/admin/report">报表导出</el-menu-item>
      </el-sub-menu>
    </template>
    <div class="auth-buttons">
      <template v-if="!userStore.token">
        <el-button text style="color:#fff" @click="$router.push('/login')">登录</el-button>
        <el-button text style="color:#fff" @click="$router.push('/register')">注册</el-button>
      </template>
      <el-button v-else text style="color:#fff" @click="handleLogout">退出</el-button>
    </div>
  </el-menu>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
const activeMenu = computed(() => {
  if (route.path.startsWith('/admin')) return '/admin'
  return route.path
})

const handleLogout = () => {
  userStore.logout()
  window.location.href = '/home'
}
</script>

<style scoped>
.auth-buttons {
  position: absolute;
  right: 20px;
  top: 0;
  height: 60px;
  display: flex;
  align-items: center;
}
</style>