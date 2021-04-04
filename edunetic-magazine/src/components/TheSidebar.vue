<template>
  <div class="app-sidebar sidebar-shadow">
    <div class="app-header__logo">
      <div class="logo-src"></div>
      <div class="header__pane ml-auto">
        <div>
          <button
            type="button"
            class="hamburger close-sidebar-btn hamburger--elastic"
            data-class="closed-sidebar"
          >
            <span class="hamburger-box">
              <span class="hamburger-inner"></span>
            </span>
          </button>
        </div>
      </div>
    </div>
    <div class="app-header__mobile-menu">
      <div>
        <button
          type="button"
          class="hamburger hamburger--elastic mobile-toggle-nav"
        >
          <span class="hamburger-box">
            <span class="hamburger-inner"></span>
          </span>
        </button>
      </div>
    </div>
    <div class="app-header__menu">
      <span>
        <button
          type="button"
          class="btn-icon btn-icon-only btn btn-primary btn-sm mobile-toggle-header-nav"
        >
          <span class="btn-icon-wrapper">
            <i class="fa fa-ellipsis-v fa-w-6"></i>
          </span>
        </button>
      </span>
    </div>
    <div class="scrollbar-sidebar">
      <div class="app-sidebar__inner">
        <ul class="vertical-nav-menu">
          <li class="app-sidebar__heading">Hello {{loginUser.fullName}}</li>
          <li>
            <router-link to="/">
              <i class="metismenu-icon fas fa-th"></i>
              <p>Dashboard</p>
            </router-link>
          </li>
          <li v-if="loginUser.roleId !== 4"> <!--Only studnet cannot access this route-->
            <router-link to="/users">
              <i class="metismenu-icon fas fa-th"></i>
              <p v-on:click="deleteUserKey()">User List</p>
            </router-link>
          </li>
          <li v-if="loginUser.roleId === 1"> <!--Only admin can access this route--> 
            <router-link to="/roles" > 
              <i class="metismenu-icon fas fa-th"></i>
              <p>Role List</p>
            </router-link>
          </li>
          <li v-if="loginUser.roleId === 2"> <!--Only MM can access this route-->
            <router-link to="/faculties">
              <i class="metismenu-icon fas fa-th"></i>
              <p>Faculty List</p>
            </router-link>
          </li>
          <li>
            <router-link to="/contributions" v-if="loginUser.roleId !== 1"> <!--Only admin cannot access this route-->
              <i class="metismenu-icon fas fa-th"></i>
              <p>Contribution List</p>
            </router-link>
          </li>
          <li>
           <router-link to="/magazines" v-if="loginUser.roleId !== 1"> <!--Only marketing manager can access this route-->
              <i class="metismenu-icon fas fa-th"></i>
              <p>Magazine List</p>
            </router-link>
          </li>
          <li>
            <a href="#" v-on:click.prevent="logOut">
              <i class="metismenu-icon fas fa-power-off"></i>
              <p>Logout</p>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
export default {
  name: "TheSidebar",
  mixins: [commonHelper],
  created() {
    this.getLoginUser();
  },
  data() {
    return {
      loginUser: {}
    }
  },
  methods: {
    deleteUserKey() {
      if (this.$cookies.isKey("facultyStudent")) {
        this.$cookies.remove("facultyStudent");
      }
    },
    getLoginUser() {
      axios
        .get(UrlConstants.User + "/" + this.$cookies.get("id"))
        .then((res) => {
          this.loginUser = res.data.data;
          this.$cookies.set("loginUser", res.data.data, "30min")
        });
    },
  },
};
</script>

<style scoped>
a.router-link-exact-active {
  background-color: #e0f3ff;
  font-weight: bold;
  color: #000000;
}
</style>
