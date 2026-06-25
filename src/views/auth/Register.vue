<template>
  <div class="register-container">
    <el-card class="register-card">
      <h2>注册账号</h2>
      <el-form :model="form" :rules="rules" ref="registerForm">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="form.phone" placeholder="手机号" prefix-icon="Phone" />
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" placeholder="邮箱" prefix-icon="Message" />
        </el-form-item>
        <el-form-item prop="realName">
          <el-input v-model="form.realName" placeholder="真实姓名" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" :loading="loading" style="width:100%">注册</el-button>
        </el-form-item>
      </el-form>
      <p>已有账号？<router-link to="/login">去登录</router-link></p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '@/api/auth'

const router = useRouter()
const registerForm = ref(null)
const loading = ref(false)
const form = reactive({ username: '', password: '', phone: '', email: '', realName: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleRegister = async () => {
  if (!registerForm.value) return
  await registerForm.value.validate()
  loading.value = true
  try {
    const res = await register(form)
    if (res.code === 200) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    } else {
      ElMessage.error(res.message || '注册失败')
    }
  } catch (e) {
    ElMessage.error('注册失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}
.register-card {
  width: 450px;
}
</style>