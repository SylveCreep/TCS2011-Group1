<template>
  <div
    class="app-container app-theme-white body-tabs-shadow fixed-sidebar fixed-header"
  >
    <the-navbar
      v-if="this.token !== null"
      v-on:user-logout="userLogout"
    ></the-navbar>
    <chat-box v-if="this.token !== null"></chat-box>
    <div class="app-main">
      <!--Sidebar section -->
      <the-sidebar
        v-if="this.token !== null"
        v-on:user-logout="userLogout"
      ></the-sidebar>
      <!--End sidebar section -->

      <div class="app-main__outer">
        <!--Main section -->
        <router-view
          v-on:user-login="userLogin"
          v-on:user-logout="userLogout"
        ></router-view>
        <!--End main section -->

        <!--Footer section -->
        <div class="app-wrapper-footer">
          <the-footer v-if="this.token !== null"></the-footer>
        </div>
        <!--End footer section -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TheSidebar from "./components/TheSidebar";
import TheNavbar from "./components/TheNavbar";
import TheFooter from "./components/TheFooter";
import ChatBox from "./components/ChatBox.vue";
export default {
  name: "Layout",
  components: {
    TheSidebar,
    TheNavbar,
    TheFooter,
    ChatBox,
  },
  data() {
    return {
      token: this.$cookies.get("jwt"),
    };
  },
  created() {
    this.setHeader();
  },
  methods: {
    setHeader() {
      if (this.$cookies.isKey("jwt")) {
        axios.defaults.headers.common["Authorization"] = "Bearer " + this.token;
      } else {
        axios.defaults.headers.common["Authorization"] = null;
      }
    },
    userLogin(e) {
      this.token = this.$cookies.get("jwt");
      axios.defaults.headers.common["Authorization"] =
        "Bearer " + this.$cookies.get("jwt");
      axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
    },
    userLogout(e) {
      this.token = e;
      axios.defaults.headers.common["Authorization"] = null;
    },
  },
};
</script>

<style scoped>
</style>
