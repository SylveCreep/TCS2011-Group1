<template>
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="index2.html" class="brand-link">
      <img src="dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
           style="opacity: .8">
      <span class="brand-text font-weight-light">Edunetic Magazine</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">Admin</a>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item">
            <router-link to="/users" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                  User List
              </p>
            </router-link>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                  Role List
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                  Contribution List
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                  Magazine List
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-th"></i>
              <p>
                Faculty List
              </p>
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
// import axios from 'axios';
// import {UrlConstants} from "../constants/UrlConstant";

export default {
    name: "TheSidebar",
    // data() {
    //     return {
    //         user: {}
    //     }
    // },
    // created() {
    //     this.getCurrentUser();
    // },
    // methods: {
    //     getCurrentUser(){
    //         axios.get(UrlConstants.user + '/' + this.$cookies.get('id'))
    //         .then(res =>{
    //             this.user = res.data
    //         })
    //     },
    //     logOut(){
    //         let result = confirm('do you want to log out?')
    //         if(result){
    //             axios.delete('/api/admin/logout').then(r => {
    //                 this.$cookies.remove('token');
    //                 this.$emit('user-logout', null);
    //                 this.$router.push('/login');
    //             })
    //         }
    //     }
    // }
}
</script>

<style scoped>
a.router-link-exact-active{
    background-color: green;
}

</style>
