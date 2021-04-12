<template>
  <div class="main_div" id="login_form">
    <div class="login_card">
      <div class="title">Login</div>
      <p style="text-align: center">
        ---------------Sign In with Google---------------
      </p>
      <div class="social_icons">
        <p class="social-login" id="google-login">
          <img src="assets/images/google-logo.png" alt="" width="30px" /><span
            >Google</span
          >
        </p>
      </div>
      <p style="text-align: center">---------------OR---------------</p>
      <form action="#" @submit.prevent="onSubmit">
        <p class="error">{{ this.error }}</p>
        <div class="input_box">
          <input type="text" v-model="email" placeholder="Email" id="email" />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
         <p style="color: red" v-if="list_errors !== null">
            {{ list_errors.email }}
          </p>
        <div class="input_box">
          <input
            type="password"
            v-model="password"
            placeholder="Password"
            id="password"
          />
          <div class="icon"><em class="fas fa-lock"></em></div>
        </div>
        <p style="color: red" v-if="list_errors !== null">
            {{ list_errors.password }}
          </p>
        <p class="forgot-password text-right mt-2 mb-4">
          <router-link to="/forgot-password">Forgot password?</router-link>
        </p>
        <div class="input_box button">
          <input type="submit" value="Login" />
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
import { commonHelper } from "@/helper/commonHelper";
import { validateHelper } from "@/helper/validateHelper";
export default {
  name: "Login",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      email: "",
      password: "",
      requireAttribute: {
        email: "Email",
        password: "Password",
      },
      error: null
    };
  },
  methods: {
    onSubmit() {
      let formData = {
        email: this.email,
        password: this.password,
      };
      this.requiredValidate(this.requireAttribute, formData);
      this.showError(this.requireAttribute, this.list_errors);
      if (this.validate) {
        axios
          .post(UrlConstants.Login, formData)
          .then((r) => {
            let jwt = this.$cookies.set("jwt", r.data.data.token, "30min");
            this.$cookies.set("id", r.data.data.id, "30min");
            this.$emit("user-login", jwt);
            this.$router.push("/");
          })
          .catch((e) => {
            this.error = "Wrong user or password";
          });
      }
    },
    async logInWithFacebook() {
      await this.loadFacebookSDK(document, "script", "facebook-jssdk");
      await this.initFacebook();
      window.FB.login((response) => {
        console.log("fb response", response);
      }, this.params);
    },
    async initFacebook() {
      window.fbAsyncInit = function () {
        window.FB.init({
          appId: "806034696708294",
          cookie: true,
          version: "v13.0",
        });
      };
    },
    async loadFacebookSDK(d, s, id) {
      let js,
        fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) {
        return;
      }
      js = d.createElement(s);
      js.id = id;
      js.src = "https://connect.facebook.net/en_US/sdk.js";
      fjs.parentNode.insertBefore(js, fjs);
    },
  },
};
</script>
<style scoped>
.social-login {
  cursor: pointer;
  margin-right: 20px;
}
.error {
  padding-top: 5px;
  color: red;
  font-weight: bolder;
}
#login_form {
  margin-top: 50px;
  margin-left: 18%;
}
</style>
