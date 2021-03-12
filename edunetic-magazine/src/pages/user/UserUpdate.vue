<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>User Update</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Update Form</h5>
        <form v-on:submit.prevent="updateUser()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Full Name: </label>
            <div class="col-sm-12">
              <input
                id="fullName"
                type="text"
                class="form-control"
                v-model="user.fullName"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.fullName }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Code: </label>
            <div class="col-sm-12">
              <input
                id="code"
                type="text"
                class="form-control"
                v-model="user.code"
                readonly
              />
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Role: </label>
            <div class="col-sm-12">
              <input
                id="roleId"
                type="text"
                class="form-control"
                v-model="user.roleName"
                readonly
              />
            </div>
          </div>
          <div
            class="position-relative form-group"
            v-if="user.roleId === 3 || user.roleId === 4"
          >
            <label class="col-sm-2 control-label">Faculty: </label>
            <div class="col-sm-12">
              <select
                class="form-control select2"
                id="facultyId"
                name="faculty"
                v-model="user.facultyId"
              >
                <option
                  v-for="faculty in list_faculties"
                  :key="faculty.id"
                  v-bind:value="faculty.id"
                >
                  {{ faculty.faculty_name }}
                </option>
              </select>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Email: </label>
            <div class="col-sm-12">
              <input
                id="email"
                type="text"
                class="form-control"
                v-model="user.email"
                readonly
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.email }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Address: </label>
            <div class="col-sm-12">
              <input
                id="address"
                type="text"
                class="form-control"
                v-model="user.address"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.address }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Phone Number: </label>
            <div class="col-sm-12">
              <input
                id="phoneNumber"
                type="tel"
                class="form-control"
                v-model="user.phoneNumber"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.phoneNumber }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Date of birth: </label>
            <div class="col-sm-12">
              <input
                id="dateOfBirth"
                type="date"
                class="form-control"
                v-model="user.dateOfBirth"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.dateOfBirth }}
              </p>
            </div>
          </div>
          <div class="col-sm-offset-2 col-sm-12 text-center">
            <router-link to="/users" tag="button" class="btn btn-primary">
              Back
            </router-link>
            <button type="submit" class="btn btn-success">Update</button>
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
  name: "UserUpdate",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      user: {},
      requireAttribute: {
        fullName: "Fullname",
        address: "Address",
        phoneNumber: "Phone number",
        dateOfBirth: "Date of birth",
      },
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      axios
        .get(UrlConstants.User + "/" + this.$route.params.id)
        .then((r) => {
          this.user = r.data.data;
        })
        .catch((error) => {
          this.list_errors = error.response;
        });
    },
    updateUser() {
      this.userValidate(this.requireAttribute, this.user); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
         axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
        axios
          .patch(UrlConstants.User + "/" + this.$route.params.id, this.user)
          .then((response) => {
            console.log(response);
            alert("success");
            this.$router.push("/users");
          })
          .catch((error) => {
            this.errors = error.response.data.errors;
            this.showError(this.errors);
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
.app-page-title {
  margin:-30px 0 0 -30px;
};
</style>
