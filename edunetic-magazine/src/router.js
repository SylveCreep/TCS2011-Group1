import Vue from 'vue'
import Router from "vue-router";

//import CommentList from "@/pages/comment/CommentList";
import TheFooter from "@/components/TheFooter";
import Login from "@/pages/login/Login";
Vue.use(Router);
export const router = new Router({
    mode: 'history',
    routes: [
        {
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