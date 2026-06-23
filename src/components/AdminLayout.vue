<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          router
          background-color="#f5f7fa"
        >
          <el-menu-item index="/admin/dashboard">数据看板</el-menu-item>
          <el-menu-item index="/admin/pets">宠物管理</el-menu-item>
          <el-menu-item index="/admin/audit">审核管理</el-menu-item>
          <el-menu-item index="/admin/stats">详细统计</el-menu-item>
          <el-menu-item index="/admin/report">报表导出</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const activeMenu = computed(() => route.path)
</script>

<style scoped>
.admin-layout {
  margin: 20px;
}
</style>