<template>
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h2>User Create</h2>
            </div>
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active show" id="settings">
                  <form
                    class="form-horizontal"
                    v-on:submit.prevent="createUser()"
                  >
                    <div class="form-group">
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Role: </label>
                      <div class="col-sm-12">
                        <select
                          class="form-control"
                          id="roleId"
                          name="role"
                          v-model="user.roleId"
                        >
                          <option
                            v-for="role in roleList"
                            :key="role.id"
                            v-bind:value="role.id"
                          >
                            {{ role.name }}
                          </option>
                        </select>
                      </div>
                    </div>
                    <div
                      class="form-group"
                      v-if="user.roleId === 3 || user.roleId === 4"
                    >
                      <label class="col-sm-2 control-label">Faculty: </label>
                      <div class="col-sm-12">
                        <select
                          class="form-control select2"
                          id="facultyId"
                          name="category"
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Email: </label>
                      <div class="col-sm-12">
                        <input
                          id="email"
                          type="email"
                          class="form-control"
                          v-model="user.email"
                        />
                        <p style="color: red" v-if="list_errors !== null">
                          {{ list_errors.email }}
                        </p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Password: </label>
                      <div class="col-sm-12">
                        <input
                          id="password"
                          type="password"
                          class="form-control"
                          v-model="user.password"
                        />
                        <p style="color: red" v-if="list_errors !== null">
                          {{ list_errors.password }}
                        </p>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label"
                        >Confirm password:
                      </label>
                      <div class="col-sm-12">
                        <input
                          id="confirm_password"
                          type="password"
                          class="form-control"
                          v-model="user.confirm_password"
                          v-on:keyup="checkPassword"
                        />
                        <p style="color: red" v-if="list_errors !== null">
                          {{ list_errors.confirm_password }}
                        </p>
                        <p v-if="password_match == false" style="color: red">
                          password don't match
                        </p>
                        <p
                          v-else-if="password_match == true"
                          style="color: green"
                        >
                          password matched
                        </p>
                      </div>
                    </div>
                    <div class="form-group">
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Gender </label>
                      <div class="col-sm-12">
                        <input
                          type="radio"
                          id="rmale"
                          v-model="user.gender"
                          value="1"
                          checked
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label"
                        >Phone Number:
                      </label>
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label"
                        >Date of birth:
                      </label>
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
                    <div class="form-group text-center">
                      <div class="col-sm-offset-2 col-sm-12">
                        <router-link
                          to="/users"
                          tag="button"
                          class="btn btn-primary"
                        >
                          Back
                        </router-link>
                        <button type="submit" class="btn btn-success">
                          Create
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
                <!-- /.tab-pane -->
              </div>
              <!-- /.tab-content -->
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.nav-tabs-custom -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
  </section>
  <!-- /.content -->
</template>

<script>
import axios from "axios";
import { validateHelper } from "@/helper/validateHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  name: "UserCreate",
  mixins: [validateHelper],
  data() {
    return {
      user: {
        gender: 1,
        roleId: 1,
      },
      requireAttribute: {
        fullName: "Fullname",
        address: "Address",
        phoneNumber: "Phone number",
        dateOfBirth: "Date of birth",
        email: "email",
        password: "password",
        confirm_password: "Confirm password",
      },
      list_errors: {},
      list_faculties: [],
      list_roles: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
      },
      timeCheck: null,
  
      password_match: null,
      validate: true,
    };
  },
  computed: {
    roleList() {
      return this.list_roles.filter((role) => role.id !== 5);
    },
  },
  created() {
    this.getFacultyList();
    this.getRoleList();
  },
  methods: {
    createUser() {
      this.list_errors = this.userValidate(this.requireAttribute, this.user); //this function is called from helperMixin.js file
      if (Object.keys(this.list_errors).length > 0) {
        this.validate = false;
      }
      console.log(this.validate)
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        this.preFormatDate(this.user.dateOfBirth);
        axios
          .post(UrlConstants.User, this.list_errors)
          .then((r) => {
            alert("Create Successfully");
            this.$router.push("/users");
          })
          .catch((error) => {
            this.list_errors = error.response;
          });
      }
    },

    getRoleList() {
      axios
        .post(UrlConstants.Role + "/filter", this.filter)
        .then((response) => {
          this.list_roles = response.data.data;
        })
        .catch((error) => {
          this.list_errors = error.response.data;
          this.showError(this.list_errors);
        });
    },
    getFacultyList() {
      axios
        .post(UrlConstants.Faculty + "/filter", this.filter)
        .then((response) => {
          this.list_faculties = response.data.data;
        })
        .catch((error) => {
          this.list_errors = error.response.data;
          this.showError(this.list_errors);
        });
    },
    checkPassword() {
      let pass = document.querySelector("#password");
      let cpass = document.querySelector("#confirm_password");
      let self = this;
      // clear timeout variable
      clearTimeout(this.timeCheck);
      this.timeCheck = setTimeout(function () {
        if (self.user.password !== self.user.confirm_password) {
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
      }, 1000);
    },
  },
};
</script>

<style scoped>
.card {
  margin: 20px;
}
.label-gender {
  padding-left: 5px;
  padding-right: 20px;
}
</style>
