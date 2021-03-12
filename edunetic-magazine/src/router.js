import Vue from 'vue'
import Router from "vue-router";

import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";

import Login from "@/pages/Login";

import RoleList from "@/pages/role/RoleList";
import RoleCreate from "@/pages/role/RoleCreate";
import RoleUpdate from "@/pages/role/RoleUpdate";

import FacultyList from "@/pages/faculty/FacultyList";
import FacultyCreate from "@/pages/faculty/FacultyCreate";
import FacultyUpdate from "@/pages/faculty/FacultyUpdate";

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
            name:'Roles',
            path: '/',
            component: RoleList,
        },
        {
            name:'Faculties',
            path: '/',
            component: FacultyList,
        },

        {
            name:'UsersList',
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
            name:'RolesList',
            path:'/roles',
            component: RoleList,
        },{
            name:'RolesCreate',
            path: '/roles/create',
            component: RoleCreate,
        },{
            name:'RolesUpdate',
            path: '/roles/:id/update',
            component: RoleUpdate,
        },{
            name:'FacultyList',
            path:'/faculties',
            component: FacultyList,
        },{
            name:'FacultyCreate',
            path: '/faculties/create',
            component: FacultyCreate,
        },{
            name:'FacultyUpdate',
            path: '/faculties/:id/update',
            component: FacultyUpdate,
        },
    ]
})
export default router