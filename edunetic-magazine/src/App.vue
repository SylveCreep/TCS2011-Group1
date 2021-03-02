<template>
  <div class="wrapper">
    <!-- Navbar -->
    <the-navbar v-if="this.token !== null"></the-navbar>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <the-sidebar v-if="this.token !== null" v-on:user-logout="userLogout"></the-sidebar>
    <!-- /.Main Sidebar Container -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <router-view v-on:user-logged="userLogin"></router-view>
    </div>
    <!-- /.content-wrapper -->

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
      <!-- Control sidebar content goes here -->
      <div class="p-3">
        <h5>Title</h5>
        <p>Sidebar content</p>
      </div>
    </aside>
    <!-- /.control-sidebar -->

    <!-- Main Footer -->
    <the-footer v-if="this.token !== null"></the-footer>
    <!-- /.Main Footer -->
  </div>
</template>

<script>
import axios from "axios";
import TheSidebar from "./components/TheSidebar";
import TheNavbar from "./components/TheNavbar";
import TheFooter from "./components/TheFooter";

export default {
  name: "Layout",
  components: {
    TheSidebar,
    TheNavbar,
    TheFooter,
  },
  data() {
    return {
      token: this.$cookies.get('jwt')
    }
  },
  created () {
   this.setHeader();
  },
  methods: {
    setHeader() {
      if (this.$cookies.isKey('jwt')) {
        axios.defaults.headers.common['Authorization'] = "Bearer " + this.token;
      } else {
        axios.defaults.headers.common['Authorization'] = null;
      }
    },
    userLogin(e) {
      this.token = this.$cookies.get('jwt');
      axios.defaults.headers.common['Authorization'] = "Bearer " + this.$cookies.get('jwt');
    },
    userLogout(e) {
      this.token = e;
      axios.defaults.headers.common["Authorization"] = null;
    }
  }
}
</script>

<style scoped>
</style>
