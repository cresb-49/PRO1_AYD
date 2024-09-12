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
      meta: { title: 'homepage', layout: DefaultLayout },
      component: HomeView
    },
    {
      path: '/buscar/:busqueda',
      name: 'busqueda',
      meta: { title: 'busqueda', layout: DefaultLayout },
      component: () => import('../views/SearchView.vue')
    },
    {
      path: '/categoria/:id',
      name: 'categoria',
      meta: {title: 'categoria', layout: DefaultLayout},
      component: () => import('../views/CategoryView.vue')
    },
    {
      path: '/perfil',
      name: 'perfil',
      meta: { title: 'perfil', layout: DefaultLayout },
      component: () => import('../views/profile/ProfileView.vue')
    },
    {
      path: '/perfil/edit',
      name: 'perfil-editar',
      meta: { title: 'perfil-editar', layout: DefaultLayout },
      component: () => import('../views/profile/ProfileEditView.vue')
    },
    {
      path: '/login',
      name: 'login',
      meta: { title: 'Login', layout: EmptyLayout },
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/sign-up',
      name: 'sign-up',
      meta: { title: 'Sign Up', layout: EmptyLayout },
      component: () => import('../views/SignUpView.vue')
    },
    {
      path: '/two-factor',
      name: 'two-factor',
      meta: { title: 'Two Factor', layout: EmptyLayout },
      component: () => import('../views/TwoFactorView.vue')
    },
    {
      path: '/password-reset',
      name: 'password-reset',
      meta: { title: 'Password Reset', layout: EmptyLayout },
      component: () => import('../views/PasswordResetView.vue')
    },
    {
      path: '/password_reset/form',
      name: 'password-reset-form',
      meta: { title: 'Password Reset', layout: EmptyLayout },
      component: () => import('../views/ChangePasswordTokenView.vue')
    },
    {
      path: '/ventas',
      name: 'ventas',
      meta: { title: 'Ventas', layout: DefaultLayout },
      component: () => import('../views/profile/SalesView.vue')
    },
    {
      path: '/venta/:id',
      name: 'venta',
      meta: { title: 'Venta', layout: DefaultLayout },
      component: () => import('../views/profile/SaleView.vue')
    },
    {
      path: '/admin',
      name: 'admin',
      meta: { title: 'Admin', layout: DefaultLayout },
      component: () => import('../views/admin/AdminView.vue')
    },
    {
      path: '/admin/user/add',
      name: 'admin-user-add',
      meta: { title: 'Admin User Add', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/SignUpUser.vue')
    },
    {
      path: '/admin/admin/add',
      name: 'admin-admin-add',
      meta: { title: 'Admin User Add', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/SignUpAdmin.vue')
    },
    {
      path: '/admin/ayudante/add',
      name: 'admin-ayudante-add',
      meta: { title: 'Admin User Add', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/SignUpAssistant.vue')
    },
    {
      path: '/admin/categorias',
      name: 'admin-categorias',
      meta: { title: 'Admin Categorias', layout: DefaultLayout },
      component: () => import('../views/admin/categorias/CategoriasView.vue')
    },
    {
      path: '/admin/categorias/edit/:id',
      name: 'admin-categorias-editar',
      meta: { title: 'Admin Categorias-Edit', layout: DefaultLayout },
      component: () => import('../views/admin/categorias/CategoriasEditView.vue')
    },
    {
      path: '/admin/categorias/add',
      name: 'admin-categorias-agregar',
      meta: { title: 'Admin Categorias-Add', layout: DefaultLayout },
      component: () => import('../views/admin/categorias/CategoriasAddView.vue')
    },
    {
      path: '/admin/envios',
      name: 'admin-envios',
      meta: { title: 'Admin Envios', layout: DefaultLayout },
      component: () => import('../views/admin/envios/EnviosView.vue')
    },
    {
      path: '/admin/envio/:id',
      name: 'admin-envio',
      meta: { title: 'Admin Envio', layout: DefaultLayout },
      component: () => import('../views/admin/envios/EnvioView.vue')
    },
    {
      path: '/admin/productos',
      name: 'admin-productos',
      meta: { title: 'Admin Productos', layout: DefaultLayout },
      component: () => import('../views/admin/productos/ProductosView.vue')
    },
    {
      path: '/admin/productos/edit/:id',
      name: 'admin-productos-editar',
      meta: { title: 'Admin Categorias-Edit', layout: DefaultLayout },
      component: () => import('../views/admin/productos/ProductosEditView.vue')
    },
    {
      path: '/admin/productos/add',
      name: 'admin-productos-agregar',
      meta: { title: 'Admin Productos-Add', layout: DefaultLayout },
      component: () => import('../views/admin/productos/ProductosAddView.vue')
    },
    {
      path: '/admin/store',
      name: 'admin-tienda-personalizacion',
      meta: { title: 'Admin Personalizacion Tienda', layout: DefaultLayout },
      component: () => import('../views/admin/personalizacion/PersonalizacionView.vue')
    },
    {
      path: '/admin/store/edit',
      name: 'admin-tienda-personalizacion-edit',
      meta: { title: 'Admin Personalizacion Tienda Editar', layout: DefaultLayout },
      component: () => import('../views/admin/personalizacion/PersonalizacionEditView.vue')
    },
    {
      path: '/admin/inventario',
      name: 'admin-inventario',
      meta: { title: 'Admin Inventario', layout: DefaultLayout },
      component: () => import('../views/admin/inventario/InventarioView.vue')
    },
    {
      path: '/admin/usuarios',
      name: 'admin-usuarios',
      meta: { title: 'Admin Usuarios', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/UsuariosView.vue')
    },
    {
      path: '/admin/usuarios/edit/:id',
      name: 'admin-usuarios-editar',
      meta: { title: 'Admin Usuario-Edit', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/UsuarioEditView.vue')
    },
    {
      path: '/admin/productos/edit/:id',
      name: 'admin-productos-editar',
      meta: { title: 'Admin Categorias-Edit', layout: DefaultLayout },
      component: () => import('../views/admin/productos/ProductosEditView.vue')
    },
    {
      path: '/admin/productos/add',
      name: 'admin-productos-agregar',
      meta: { title: 'Admin Productos-Add', layout: DefaultLayout },
      component: () => import('../views/admin/productos/ProductosAddView.vue')
    },
    {
      path: '/admin/usuario/permisos/:id',
      name: 'admin-usuario-permisos',
      meta: { title: 'Permisos Usuario', layout: DefaultLayout },
      component: () => import('../views/admin/usuarios/PermisosView.vue')
    },
    {
      path: '/admin/reportes',
      name: 'admin-reportes',
      meta: { title: 'Reportes', layout: DefaultLayout },
      component: () => import('../views/admin/reportes/SalesReports.vue')
    },
    {
      path: '/admin/reportes/graficos',
      name: 'admin-reportes-graficos',
      meta: { title: 'Reportes Graficos', layout: DefaultLayout },
      component: () => import('../views/admin/reportes/ProyectionReports.vue')
    },
    {
      path: '/admin/reportes/proyecciones',
      name: 'admin-reportes-proyecciones',
      meta: { title: 'Reportes Proyecciones', layout: DefaultLayout },
      component: () => import('../views/admin/reportes/ProyectionReports.vue')
    },
    {
      path: '/producto/:id',
      name: 'detalle-producto',
      meta: { title: 'Detalle Producto', layout: DefaultLayout },
      component: () => import('../views/ProductDetailView.vue')
    },
    {
      path: '/carrito',
      name: 'carrito',
      meta: { title: 'Carrito', layout: DefaultLayout },
      component: () => import('../views/profile/CartView.vue')
    }
  ]
})

router.beforeEach(async (to, from) => {
  //Se obtiene la autenticacion actual
  const { user, role } = useAuthStore()

  if (to.path === '/login' && user) {
    if (from.path === '/login') {
      return { path: '/' }
    }
    return { path: from.path }
  } else if (to.path === '/sign-up' && user) {
    if (from.path === '/sign-up') {
      return { path: '/' }
    }
    return { path: from.path }
  } else if (to.path === '/pasword-rest' && user) {
    if (from.path === '/password-reset') {
      return { path: '/' }
    }
    return { path: from.path }
  } else if (to.path.includes('/perfil') && !user) {
    if (from.path.includes('/perfil')) {
      return { path: '/' }
    }
    return { path: from.path }
  } else if (to.path.includes('/admin') && (!user || (user && role === 'regular'))) {
    //Si se intenta ir a una ruta de admin y no hay usuario, o hay usuario pero su rol es normal
    if (from.path.includes('/admin')) {
      //Si el path de donde viene es igual al path a donde va se devuelve a home
      return { path: '/' }
    }
    return { path: from.path }
  }
})

export default router
