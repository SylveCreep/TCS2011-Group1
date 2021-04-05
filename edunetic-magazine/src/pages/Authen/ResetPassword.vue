<template>
  <div class="main_div" id="login_form" v-if="canAccess">
    <div class="login_card">
      <div class="title">Reset Password</div>
      <form action="#" @submit.prevent="resetPassword()">
        <div class="input_box">
          <input
            id="email"
            type="email"
            v-model="user.email"
            placeholder="Email"
          />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
        <p style="color: red" v-if="list_errors !== null">
          {{ list_errors.email }}
        </p>
        <div class="input_box">
          <input
            id="password"
            type="password"
            class="form-control"
            v-model="user.password"
            placeholder="Password"
          />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
        <p style="color: red" v-if="list_errors !== null">
          {{ list_errors.password }}
        </p>
        <div class="input_box">
          <input
            id="confirm_password"
            type="password"
            class="form-control"
            v-model="user.confirm_password"
            placeholder="Confirm Password"
            v-on:keyup="checkPassword"
          />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
        <p style="color: red" v-if="list_errors !== null">
          {{ list_errors.confirm_password }}
        </p>
        <p v-if="password_match == false" style="color: red">
          password don't match
        </p>
        <p v-else-if="password_match == true" style="color: green">
          password matched
        </p>
        <div class="input_box button">
          <input type="submit" value="Send" />
        </div>
      </form>
    </div>
  </div>
  <not-found v-else></not-found>
</template>

<script>
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";
import NotFound from "@/pages/NotFound";
import Swal from "sweetalert2";
export default {
  name: "ResetPassword",
  mixins: [validateHelper,commonHelper],
  components: {
    NotFound,
  },
  data() {
    return {
      canAccess: true,
      user: {
        key: this.$route.params.id,
      },
      requireAttribute: {
        email: "email",
        password: "password",
        confirm_password: "Confirm password",
      },
    };
  },
  created() {
    console.log(this.$route.params.id);
    this.checkKeyInvalid();
  },
  methods: {
    async resetPassword() {
      this.requiredValidate(this.requireAttribute, this.user); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        await this.confirmAlert("reset", "password");
        if (this.confirmResult) {
          delete this.user.confirm_password;
          axios
            .post(UrlConstants.User + "/updatePassword", this.user)
            .then((r) => {
              this.successAlert(); //This function are called from commonHelper.js file
              this.$router.push("/login");
            })
            .catch((error) => {
              this.list_errors = {
                  password: "Password length must bigger than 8"
              };
              this.showError(this.requireAttribute, this.list_errors);
            });
        }
      }
    },
    checkKeyInvalid() {
      axios
        .get(UrlConstants.User + "/password/check?key=" + this.$route.params.id)
        .then((r) => {
          if (r.data.code === 0) {
              this.canAccess = false
          }
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
