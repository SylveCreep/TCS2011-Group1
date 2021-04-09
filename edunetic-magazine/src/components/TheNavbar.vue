<template>
  <div class="app-header header-shadow">
    <div class="app-header__logo">
      <h6>Edunetic Magazine</h6>
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
    <div class="app-header__content">
      <div class="app-header-right">
        <div class="header-btn-lg pr-0">
          <div class="widget-content p-0">
            <div class="widget-content-wrapper">
              <div class="widget-content-left">
                <div class="btn-group">
                  <a
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                    class="p-0 btn"
                  >
                    <img id="avatar-nav"
                      v-if="avatarUrl"
                      :src="avatarUrl"
                      width="42"
                      height="40"
                    />
                    <i class="fa fa-angle-down ml-2 opacity-8"></i>
                  </a>
                  <div
                    tabindex="-1"
                    role="menu"
                    aria-hidden="true"
                    class="dropdown-menu dropdown-menu-right"
                  >
                    <router-link
                      to="/profile"
                      type="button"
                      tabindex="0"
                      class="dropdown-item"
                    >
                      User Account
                    </router-link>
                    <div tabindex="-1" class="dropdown-divider"></div>
                    <router-link
                      to="/ChangePassword"
                      type="button"
                      tabindex="0"
                      class="dropdown-item"
                    >
                      Change Password
                    </router-link>
                    <div tabindex="-1" class="dropdown-divider"></div>
                    <button
                      type="button"
                      tabindex="0"
                      class="dropdown-item"
                      v-on:click="logOut"
                    >
                      Logout
                    </button>
                  </div>
                </div>
              </div>
              <div class="widget-content-left ml-3 header-user-info">
                <div class="widget-heading">{{ loginUser.fullName }}</div>
                <div class="widget-subheading">{{ loginUser.roleName }}</div>
              </div>
              <div class="widget-content-right header-user-info ml-3">
                <button
                  type="button"
                  class="btn-shadow p-1 btn btn-primary btn-sm show-toastr-example"
                >
                  <i class="fa text-white fa-calendar pr-1 pl-1"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
export default {
  
  name: "TheNavbar",
  mixins: [commonHelper],
  data() {
    return {
      loginUser: {},
      avatarUrl: null,
    };
  },
  created () {
    this.getLoginUser();
    
  },
  methods: {
    getLoginUser() {
      axios
        .get(UrlConstants.User + "/" + this.$cookies.get("id"))
        .then((res) => {
          this.loginUser = res.data.data;
          this.avatarUrl = UrlConstants.AvatarSource + this.loginUser.avatar
        });
    },
  }
};
</script>

<style scoped>
#avatar-nav {
  border-radius: 50%;
}
</style>
