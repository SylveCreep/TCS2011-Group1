<template>
  <div class="row-center">
    <div class="back-ground">
      <form v-on:submit.prevent="changePassword()">
        <div class="row">
          <div class="col-md-12">
            <label>Email:</label>
            <div class="form-group-center pass_show">
              <input type="text" class="form-control" />
            </div>
            <label>Current Password:</label>
            <div class="form-group pass_show">
              <input
                type="password"
                class="form-control"
                placeholder="Current Password"
              />
            </div>
            <label>New Password:</label>
            <div class="form-group pass_show">
              <input
                type="password"
                class="form-control"
                placeholder="New Password"
              />
            </div>
            <label>Confirm Password:</label>
            <div class="form-group pass_show">
              <input
                type="password"
                class="form-control"
                placeholder="Confirm Password"
              />
            </div>
          </div>
          <div class="mx-auto">
              <router-link to="/" class="btn btn-primary" style="margin-right: 10px;">
              Back
            </router-link>
            <button type="submit" class="btn btn-success">
              Change
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";

export default {
  name: "ChangePassword",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      
    };
  },
  created() {

},
  methods: {

  }
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
.row-center {
  margin: 50px auto auto;
  width: 500px;
}
.back-ground {
  padding: 50px;
  background-color: #fff;
}
</style>
