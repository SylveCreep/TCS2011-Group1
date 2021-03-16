import Vue from 'vue'
import Router from "vue-router";

import Login from "@/pages/Login";

//import User Components
import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";

//import Role Components
import RoleList from "@/pages/role/RoleList";
import RoleCreate from "@/pages/role/RoleCreate";
import RoleUpdate from "@/pages/role/RoleUpdate";

//import Faculty Components
import FacultyList from "@/pages/faculty/FacultyList";
import FacultyCreate from "@/pages/faculty/FacultyCreate";
import FacultyUpdate from "@/pages/faculty/FacultyUpdate";

//import Contribution Components
import ContributionList from "@/pages/contribution/ContributionList";
import ContributionSubmit from "@/pages/contribution/ContributionSubmit";
import ContributionDetail from "@/pages/contribution/ContributionDetail";
import UserProfile from "@/pages/UserProfile";

Vue.use(Router);
export const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'UserProfile',
            path: '/profile',
            component: UserProfile,
        },
        {
            name:'Users',
            path: '/',
            component: UserList,
        },
        //Login Route
        {
            name:'Login',
            path:'/login',
            component: Login,
        },
        //User Routes
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
        },
        //Role Routes
        {
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
        },
        //Faculty Routes
        {
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
        //Contribution Routes
        {
            name: 'ContributionList',
            path: '/contributions',
            component: ContributionList,
        },
        {
            name: 'ContributionSubmit',
            path: '/contributions/submit',
            component: ContributionSubmit,
        },
        {
            name: 'ContributionDetail',
            path: '/contributions/:id/detail',
            component: ContributionDetail,
        },
    ]
})
export default router