import Vue from 'vue'
import Router from "vue-router";
import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";
Vue.use(Router);
const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/users',
            component: UserList,
        }, {
            name:'UserCreate',
            path: '/users/create',
            component: UserCreate,
        },{
            name: 'UserUpdate',
            path: '/users/:id/update',
            component: UserUpdate
        }
    ]
})
export default router