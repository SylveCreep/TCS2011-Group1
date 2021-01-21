import Vue from 'vue'
import Router from "vue-router";
import CommentList from "@/pages/comment/CommentList";
import TheFooter from "@/components/TheFooter";
Vue.use(Router);
const router = new Router({
    base: 'admin',
    mode: 'history',
    routes: [
        {
            name:'CommentList',
            path: '/comments',
            component: CommentList,
        }, {
            name:'TheFooter',
            path: '/footers',
            component: TheFooter,
        }
    ]
})
export default router