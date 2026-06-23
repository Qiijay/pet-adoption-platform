<template>
  <div class="loading-overlay" v-if="visible">
    <el-icon class="is-loading"><Loading /></el-icon>
    <p v-if="text">{{ text }}</p>
  </div>
</template>

<script setup>
defineProps({
  visible: Boolean,
  text: String
})
</script>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(255,255,255,0.8);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
</style>