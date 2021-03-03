import Vue from 'vue'
import Router from "vue-router";

import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";

import Login from "@/pages/Login";

import RoleList from "@/pages/role/RoleList";
import RoleCreate from "@/pages/role/RoleCreate";
import RoleUpdate from "@/pages/role/RoleUpdate";

Vue.use(Router);
export const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/',
            component: UserList,
        },
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
        },{
            name:'Login',
            path:'/login',
            component: Login,
            
        },{
            name:'Roles',
            path:'/roles',
            component: RoleList,
        },{
            name:'Roles',
            path: '/roles/create',
            component: RoleCreate,
        },{
            name:'Roles',
            path: '/roles/update',
            component: RoleUpdate,
        }

    ]
})
export default router