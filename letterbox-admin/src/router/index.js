import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from "@/views/MainView"
import AboutView from "@/views/AboutView"
import LoginView from "@/views/LoginView"
import RegisterView from "@/views/RegisterView"
import { getToken } from '@/utils/auth'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/about',
    name: '关于',
    component: AboutView
  },
  {
    path: '/login',
    name: '登录',
    component: LoginView
  },
  {
    path: '/register',
    name: '注册',
    component: RegisterView
  }
]

const router = new VueRouter({
  mode: 'hash',
  // base: '/',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach( async (to, from, next) => {
  const whiteList = ['/login', '/register'];
  let token = getToken();
  if (token) {
    if (to.path === '/login') {
      next({path: '/'})
    }
    if (!store.state.isAddRoutes) {
      setRoutes();
      next({...to, replace: true});
    } else {
      next();
    }
  } else {
      if (whiteList.includes(to.path)) {
        next();
      } else {
        next('/login');
      }
  }
})

export const setRoutes = () => {
  const menus = store.getters.GET_MENUS;
  if (menus) {
    const indexMenu = [{path: '/', name: '首页', redirect: '/home', component: MainView, children: []}
  ];
    menus.forEach(pmenu => {
      pmenu.children.forEach(menu => {
        let route = {
          path: menu.path,
          name: menu.menuName,
          component: () => import("../views/"+menu.component+".vue")
        }
        indexMenu[0].children.push(route);
      })
    })
    router.addRoutes(indexMenu);
    store.state.isAddRoutes = true;
  }
}

/* 路由异常错误处理，尝试解析一个异步组件时发生错误，重新渲染目标页面 */
router.onError((error) => {
  const pattern = /Loading chunk (\d)+ failed/g;
  const isChunkLoadFailed = error.message.match(pattern);
  const targetPath = router.history.pending.fullPath;
  console.log(targetPath)
  if (isChunkLoadFailed) {
    router.replace(targetPath);
  }
});

export default router
