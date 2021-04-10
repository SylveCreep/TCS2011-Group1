<template>
  <div class="main_div">
    <div class="change-password_card">
      <div class="title"><h1>Change Password</h1></div>
      <form v-on:submit.prevent="changePassword()">
        <div class="input_box">
          <label class="label"><b>Email:</b></label>
          <div class="form-group-center pass_show">
            <input
              id="email"
              type="email"
              class="form-control"
              placeholder="Email"
              v-model="user.email"
            />
            <div class="icon"><em class="fas fa-user"></em></div>
          </div>
        </div>
        <p class="error_notify" style="color: red" v-if="list_errors !== null">
          {{ list_errors.email }}
        </p>
        <div class="input_box">
          <label class="label"><b>Current Password:</b></label>
          <div class="form-group pass_show">
            <input
              id="password"
              type="password"
              class="form-control"
              placeholder="Current Password"
              v-model="user.password"
            />
            <div class="icon"><em class="fas fa-user"></em></div>
          </div>
        </div>
        <p class="error_notify" style="color: red" v-if="list_errors !== null">
          {{ list_errors.password }}
        </p>
        <div class="input_box">
          <label class="label"><b>New Password:</b></label>
          <div class="form-group pass_show">
            <input
              id="new_password"
              type="password"
              class="form-control"
              placeholder="New Password"
              v-model="user.new_password"
              v-on:keyup="checkConfirmPassword"
            />
            <div class="icon"><em class="fas fa-user"></em></div>
          </div>
        </div>
        <p class="error_notify" style="color: red" v-if="list_errors !== null">
          {{ list_errors.new_password }}
        </p>
        <div class="input_box">
          <label class="label"><b>Confirm Password:</b></label>
          <div class="form-group pass_show">
            <input
              id="confirm_password"
              type="password"
              class="form-control"
              placeholder="Confirm Password"
              v-model="user.confirm_password"
              v-on:keyup="checkConfirmPassword"
            />
            <div class="icon"><em class="fas fa-user"></em></div>
          </div>
        </div>
        <p class="error_notify" style="color: red" v-if="list_errors !== null">
          {{ list_errors.confirm_password }}
        </p>
        <p v-if="password_match == false" style="color: red">
          password don't match
        </p>
        <p v-else-if="password_match == true" style="color: green">
          password matched
        </p>
        <div class="input_box text-center button">
          <router-link
            to="/"
            class="btn btn-primary"
            style="margin-right: 10px;"
          >
            Back
          </router-link>
          <button type="submit" class="btn btn-success">
            Change
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";
import { ResultConstants } from "@/constant/ResultConstant";

export default {
  name: "ChangePassword",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      user: {
        email: "",
        password: "",
        new_password: "",
        confirm_password: "",
      },
      requireAttribute: {
        email: "Email",
        password: "Password",
        new_password: "New Password",
        confirm_password: "Confirm Password",
      },
    };
  },
  methods: {
    async changePassword() {
      this.userChangePasswordValidate(this.requireAttribute, this.user); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        await this.confirmAlert("change", "password");
        if (this.confirmResult) {
          delete this.user.confirm_password;
          axios
            .post(UrlConstants.User + "/changepassword", this.user)
            .then((r) => {
              console.log("respone", r.data.code);
              if (r.data.code === ResultConstants.Success) {
                this.successAlert(); //This function are called from commonHelper.js file
                axios.delete(UrlConstants.Logout).then((r) => {
                  this.$cookies.remove("jwt");
                  this.$cookies.remove("loginUser");
                  this.$cookies.remove("id");
                  this.$cookies.remove("studentContribution");
                  this.$emit("user-logout", null);
                  this.$router.push("/login");
                });
              }
            })
            .catch((error) => {
              this.errorAlert("change", "password"); //This function are called from commonHelper.js file
              delete this.user.new_password;
              this.list_errors = {
                email: "Email or Password are not correct",
                password: "Email or Password are not correct",
              };
              this.showError(this.requireAttribute, this.list_errors);
            });
        }
      }
    },
    checkConfirmPassword() {
      let pass = document.querySelector("#new_password");
      let cpass = document.querySelector("#confirm_password");
      let self = this;
      // clear timeout variable
      clearTimeout(this.timeCheck);
      this.timeCheck = setTimeout(function() {
        if (
          self.user.new_password !== "" &&
          self.user.confirm_password !== ""
        ) {
          if (self.user.new_password !== self.user.confirm_password) {
            self.password_match = false;
            pass.style.cssText = "border-color: red";
            cpass.style.cssText = "border-color: red";
            self.validate = false;
            self.password_match = false; //render don't match message
          } else {
            self.password_match = true;
            pass.style.cssText = "border-color: #CED4DA";
            cpass.style.cssText = "border-color: #CED4DA";
            self.validate = true;
            self.password_match = true; //render match message
          }
        }
      }, 1000);
    },
  },
};
</script>

<style scoped>
.pass_show {
  position: relative;
}
.pass_show .ptxt {
  position: absolute;
  top: 50%;
  right: 10px;
  z-index: 1;
  color: #f36c01;
  margin-top: -10px;
  cursor: pointer;
  transition: 0.3s ease all;
}
.pass_show .ptxt:hover {
  color: #333333;
}
.main_div {
  padding: 50px 50px 0;
  border-radius: 10px;
  margin: 50px auto auto;
  background-color: #fff;
  width: 700px;
}
.button {
  margin: 15px 0 0 !important;
}
.label {
  margin: 0;
}
.error_notify {
  margin: 0;
}
.input_box {
  margin: 0 0 25px !important;
}
/* .btn {
  background: linear-gradient(to top, #0ba360 0%, #3cba92 100%) !important;
} */
</style>
