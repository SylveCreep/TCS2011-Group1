import Vue from 'vue'
import Router from "vue-router";
import VueCookies from 'vue-cookies'
import Demo from '@/pages/demo'
import GChart from '@/pages/Gchart'

//import Authen Components
import Login from "@/pages/Authen/Login";
import SendMail from "@/pages/Authen/SendMail";
import ResetPassword from "@/pages/Authen/ResetPassword";

//import NotFound Components
import NotFound from "@/pages/NotFound";

//import Change Password Components
import ChangePassword from "@/pages/user/UserChangePassword/ChangePassword";

//import Dash Board Components
import DashBoard from "@/pages/DashBoard";

//import User Components
import UserList from "@/pages/user/UserList";
import UserCreate from "@/pages/user/UserCreate";
import UserUpdate from "@/pages/user/UserUpdate";
import UserProfile from "@/pages/UserProfile";

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

//import Magazine Components
import MagazineList from "@/pages/magazine/MagazineList";
import MagazineCreate from "@/pages/magazine/MagazineCreate";
import MagazineUpdate from "@/pages/magazine/MagazineUpdate";


Vue.use(Router);
export const router = new Router({
    mode: 'history',
    routes: [
        {
            name: 'UserProfile',
            path: '/profile',
            component: UserProfile,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'ChangePassword',
            path: '/users/changepassword',
            component: ChangePassword,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'HomePage',
            path: '/',
            component: DashBoard,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'Dashboard',
            path: '/dashboard',
            component: DashBoard,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'GChart',
            path: '/dashboard/demo/gchart',
            component: GChart,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'Demo',
            path: '/dashboard/demo',
            component: Demo,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        //Authen Routes
        {
            name: 'Login',
            path: '/login',
            component: Login,
            meta: {
                authen: false,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'SendMail',
            path: '/forgot-password',
            component: SendMail,
            meta: {
                authen: false,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'ResetPassword',
            path: '/users/forgotpassword/:id',
            component: ResetPassword,
            meta: {
                authen: false,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        },
        //User Routes
        {
            name: 'UserList',
            path: '/users',
            component: UserList,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: true,
                mm: true,
            }
        }, {
            name: 'UserCreate',
            path: '/users/create',
            component: UserCreate,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: false,
                mm: false,
            }
        }, {
            name: 'UserUpdate',
            path: '/users/:id/detail',
            component: UserUpdate,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: true,
                mm: true,
            }
        },
        //Role Routes
        {
            name: 'RoleList',
            path: '/roles',
            component: RoleList,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: false,
                mm: false,
            }
        }, {
            name: 'RoleCreate',
            path: '/roles/create',
            component: RoleCreate,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: false,
                mm: false,
            }
        }, {
            name: 'RoleUpdate',
            path: '/roles/:id/update',
            component: RoleUpdate,
            meta: {
                authen: true,
                admin: true,
                student: false,
                mc: false,
                mm: false,
            }
        },
        //Faculty Routes
        {
            name: 'FacultyList',
            path: '/faculties',
            component: FacultyList,
            meta: {
                authen: true,
                admin: false,
                student: false,
                mc: false,
                mm: true,
            }
        }, {
            name: 'FacultyCreate',
            path: '/faculties/create',
            component: FacultyCreate,
            meta: {
                authen: true,
                admin: false,
                student: false,
                mc: false,
                mm: true,
            }
        }, {
            name: 'FacultyUpdate',
            path: '/faculties/:id/update',
            component: FacultyUpdate,
            meta: {
                authen: true,
                admin: false,
                student: false,
                mc: false,
                mm: true,
            }
        },
        //Contribution Routes
        {
            name: 'ContributionList',
            path: '/contributions',
            component: ContributionList,
            meta: {
                authen: true,
                admin: false,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'ContributionSubmit',
            path: '/contributions/submit',
            component: ContributionSubmit,
            meta: {
                authen: true,
                admin: false,
                student: true,
                mc: false,
                mm: false,
            }
        },
        {
            name: 'ContributionDetail',
            path: '/contributions/:id/detail',
            component: ContributionDetail,
            meta: {
                authen: true,
                admin: false,
                student: true,
                mc: true,
                mm: true,
            }
        },
        //Magazine Routes
        {
            name: 'MagazineList',
            path: '/magazines',
            component: MagazineList,
            meta: {
                authen: true,
                admin: false,
                student: true,
                mc: true,
                mm: true,
            }
        },
        {
            name: 'MagazineCreate',
            path: '/magazines/create',
            component: MagazineCreate,
            meta: {
                authen: true,
                admin: false,
                student: false,
                mc: false,
                mm: true,
            }
        },
        {
            name: 'MagazineUpdate',
            path: '/magazines/:id/update',
            component: MagazineUpdate,
            meta: {
                authen: true,
                admin: false,
                student: false,
                mc: false,
                mm: true,
            }
        }, {
            name: 'NotFound',
            path: '*',
            component: NotFound,
            meta: {
                authen: true,
                admin: true,
                student: true,
                mc: true,
                mm: true,
            }
        }
    ]
})

//AUTHENTICATION
//if users haven't logged in yet, cannot access any route except login
router.beforeEach((to, from, next) => {
    if (!VueCookies.isKey("jwt")) {
        if (to.meta.authen) {
            next("/login")
        } else {
            next()
        }

    } else {
        next()
    }
})
//if users have already logged in, cannot access login route
router.beforeEach((to, from, next) => {
    if (to.name === 'Login' && VueCookies.isKey("jwt")) {
        next("/")
    } else {
        next()
    }
})
//ADMIN AUTHORIZATION
router.beforeEach((to, from, next) => {
    //get login role= 
    if (!to.meta.admin && VueCookies.get("loginUser").roleId === 1) {
        next("/not-found")
    } else {
        next()
    }
})
//MARKETING MANAGER AUTHORIZATION
router.beforeEach((to, from, next) => {
    //get login role= 
    if (!to.meta.mm && VueCookies.get("loginUser").roleId === 2) {
        next("/not-found")
    } else {
        next()
    }
})
//MARKETING COORDINATOR AUTHORIZATION
router.beforeEach((to, from, next) => {
    //get login role= 
    if (!to.meta.mc && VueCookies.get("loginUser").roleId === 3) {
        next("/not-found")
    } else {
        next()
    }
})
//STUDENT AUTHORIZATION
router.beforeEach((to, from, next) => {
    //get login role
    if (!to.meta.student && VueCookies.get("loginUser").roleId === 4) {
        next("/not-found")
    } else {
        next()
    }
})
export default router;