<template>
  <div class="pet-detail" v-if="pet">
    <div class="detail-card">
      <div class="detail-image">
        <img :src="pet.imageUrl || 'https://via.placeholder.com/400x300?text=宠物图片'" alt="宠物图片" />
      </div>
      <div class="detail-info">
        <h1>{{ pet.name }}</h1>
        <p><strong>品种：</strong>{{ pet.species }} - {{ pet.breed || '未知' }}</p>
        <p><strong>年龄：</strong>{{ pet.age }} 个月</p>
        <p><strong>性别：</strong>{{ pet.gender }}</p>
        <p><strong>体重：</strong>{{ pet.weight }} kg</p>
        <p><strong>颜色：</strong>{{ pet.color }}</p>
        <p><strong>健康状况：</strong>{{ pet.healthStatus }}</p>
        <p><strong>状态：</strong><span :class="'status-' + pet.status">{{ pet.status }}</span></p>
        <p><strong>描述：</strong>{{ pet.description }}</p>
        <router-link v-if="pet.status === 'AVAILABLE'" :to="'/apply/' + pet.id" class="btn-apply">
          申请领养
        </router-link>
        <button v-else class="btn-disabled" disabled>已不可领养</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getPetDetail } from '@/api/pet'

const route = useRoute()
const pet = ref(null)

onMounted(async () => {
  try {
    const id = route.params.id
    const res = await getPetDetail(id)
    if (res.code === 200) {
      pet.value = res.data
    }
  } catch (err) {
    console.error('加载宠物详情失败', err)
  }
})
</script>

<style scoped>
.pet-detail {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}
.detail-card {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  background: #fff;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}
.detail-image img {
  width: 100%;
  border-radius: 10px;
}
.detail-info h1 {
  font-size: 32px;
  color: #2c3e50;
  margin-bottom: 20px;
}
.detail-info p {
  margin: 10px 0;
  font-size: 16px;
  color: #555;
}
.status-AVAILABLE {
  color: #27ae60;
  font-weight: bold;
}
.status-RESERVED {
  color: #f39c12;
  font-weight: bold;
}
.status-ADOPTED {
  color: #95a5a6;
  font-weight: bold;
}
.btn-apply {
  display: inline-block;
  margin-top: 20px;
  padding: 12px 40px;
  background: #27ae60;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 18px;
  cursor: pointer;
  text-decoration: none;
}
.btn-apply:hover {
  background: #219a52;
}
.btn-disabled {
  margin-top: 20px;
  padding: 12px 40px;
  background: #95a5a6;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 18px;
  cursor: not-allowed;
}
@media (max-width: 768px) {
  .detail-card {
    grid-template-columns: 1fr;
  }
}
</style>