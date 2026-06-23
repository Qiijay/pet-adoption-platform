<template>
  <el-card shadow="hover" class="pet-card" @click="$emit('click')">
    <el-image :src="pet.image" fit="cover" style="width:100%; height:180px" lazy />
    <div class="pet-info">
      <h3>{{ pet.name }}</h3>
      <el-tag size="small" type="info">{{ pet.type === 'dog' ? '狗' : pet.type === 'cat' ? '猫' : '其他' }}</el-tag>
      <p>{{ pet.age }}个月 | {{ pet.shelterName }}</p>
    </div>
  </el-card>
</template>

<script setup>
defineProps({
  pet: Object
})
</script>

<style scoped>
.pet-card {
  cursor: pointer;
  transition: transform 0.2s;
}
.pet-card:hover {
  transform: translateY(-2px);
}
.pet-info {
  padding: 10px 0;
  text-align: center;
}
</style>