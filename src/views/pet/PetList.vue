<template>
  <div class="pet-list">
    <h1>🐾 待领养宠物</h1>
    <div class="pet-grid">
      <div v-for="pet in pets" :key="pet.id" class="pet-card">
        <div class="pet-image">
          <img :src="pet.imageUrl || 'https://via.placeholder.com/300x200?text=宠物图片'" alt="宠物图片" />
        </div>
        <div class="pet-info">
          <h3>{{ pet.name }}</h3>
          <p><strong>品种：</strong>{{ pet.species }} - {{ pet.breed || '未知' }}</p>
          <p><strong>年龄：</strong>{{ pet.age }} 个月</p>
          <p><strong>性别：</strong>{{ pet.gender }}</p>
          <p><strong>状态：</strong><span :class="'status-' + pet.status">{{ pet.status }}</span></p>
          <router-link :to="'/pets/' + pet.id" class="btn-detail">查看详情</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPetList } from '@/api/pet'

const pets = ref([])

onMounted(async () => {
  try {
    const res = await getPetList()
    if (res.code === 200) {
      pets.value = res.data
    }
  } catch (err) {
    console.error('加载宠物列表失败', err)
  }
})
</script>

<style scoped>
.pet-list {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
.pet-list h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #2c3e50;
}
.pet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px;
}
.pet-card {
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  transition: transform 0.3s;
}
.pet-card:hover {
  transform: translateY(-5px);
}
.pet-image img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}
.pet-info {
  padding: 20px;
}
.pet-info h3 {
  font-size: 20px;
  color: #2c3e50;
  margin-bottom: 10px;
}
.pet-info p {
  margin: 5px 0;
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
.btn-detail {
  display: inline-block;
  margin-top: 10px;
  padding: 8px 20px;
  background: #3498db;
  color: #fff;
  border-radius: 5px;
  text-decoration: none;
  font-size: 14px;
}
.btn-detail:hover {
  background: #2980b9;
}
</style>