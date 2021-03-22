<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2 v-if="loggedUser !== undefined">Profile Update</h2>
            <h2 v-else>User Update</h2>
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
              <input
                id="facultyId"
                type="text"
                class="form-control"
                v-model="user.facultyName"
                readonly
              />
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
            <label class="col-sm-2 control-label">Gender: </label>
            <div class="col-sm-12">
              <input
                type="radio"
                id="rmale"
                v-model="user.gender"
                value="1"
              />
              <label for="male" class="label-gender">Male</label>
              <input
                type="radio"
                id="rfemale"
                v-model="user.gender"
                value="0"
              />
              <label for="female" class="label-gender">Female</label>
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
          <div class="position-relative form-group">
            <label for="exampleFile" class="col-sm-2 control-label">Avatar</label>
            <div class="col-3">
              <input
                name="file"
                id="exampleFile"
                type="file"
                ref="file"
                class="form-control-file"
                v-on:change="onFileChange"
              />
            </div>
            <p class="form-text-file"
              >This is some placeholder block-level help text for the above
              input. It's a bit lighter and easily wraps to a new line.</p
            >
            <div class="col-3">
              <div id="preview">
                <img src="http://localhost:8080/file/users/avatar_U0217.png" />   
              </div>
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
  props: {
    loggedUser: Number
  },
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
    this.getFacultyList(); //This function are called from commonHelper.js file
  },
  methods: {
    getUser() {
      let user_id = this.$route.params.id
      if (this.loggedUser !== undefined) {
        user_id = this.loggedUser
      }
      axios
        .get(UrlConstants.User + "/" + user_id )
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
        let updateUser = {
          id: this.user.id,
          fullName: this.user.fullName,
          roleId: this.user.roleId,
          facultyId: this.user.facultyId,
          email: this.user.email,
          address: this.user.address,
          gender: this.user.gender,
          phoneNumber: this.user.phoneNumber,
          dateOfBirth: this.user.dateOfBirth
        }
        axios
          .patch(UrlConstants.User, updateUser)
          .then((response) => {
            this.successAlert(); //This function are called from commonHelper.js file
            this.$router.push("/users");
          })
          .catch((error) => {
            this.errors = error.response.data.errors;
            this.showError(this.errors);
          });
      }
    },
    onFileChange() {
      
    }
  },
};
</script>

<style scoped>

.app-page-title {
  margin:-30px 0 0 -30px;
};
.label-gender {
  padding-left: 5px !important;
  padding-right: 20px !important;
}
</style>
