<template>
  <div class="main_div" id="login_form">
    <div class="login_card">
      <div class="title">Login</div>
      <div class="social_icons">
        <a href="#"><em class="fab fa-facebook-f"></em> <span>Facebook</span></a>
        <a href="#"><em class="fab fa-google"></em><span>Google</span></a>
      </div>
      <form action="#" @submit.prevent="onSubmit">
        <p class="error">{{ this.error }}</p>
        <div class="input_box">
          <input type="text" v-model="email" placeholder="Email" required />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
        <div class="input_box">
          <input type="password" v-model="password" placeholder="Password" required />
          <div class="icon"><em class="fas fa-lock"></em></div>
        </div>
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
export default {
  data() {
    return {
      email: "",
      password: "",
      error: null,
    };
  },
  methods: {
    onSubmit() {
      let formData = {
        email: this.email,
        password: this.password,
      };
      axios
        .post(UrlConstants.Login, formData)
        .then((r) => {
          let jwt = this.$cookies.set("jwt", r.data.data.token, "30min");
          this.$cookies.set("id", r.data.data.id, "30min");
          this.$emit("user-logged", jwt);
          this.getLoginUser();
          this.$router.push("/users");
        })
        .catch((e) => {
          this.error = "Wrong user or password";
        });
    },
    getLoginUser() {
      axios
        .get(UrlConstants.User + "/" + this.$cookies.get("id"))
        .then((res) => {
          this.$cookies.set("loginUser", res.data.data, "30min")
        });
    },
  },
};
</script>
<style scoped>
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
