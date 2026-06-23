<template>
  <div>
    <el-button type="primary" @click="showAdd = true">发布宠物</el-button>
    <el-table :data="petList" v-loading="loading" style="margin-top:20px">
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="age" label="年龄（月）" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑宠物' : '发布宠物'" width="500px">
      <el-form :model="petForm" ref="petFormRef">
        <el-form-item label="名称" prop="name" :rules="[{required:true}]">
          <el-input v-model="petForm.name" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="petForm.type">
            <el-option label="狗" value="dog" />
            <el-option label="猫" value="cat" />
            <el-option label="其他" value="other" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄（月）" prop="age">
          <el-input-number v-model="petForm.age" :min="0" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="petForm.description" />
        </el-form-item>
        <el-form-item label="图片URL" prop="image">
          <el-input v-model="petForm.image" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPet">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getPetList, createPet, updatePet, deletePet } from '@/api/pet'
import { ElMessage, ElMessageBox } from 'element-plus'

const petList = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const editId = ref(null)
const petFormRef = ref(null)
const petForm = reactive({
  name: '',
  type: 'dog',
  age: 0,
  description: '',
  image: ''
})

const fetchPets = async () => {
  loading.value = true
  const res = await getPetList({ page: 1, size: 100 }) // 管理员显示全部
  petList.value = res.data.records
  loading.value = false
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(petForm, { name: '', type: 'dog', age: 0, description: '', image: '' })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  editId.value = row.id
  Object.assign(petForm, row)
  dialogVisible.value = true
}

const submitPet = async () => {
  if (!petFormRef.value) return
  await petFormRef.value.validate()
  if (isEdit.value) {
    await updatePet(editId.value, petForm)
    ElMessage.success('修改成功')
  } else {
    await createPet(petForm)
    ElMessage.success('发布成功')
  }
  dialogVisible.value = false
  fetchPets()
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除该宠物吗？', '警告', { type: 'warning' })
    .then(async () => {
      await deletePet(id)
      ElMessage.success('删除成功')
      fetchPets()
    })
    .catch(() => {})
}

onMounted(fetchPets)
</script>