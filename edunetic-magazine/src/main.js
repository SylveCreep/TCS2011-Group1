import Vue from 'vue'
import App from './App.vue'
import axios from "axios";
import router from "@/router";
import store from './store'

import 'bootstrap/dist/css/bootstrap.min.css'
import '@/assets/css/main.css'

Vue.config.productionTip = false

new Vue({
  axios,router,store,
  render: h => h(App),
}).$mount('#app')
