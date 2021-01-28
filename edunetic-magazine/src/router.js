import Vue from 'vue'
import Router from "vue-router";
import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate.vue";
Vue.use(Router);
const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/users',
            component: UserList,
        }, {
            name:'Users',
            path: '/users/create',
            component: UserCreate,
        }
    ]
})
export default router