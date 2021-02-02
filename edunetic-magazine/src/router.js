import Vue from 'vue'
import Router from "vue-router";
import UserList from "@/pages/user/UserList";
import RoleList from "@/pages/role/RoleList";
import UserCreate from "@/pages/user/UserCreate.vue";
import RoleCreate from "@/pages/role/RoleCreate.vue";
import RoleUpdate from "@/pages/role/RoleUpdate.vue";
Vue.use(Router);
const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/users',
            component: UserList,
        }, 
        {
            name:'Users',
            path: '/users/create',
            component: UserCreate,
        },
        {
            name:'Roles',
            path:'/roles',
            component: RoleList,
        },
        {
            name:'Roles',
            path: '/roles/create',
            component: RoleCreate,
        },
        {
            name:'Roles',
            path: '/roles/update',
            component: RoleUpdate,
        }
    ]
})
export default router