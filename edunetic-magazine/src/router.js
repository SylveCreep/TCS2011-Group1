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
import StudentList from "@/pages/faculty/StudentList";

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
            name:'Roles',
            path:'/roles',
            component: RoleList,
        },{
            name:'RolesCreate',
            path: '/roles/create',
            component: RoleCreate,
        },{
            name:'RolesUpdate',
            path: '/roles/update',
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
            path: '/faculties/update',
            component: FacultyUpdate,
        },{
            name:'StudentList',
            path: '/faculties/studentlist',
            component: StudentList,
        }

    ]
})
export default router