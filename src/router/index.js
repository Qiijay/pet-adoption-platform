import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue'),
    meta: { guest: true }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/auth/Register.vue'),
    meta: { guest: true }
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/home/Home.vue')
  },
  {
    path: '/pets',
    name: 'PetList',
    component: () => import('@/views/pet/PetList.vue')
  },
  {
    path: '/pet/:id',
    name: 'PetDetail',
    component: () => import('@/views/pet/PetDetail.vue'),
    props: true
  },
  {
    path: '/user',
    name: 'UserCenter',
    component: () => import('@/views/user/UserCenter.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/user/adoptions',
    name: 'MyAdoptions',
    component: () => import('@/views/user/MyAdoptions.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/admin',
    component: () => import('@/components/AdminLayout.vue'),
    meta: { requiresAuth: true, role: 'shelter' },
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue')
      },
      {
        path: 'pets',
        name: 'PetManage',
        component: () => import('@/views/admin/PetManage.vue')
      },
      {
        path: 'audit',
        name: 'AuditAdopt',
        component: () => import('@/views/admin/AuditAdopt.vue')
      },
      {
        path: 'stats',
        name: 'DataStats',
        component: () => import('@/views/admin/DataStats.vue')
      },
      {
        path: 'report',
        name: 'ReportExport',
        component: () => import('@/views/report/ReportExport.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：权限验证
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth && !userStore.token) {
    next('/login')
  } else if (to.meta.role && userStore.role !== to.meta.role) {
    next('/home')
  } else {
    next()
  }
})

export default router