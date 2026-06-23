<template>
  <div class="pet-detail" v-loading="loading">
    <el-row :gutter="20">
      <el-col :xs="24" :md="12">
        <el-image
          :src="pet.image"
          fit="cover"
          style="width:100%; border-radius:8px; aspect-ratio:4/3"
          :preview-src-list="[pet.image]"
          lazy
        />
      </el-col>
      <el-col :xs="24" :md="12">
        <h1>{{ pet.name }}</h1>
        <el-tag size="large">{{ pet.type === 'dog' ? '狗' : pet.type === 'cat' ? '猫' : '其他' }}</el-tag>
        <p class="desc">{{ pet.description }}</p>
        <p>年龄：{{ pet.age }}个月</p>
        <p>救助站：{{ pet.shelterName }}</p>
        <p>发布日期：{{ pet.createTime }}</p>
        <el-button
          v-if="userStore.token && userStore.role === 'user'"
          type="primary"
          size="large"
          @click="showAdoptForm = true"
        >
          申请领养
        </el-button>
        <el-button v-else-if="!userStore.token" @click="$router.push('/login')">
          登录后申请领养
        </el-button>
      </el-col>
    </el-row>

    <!-- 领养申请对话框 -->
    <el-dialog v-model="showAdoptForm" title="领养申请" width="500px">
      <el-form :model="adoptForm" :rules="adoptRules" ref="adoptFormRef">
        <el-form-item label="申请人姓名" prop="applicantName">
          <el-input v-model="adoptForm.applicantName" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="adoptForm.phone" />
        </el-form-item>
        <el-form-item label="申请理由" prop="reason">
          <el-input type="textarea" :rows="4" v-model="adoptForm.reason" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdoptForm = false">取消</el-button>
        <el-button type="primary" @click="submitAdopt" :loading="submitting">提交申请</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getPetDetail } from '@/api/pet'
import { submitAdoption } from '@/api/adopt'
import { ElMessage } from 'element-plus'

const route = useRoute()
const userStore = useUserStore()
const pet = ref({})
const loading = ref(false)
const showAdoptForm = ref(false)
const submitting = ref(false)
const adoptFormRef = ref(null)
const adoptForm = reactive({
  applicantName: '',
  phone: '',
  reason: ''
})
const adoptRules = {
  applicantName: [{ required: true, message: '请输入姓名' }],
  phone: [{ required: true, message: '请输入电话' }],
  reason: [{ required: true, message: '请填写申请理由' }]
}

const fetchDetail = async () => {
  loading.value = true
  const res = await getPetDetail(route.params.id)
  pet.value = res.data
  loading.value = false
}

const submitAdopt = async () => {
  if (!adoptFormRef.value) return
  await adoptFormRef.value.validate()
  submitting.value = true
  try {
    await submitAdoption({
      petId: pet.value.id,
      ...adoptForm
    })
    ElMessage.success('申请已提交，等待审核')
    showAdoptForm.value = false
  } finally {
    submitting.value = false
  }
}

onMounted(fetchDetail)
</script>

<style scoped>
.pet-detail {
  max-width: 1000px;
  margin: 20px auto;
  padding: 20px;
}
.desc {
  margin: 15px 0;
  line-height: 1.8;
}
</style>