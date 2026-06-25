<template>
  <div class="audit-adopt">
    <h1>📋 审核管理</h1>
    <!-- 状态筛选 -->
    <div class="filter-bar">
      <el-select v-model="filterStatus" placeholder="筛选状态" clearable @change="loadApplications">
        <el-option label="待审核" value="PENDING" />
        <el-option label="已通过" value="APPROVED" />
        <el-option label="已拒绝" value="REJECTED" />
      </el-select>
      <el-button type="primary" @click="loadApplications" style="margin-left: 10px">刷新</el-button>
    </div>

    <!-- 申请列表 -->
    <el-table :data="applications" v-loading="loading" stripe border style="width: 100%">
      <el-table-column prop="id" label="申请ID" width="70" />
      <el-table-column prop="petId" label="宠物ID" width="70" />
      <el-table-column prop="applicantId" label="申请人ID" width="90" />
      <el-table-column prop="homeEnvironment" label="居住环境" min-width="120" />
      <el-table-column prop="experience" label="养宠经验" min-width="120" />
      <el-table-column prop="reason" label="领养理由" min-width="150" />
      <el-table-column prop="createdAt" label="申请时间" width="180" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="reviewComment" label="审核意见" min-width="120" />
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="{ row }">
          <template v-if="row.status === 'PENDING'">
            <el-button size="small" type="success" @click="openAuditDialog(row, 'APPROVED')">通过</el-button>
            <el-button size="small" type="danger" @click="openAuditDialog(row, 'REJECTED')">拒绝</el-button>
          </template>
          <span v-else style="color: #909399">已处理</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="filteredTotal"
        :page-size="pageSize"
        v-model:current-page="currentPage"
      />
    </div>

    <!-- 审核对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="auditForm" ref="auditFormRef" label-width="100px">
        <el-form-item label="审核意见" prop="reviewComment">
          <el-input
            v-model="auditForm.reviewComment"
            type="textarea"
            :rows="4"
            placeholder="请输入审核意见（可选）"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAudit" :loading="submitting">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPendingAudit, auditAdoption } from '@/api/adopt'

// ---------- 数据 ----------
const applications = ref([])
const loading = ref(false)
const filterStatus = ref('PENDING') // 默认显示待审核
const currentPage = ref(1)
const pageSize = ref(10)

// 审核对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
const auditForm = ref({
  applicationId: null,
  status: '',
  reviewComment: ''
})
const submitting = ref(false)
const auditFormRef = ref(null)

// ---------- 计算 ----------
const filteredTotal = computed(() => applications.value.length)

// 状态辅助
const statusText = (status) => {
  const map = { PENDING: '待审核', APPROVED: '已通过', REJECTED: '已拒绝' }
  return map[status] || status
}
const statusTagType = (status) => {
  const map = { PENDING: 'warning', APPROVED: 'success', REJECTED: 'danger' }
  return map[status] || 'info'
}

// ---------- 方法 ----------
const loadApplications = async () => {
  loading.value = true
  try {
    // 如果后端支持按状态筛选，可以传参，否则前端过滤
    // 这里调用 getPendingAudit 可能只返回待审核，如果要全部则需改用 getAll
    // 我们先用 getPendingAudit（如果后端有），或者修改后端接口支持 status 参数
    // 为了演示，我们假设 getPendingAudit 返回所有申请（根据状态筛选由前端做）
    // 或者我们新增一个 getAllAdoptions 接口
    // 简单起见，我们直接调用 getPendingAudit 并传递 status 参数（需后端支持）
    const res = await getPendingAudit({ status: filterStatus.value || undefined })
    if (res.code === 200) {
      applications.value = res.data
    } else {
      ElMessage.error(res.message || '加载失败')
    }
  } catch (err) {
    ElMessage.error('网络错误')
  }
  loading.value = false
}

// 打开审核对话框
const openAuditDialog = (row, status) => {
  auditForm.value.applicationId = row.id
  auditForm.value.status = status
  auditForm.value.reviewComment = ''
  dialogTitle.value = status === 'APPROVED' ? '通过申请' : '拒绝申请'
  dialogVisible.value = true
}

// 提交审核
const submitAudit = async () => {
  submitting.value = true
  try {
    const { applicationId, status, reviewComment } = auditForm.value
    const res = await auditAdoption(applicationId, status, reviewComment)
    if (res.code === 200) {
      ElMessage.success('审核成功')
      dialogVisible.value = false
      loadApplications()
    } else {
      ElMessage.error(res.message || '审核失败')
    }
  } catch (err) {
    ElMessage.error('网络错误')
  }
  submitting.value = false
}

// ---------- 生命周期 ----------
onMounted(() => {
  loadApplications()
})
</script>

<style scoped>
.audit-adopt {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}
.filter-bar {
  margin-bottom: 20px;
}
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>