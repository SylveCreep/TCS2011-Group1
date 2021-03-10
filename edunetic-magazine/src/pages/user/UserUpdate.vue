<template>
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h2>User Update</h2>
            </div>
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active show" id="settings">
                  <form
                    class="form-horizontal"
                    v-on:submit.prevent="updateUser()"
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
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Role: </label>
                      <div class="col-sm-12">
                        <select
                          class="form-control select2"
                          id="roleId"
                          name="role"
                          v-model="user.roleId"
                        >
                          <option
                            v-for="role in list_roles"
                            :key="role.id"
                            v-bind:value="role.id"
                          >
                            {{ role.name }}
                          </option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
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
                    <div class="form-group">
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
                          Update
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
      console.log(this.validate)
      if (this.validate) {
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
  margin: 20px;
}
</style>
