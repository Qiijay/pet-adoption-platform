<template>
  <div class="pet-manage">
    <div class="header">
      <h1>🐾 宠物管理</h1>
      <el-button type="primary" @click="openAddDialog">发布新宠物</el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索宠物名称"
        clearable
        style="width: 200px; margin-right: 10px;"
        @input="handleSearch"
      />
      <el-select v-model="searchStatus" placeholder="选择状态" clearable @change="handleSearch">
        <el-option label="待领养" value="AVAILABLE" />
        <el-option label="已预留" value="RESERVED" />
        <el-option label="已领养" value="ADOPTED" />
      </el-select>
    </div>

    <!-- 宠物列表 -->
    <el-table :data="filteredPets" v-loading="loading" stripe border style="width: 100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="名称" width="100" />
      <el-table-column prop="species" label="品种" width="80" />
      <el-table-column prop="breed" label="具体品种" width="100" />
      <el-table-column prop="age" label="年龄(月)" width="80" />
      <el-table-column prop="gender" label="性别" width="60" />
      <el-table-column prop="weight" label="体重(kg)" width="80" />
      <el-table-column prop="color" label="颜色" width="80" />
      <el-table-column prop="healthStatus" label="健康状况" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="openEditDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="filteredPets.length"
        :page-size="10"
        v-model:current-page="currentPage"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入宠物名称" />
        </el-form-item>
        <el-form-item label="品种" prop="species">
          <el-input v-model="form.species" placeholder="如：猫、狗" />
        </el-form-item>
        <el-form-item label="具体品种" prop="breed">
          <el-input v-model="form.breed" placeholder="如：橘猫、金毛" />
        </el-form-item>
        <el-form-item label="年龄(月)" prop="age">
          <el-input-number v-model="form.age" :min="0" :step="1" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="公">公</el-radio>
            <el-radio label="母">母</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="体重(kg)" prop="weight">
          <el-input-number v-model="form.weight" :min="0" :step="0.1" :precision="1" />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="如：橘色、白色" />
        </el-form-item>
        <el-form-item label="健康状况" prop="healthStatus">
          <el-input v-model="form.healthStatus" placeholder="如：健康、需要治疗" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请描述宠物特点" />
        </el-form-item>
        <el-form-item label="图片URL" prop="imageUrl">
          <el-input v-model="form.imageUrl" placeholder="输入图片链接" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="待领养" value="AVAILABLE" />
            <el-option label="已预留" value="RESERVED" />
            <el-option label="已领养" value="ADOPTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="救助站ID" prop="shelterId">
          <el-input-number v-model="form.shelterId" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitting">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPetList, createPet, updatePet, deletePet } from '@/api/pet'

// ---------- 数据 ----------
const pets = ref([])
const loading = ref(false)
const searchKeyword = ref('')
const searchStatus = ref('')
const currentPage = ref(1)

// 表单相关
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const submitting = ref(false)
const formRef = ref(null)
const form = ref({
  name: '',
  species: '',
  breed: '',
  age: 0,
  gender: '公',
  weight: 0,
  color: '',
  healthStatus: '',
  description: '',
  imageUrl: '',
  status: 'AVAILABLE',
  shelterId: 1
})

// 校验规则
const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  species: [{ required: true, message: '请输入品种', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  shelterId: [{ required: true, message: '请输入救助站ID', trigger: 'blur' }]
}

// ---------- 计算 ----------
const filteredPets = computed(() => {
  let list = pets.value
  if (searchKeyword.value) {
    list = list.filter(p => p.name.includes(searchKeyword.value))
  }
  if (searchStatus.value) {
    list = list.filter(p => p.status === searchStatus.value)
  }
  return list
})

// 状态辅助
const statusText = (status) => {
  const map = { AVAILABLE: '待领养', RESERVED: '已预留', ADOPTED: '已领养' }
  return map[status] || status
}
const statusTagType = (status) => {
  const map = { AVAILABLE: 'success', RESERVED: 'warning', ADOPTED: 'info' }
  return map[status] || 'info'
}

// ---------- 方法 ----------
const loadPets = async () => {
  loading.value = true
  try {
    const res = await getPetList()
    if (res.code === 200) {
      pets.value = res.data
    } else {
      ElMessage.error(res.message || '加载失败')
    }
  } catch (err) {
    ElMessage.error('网络错误')
  }
  loading.value = false
}

const handleSearch = () => {
  currentPage.value = 1
}

// 打开添加对话框
const openAddDialog = () => {
  dialogTitle.value = '发布新宠物'
  isEdit.value = false
  resetForm()
  dialogVisible.value = true
}

// 打开编辑对话框
const openEditDialog = (row) => {
  dialogTitle.value = '编辑宠物信息'
  isEdit.value = true
  editId.value = row.id
  Object.assign(form.value, {
    name: row.name,
    species: row.species,
    breed: row.breed || '',
    age: row.age || 0,
    gender: row.gender || '公',
    weight: row.weight || 0,
    color: row.color || '',
    healthStatus: row.healthStatus || '',
    description: row.description || '',
    imageUrl: row.imageUrl || '',
    status: row.status || 'AVAILABLE',
    shelterId: row.shelterId || 1
  })
  dialogVisible.value = true
}

// 重置表单
const resetForm = () => {
  form.value = {
    name: '',
    species: '',
    breed: '',
    age: 0,
    gender: '公',
    weight: 0,
    color: '',
    healthStatus: '',
    description: '',
    imageUrl: '',
    status: 'AVAILABLE',
    shelterId: 1
  }
  editId.value = null
  if (formRef.value) formRef.value.resetFields()
}

// 提交表单
const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate()
  submitting.value = true
  try {
    let res
    if (isEdit.value) {
      res = await updatePet(editId.value, form.value)
    } else {
      res = await createPet(form.value)
    }
    if (res.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
      dialogVisible.value = false
      loadPets()
    } else {
      ElMessage.error(res.message || '操作失败')
    }
  } catch (err) {
    ElMessage.error('网络错误，请重试')
  }
  submitting.value = false
}

// 删除宠物
const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这只宠物吗？', '删除确认', {
    type: 'warning'
  }).then(async () => {
    try {
      const res = await deletePet(id)
      if (res.code === 200) {
        ElMessage.success('删除成功')
        loadPets()
      } else {
        ElMessage.error(res.message || '删除失败')
      }
    } catch (err) {
      ElMessage.error('网络错误')
    }
  }).catch(() => {})
}

// ---------- 生命周期 ----------
onMounted(() => {
  loadPets()
})
</script>

<style scoped>
.pet-manage {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.search-bar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>