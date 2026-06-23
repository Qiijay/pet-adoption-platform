<template>
  <div class="user-center">
    <h2>个人中心</h2>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="个人信息" name="info">
        <el-form :model="userForm" ref="userFormRef" style="max-width:500px">
          <el-form-item label="昵称">
            <el-input v-model="userForm.nickname" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="userForm.phone" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="userForm.email" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveInfo">保存修改</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="我的领养记录" name="adoptions">
        <MyAdoptions />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import MyAdoptions from './MyAdoptions.vue'

const userStore = useUserStore()
const activeTab = ref('info')
const userForm = reactive({
  nickname: '',
  phone: '',
  email: ''
})

onMounted(async () => {
  await userStore.fetchUserInfo()
  if (userStore.userInfo) {
    Object.assign(userForm, userStore.userInfo)
  }
})

const saveInfo = async () => {
  await userStore.updateUserInfo(userForm)
  ElMessage.success('信息更新成功')
}
</script>

<style scoped>
.user-center {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
}
</style>