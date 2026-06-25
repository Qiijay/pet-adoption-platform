<template>
  <div class="home">
    <div class="hero">
      <h1>🐾 给毛孩子一个温暖的家</h1>
      <p>领养代替购买，让爱不再流浪</p>
      <router-link to="/pets" class="btn-hero">查看待领养宠物</router-link>
    </div>
    <div class="features">
      <div class="feature-item">
        <h3>🐶 真实信息</h3>
        <p>所有宠物信息真实可靠</p>
      </div>
      <div class="feature-item">
        <h3>🏠 简单流程</h3>
        <p>在线申请，快速审核</p>
      </div>
      <div class="feature-item">
        <h3>❤️ 爱心传递</h3>
        <p>给流浪动物一个家</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
}
.hero {
  text-align: center;
  padding: 80px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border-radius: 20px;
  margin: 20px;
}
.hero h1 {
  font-size: 48px;
  margin-bottom: 20px;
}
.hero p {
  font-size: 20px;
  margin-bottom: 30px;
}
.btn-hero {
  display: inline-block;
  padding: 15px 40px;
  background: #fff;
  color: #764ba2;
  border-radius: 50px;
  text-decoration: none;
  font-weight: bold;
  font-size: 18px;
}
.btn-hero:hover {
  background: #f0f0f0;
}
.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 30px;
  padding: 40px 20px;
}
.feature-item {
  text-align: center;
  padding: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}
.feature-item h3 {
  font-size: 24px;
  color: #2c3e50;
  margin-bottom: 10px;
}
.feature-item p {
  color: #666;
}
</style>