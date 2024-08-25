import { createRouter, createWebHistory, useRoute } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EmptyLayout from '@/layouts/EmptyLayout.vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      meta: {title: 'homepage', layout: DefaultLayout},
      component: HomeView
    },
    {
      path: '/perfil',
      name: 'perfil',
      meta: {title: 'perfil', layout: DefaultLayout},
      component: () => import('../views/profile/ProfileView.vue')
    },
    {
      path: '/perfil/edit',
      name: 'perfil-editar',
      meta: {title: 'perfil-editar', layout: DefaultLayout},
      component: () => import('../views/profile/ProfileEditView.vue')
    },
    {
      path: '/login',
      name: 'login',
      meta: {title: 'Login', layout: EmptyLayout},
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/sign-up',
      name: 'sign-up',
      meta: {title: 'Sign Up', layout: EmptyLayout},
      component: () => import('../views/SignUpView.vue')
    }
  ]
})

router.beforeEach(async (to, from) => {
  //Se obtiene la autenticacion actual
  const {user, role} = useAuthStore();

  if (to.path === '/login' && user) {
    if (from.path === '/login') {
      return {path: '/'};
    }
    return {path: from.path};
  } else if (to.path === '/sign-up' && user) {
    if (from.path === '/sign-up') {
      return {path: '/'};
    }
    return {path: from.path};
  }
})

export default router
