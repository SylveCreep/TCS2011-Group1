import Vue from 'vue'
import App from './App.vue'
import axios from "axios";
import router from "@/router";
import VueCookies from 'vue-cookies'
import moment from 'moment';
// import VueSweetalert2 from 'vue-sweetalert2';

import 'bootstrap/dist/css/bootstrap.min.css'
import '@/assets/css/main.css'
Vue.use(VueCookies);
Vue.filter('formatDate', function(value) {
  if (value) {
      return moment(String(value)).format('MM/DD/YYYY')
  }
});
new Vue({
  axios,router,
  render: h => h(App),
}).$mount('#app')
