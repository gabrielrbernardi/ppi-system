import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

import "primevue/resources/themes/saga-blue/theme.css"       //theme
import "primevue/resources/primevue.min.css"                //core css
import "primeicons/primeicons.css"                           //icons
import "/node_modules/primeflex/primeflex.css"

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  
  {
    path: '/promocoes',
    name: 'Promocoes',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "promocoes" */ '../views/Promocoes.vue')
  },

  {
    path: '/promocoes/criar',
    name: 'CriaPromocoes',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "promocoes" */ '../views/CriaPromocoes.vue')
  },

  {
    path: '/promocoesadmin',
    name: 'PromocoesAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "promocoesadmin" */ '../views/PromocoesAdmin.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
