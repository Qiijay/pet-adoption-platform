<template>
  <el-menu
    :default-active="activeMenu"
    mode="horizontal"
    router
    background-color="#409EFF"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-menu-item index="/">首页</el-menu-item>
    <el-menu-item index="/pets">宠物列表</el-menu-item>
    
    <!-- 登录后显示 -->
    <template v-if="isLoggedIn">
      <el-menu-item index="/user-center">个人中心</el-menu-item>
      <el-menu-item index="/my-adoptions">我的领养</el-menu-item>
      
      <!-- 管理员菜单：SYSTEM_ADMIN 或 SHELTER_ADMIN -->
      <template v-if="isAdmin">
        <el-sub-menu index="/admin">
          <template #title>管理后台</template>
          <el-menu-item index="/admin/pets">宠物管理</el-menu-item>
          <el-menu-item index="/admin/audit">审核管理</el-menu-item>
        </el-sub-menu>
      </template>
    </template>

    <div class="auth-buttons">
      <template v-if="!isLoggedIn">
        <el-button text style="color:#fff" @click="$router.push('/login')">登录</el-button>
        <el-button text style="color:#fff" @click="$router.push('/register')">注册</el-button>
      </template>
      <el-button v-else text style="color:#fff" @click="handleLogout">退出</el-button>
    </div>
  </el-menu>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const isLoggedIn = computed(() => !!localStorage.getItem('token'))
const userRole = computed(() => localStorage.getItem('role') || '')
const isAdmin = computed(() => {
  const role = userRole.value
  return role === 'SYSTEM_ADMIN' || role === 'SHELTER_ADMIN'
})

const activeMenu = computed(() => {
  return route.path
})

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  localStorage.removeItem('userId')
  localStorage.removeItem('role')
  router.push('/login')
  window.location.reload()
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