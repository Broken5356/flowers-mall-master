import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Vuex from 'vuex';
import axios from 'axios'
import global from './assets/css/global.css'
import VueWechatTitle from 'vue-wechat-title';


Vue.use(Vuex);
Vue.use(VueWechatTitle);

// axios 请求拦截器
axios.interceptors.request.use(config => {
  //为请求头对象，添加token 验证的 Authorization 字段
  config.headers.Authorization = window.sessionStorage.getItem('token');
  return config;
});

//全局后置守卫:配置页面标题
router.afterEach(function (to, from) {
  if(to.meta.title){
    document.title = to.meta.title;
  }
});

Vue.prototype.$http = axios;

const store = new Vuex.Store({
  state: {
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
  },

  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin(state, user) {
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    }
  }
});

export default store;
Vue.use(ElementUI);
Vue.config.productionTip = false;
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App),
  components: {App},
  template: '<App/>'
});

axios.defaults.baseURL = 'http://localhost:8090';
