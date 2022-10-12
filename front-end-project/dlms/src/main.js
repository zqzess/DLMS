import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'//导入自定义路由配置
import '@/assets/css/global.css'
// import axios from 'axios' ;
import store from './store'
// import Vuex from 'vuex'
import {Message} from 'element-ui'
import instance from '@/axios/http'

Vue.prototype.$axios = instance

// axios.defaults.baseURL = 'http://localhost:8082/api'
// axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
// axios.defaults.withCredentials = true //请求发送cookie

Vue.prototype.$message=Message
// Vue.prototype.$axios = axios
Vue.config.productionTip = false

// Vue.use(Vuex) ;
Vue.use(ElementUI);
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')

// 添加请求拦截器，在请求头中加token
// axios.interceptors.request.use(
//     config => {
//         console.log(localStorage.getItem('token'))
//         if (localStorage.getItem('token')) {
//             config.headers.token = localStorage.getItem('token');
//         }
//         return config;
//     },
//     error => {
//         return Promise.reject(error);
//     });
