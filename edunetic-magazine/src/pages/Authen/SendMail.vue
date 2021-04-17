<template>
  <div class="main_div" id="login_form">
    <div class="login_card">
      <div class="title">Recover Password</div>
      <form action="#" @submit.prevent="onSubmit">
        <p style="color: red">{{ this.error }}</p>
        <div class="input_box">
          <input type="text" v-model="email" placeholder="Email" required />
          <div class="icon"><em class="fas fa-user"></em></div>
        </div>
        <div class="input_box button">
          <input type="submit" value="Send" />
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "SendMail",
  data() {
    return {
      email: "",
      error: null,
    };
  },
  methods: {
    onSubmit() {
      axios
        .get(UrlConstants.SendMail + "email=" + this.email)
        .then((r) => {
          Swal.fire({
            icon: "success",
            html:
              "<h1>We have sent the reset link to your email.</h1> <br> <b>Check it now<b>",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push("/login");
        })
        .catch((e) => {
          this.error = "Email does not existed";
        });
    },
    successAlert() {},
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
