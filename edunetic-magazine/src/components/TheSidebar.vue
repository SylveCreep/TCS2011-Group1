<template>
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="index2.html" class="brand-link">
      <img
        src="dist/img/AdminLTELogo.png"
        alt="AdminLTE Logo"
        class="brand-image img-circle elevation-3"
        style="opacity: 0.8"
      />
      <span class="brand-text font-weight-light">Edunetic Magazine</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="info">
          <router-link to="/profile" class="d-block">Hello {{this.user.fullName}}</router-link>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul
          class="nav nav-pills nav-sidebar flex-column"
          data-widget="treeview"
          role="menu"
          data-accordion="false"
        >
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item">
            <router-link to="/users" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>User List</p>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/roles" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>Role List</p>
            </router-link>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>Contribution List</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>Magazine List</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>Faculty List</p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link" v-on:click.prevent="logOut">
              <i class="nav-icon fas fa-power-off"></i>
              <p>Logout</p>
            </a>
          </li>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
</template>

<script>
import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";

export default {
  watch: {},
  name: "TheSidebar",
  data() {
      return {
          user: {}
      }
  },
  created() {
      this.getCurrentUser();
  },
  methods: {
    getCurrentUser() {
      axios.get(UrlConstants.User + "/" + this.$cookies.get("id")).then((res) => {
        this.user = res.data.data
      });
    },
    logOut() {
      let result = confirm("Do you want to log out?");
      if (result) {
         this.$cookies.remove("jwt");
          this.$emit("user-logout", null);
          this.$router.push("/login");
      }
    },
  },
};
</script>

<style scoped>
a.router-link-exact-active {
  background-color: green;
}
</style>
