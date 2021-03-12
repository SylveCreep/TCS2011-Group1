<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Role Create</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Create Form</h5>
        <form v-on:submit.prevent="createRole()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Name: </label>
            <div class="col-sm-12">
              <input
                id="name"
                type="text"
                class="form-control"
                v-model="role.name"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.name }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group text-center">
            <div class="col-sm-offset-2 col-sm-12">
              <router-link to="/roles" tag="button" class="btn btn-primary">
                Back
              </router-link>
              <button type="submit" class="btn btn-success">Create</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";

export default {
  name: "RoleCreate",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      role: {},
      requireAttribute: {
        name: "Role Name",
      },
    };
  },
  methods: {
    createRole() {
      this.userValidate(this.requireAttribute, this.role); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        axios
          .post(UrlConstants.Role, this.role)
          .then((r) => {
            alert("Create Successfully");
            this.$router.push("/roles");
          })
          .catch((error) => {
            this.list_errors = error.response;
          });
      }
    },
  },
};
</script>

<style scoped>
.card {
  margin: 0 0 30px;
}
.label-gender {
  padding-left: 5px;
  padding-right: 20px;
}
.app-page-title {
  margin:-30px 0 0 -30px;
}
</style>
