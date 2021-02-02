import Vue from 'vue'
import App from './App.vue'
import axios from "axios";
import { router } from './router';
import store from './store';

import Vuex from 'vuex';

import 'bootstrap/dist/css/bootstrap.min.css'
import '@/assets/css/main.css'

Vue.config.productionTip = false

Vue.use(Vuex);

new Vue({
  axios,router,store,
  render: h => h(App),
}).$mount('#app')
