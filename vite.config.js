import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',   // 后端接口地址
        changeOrigin: true
      }
    }
  },
  test: {
    // 测试环境
    environment: 'happy-dom',
    // 测试文件匹配规则
    include: ['__tests__/**/*.test.js'],
    // 全局变量（可选）
    globals: true
  }
})