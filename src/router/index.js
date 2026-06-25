import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/home/Home.vue'
import Login from '../views/auth/Login.vue'
import Register from '../views/auth/Register.vue'
import PetList from '../views/pet/PetList.vue'
import PetDetail from '../views/pet/PetDetail.vue'
import MyAdoptions from '../views/user/MyAdoptions.vue'
import UserCenter from '../views/user/UserCenter.vue'
import PetManage from '../views/admin/PetManage.vue'
import AuditAdopt from '../views/admin/AuditAdopt.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/pets', name: 'PetList', component: PetList },
  { path: '/pets/:id', name: 'PetDetail', component: PetDetail },
  { path: '/my-adoptions', name: 'MyAdoptions', component: MyAdoptions },
  { path: '/user-center', name: 'UserCenter', component: UserCenter },
  { path: '/admin/pets', name: 'PetManage', component: PetManage },
  { path: '/admin/audit', name: 'AuditAdopt', component: AuditAdopt },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const publicPages = ['/', '/login', '/register', '/pets']
  const isPublic = publicPages.includes(to.path) || to.path.startsWith('/pets/')
  
  if (!isPublic && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router