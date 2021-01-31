import Vue from 'vue'
import Router from "vue-router";

//import CommentList from "@/pages/comment/CommentList";
import TheFooter from "@/components/TheFooter";
import UserList from "@/pages/user/UserList";
import Login from "@/pages/login/Login";
Vue.use(Router);
const router = new Router({
    mode: 'history',
    routes: [
        {
            name:'Users',
            path: '/users',
            component: UserList,
        }, {
            name:'TheFooter',
            path: '/footers',
            component: TheFooter,
        },{
            name:'Login',
            path:'/login',
            component: Login,
            
        },
    ]
})
export default router