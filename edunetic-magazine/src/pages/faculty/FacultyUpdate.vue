<template>
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h2>Faculty Update</h2>
            </div>
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active show" id="settings">
                  <div class="alert alert-danger" v-if="errors !== null">
                    <ul>
                      <li v-for="(v, k) in errors" :key="k">
                        {{ v.toString() }}
                      </li>
                    </ul>
                  </div>
                  <form
                    class="form-horizontal"
                    v-on:submit.prevent="updateFaculty()"
                  >
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Name: </label>
                      <div class="col-sm-12">
                        <input
                          id="facultyname"
                          type="text"
                          class="form-control"
                          v-model="faculty.faculty_name"
                        />
                      </div>
                      <label class="col-sm-2 control-label"
                        >Faculty code:
                      </label>
                      <div class="col-sm-12">
                        <input
                          id="facultycode"
                          type="text"
                          class="form-control"
                          v-model="faculty.code"
                          readonly
                        />
                      </div>
                      <label class="col-sm-2 control-label"
                        >Co-codinator's name:
                      </label>
                      <div class="col-sm-12">
                        <select
                          class="form-control select2"
                          id="user_id"
                          name="category"
                        >
                          <option
                            v-for="user in list_users"
                            :key="user.id"
                            v-bind:value="user.id"
                          >
                            {{ user.fullName }}
                          </option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group text-center">
                      <div class="col-sm-offset-2 col-sm-12">
                        <router-link
                          to="/faculties"
                          tag="button"
                          class="btn btn-primary"
                        >
                          Back
                        </router-link>
                        <button
                          type="submit"
                          class="btn btn-success"
                          v-on:click="UpdateFaculty()"
                        >
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
import { RoleConstants } from "@/constant/RoleConstants";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  name: "RoleCreate",
  data() {
    return {
      faculty: [],
      errors: null,
      list_users: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
        roleId: RoleConstants.MarketingCoordinator, //default role_id of MarketingCoordinator = 3
      },
    };
  },
  created() {
    this.getFaculty();
    this.getUserList();
  },
  methods: {
    getFaculty() {
      axios
        .get(UrlConstants.Faculty + "/" + this.$route.params.id)
        .then((r) => {
          this.faculty = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    showError(errors) {
      Object.keys(errors).forEach((error) => {
        let text = document.querySelector("#" + error);
        text.style.cssText = "border-color: red";
      });
    },
    getUserList() {
      axios
        .post(UrlConstants.User + "/filter", this.filter)
        .then((response) => {
          this.list_users = response.data.data;
          console.log(this.list_users);
        })
        .catch((error) => {
          this.errors = error.response.data;
          this.showError(this.errors);
        });
    },
    updateFaculty() {
      axios
        .patch(
          UrlConstants.Faculty + "/" + this.$route.params.faculty_id,
          this.faculty
        )
        .then((response) => {
          console.log(response);
          alert("success");
          this.$router.push("/faculties");
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
          this.showError(this.errors);
        });
    },
  },
};
</script>

<style scoped>
.card {
  margin: 20px;
}
</style>
