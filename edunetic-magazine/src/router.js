import Vue from 'vue'
import Router from "vue-router";
import UserList from "@/pages/user/UserList";
<<<<<<< HEAD
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";
import Login from "@/pages/login/Login";
=======
import RoleList from "@/pages/role/RoleList";
import UserCreate from "@/pages/user/UserCreate.vue";
import RoleCreate from "@/pages/role/RoleCreate.vue";
import RoleUpdate from "@/pages/role/RoleUpdate.vue";
>>>>>>> show-role-list
Vue.use(Router);
const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/users',
            component: UserList,
<<<<<<< HEAD
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
            
        },
=======
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
>>>>>>> show-role-list
    ]
})
export default router