import Vue from 'vue'
import Vuex from 'vuex'
import router, { setRoutes } from '@/router';
import { delToken } from '@/utils/auth';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isAddRoutes: false
  },
  getters: {
    GET_MENUS(state) {
      return JSON.parse(sessionStorage.getItem("menus"));
    }
  },
  mutations: {
    SET_MENUS(state, menus) {
      sessionStorage.setItem("menus", JSON.stringify(menus));
      setRoutes();
    },
    logout() {
      sessionStorage.clear();
      delToken();
      router.push("/login");
    }
  },
  actions: {
  },
  modules: {
  }
})
