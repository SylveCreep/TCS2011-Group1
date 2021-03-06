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
                          v-model="faculty.name"
                        />
                      </div>
                      <label class="col-sm-2 control-label">Faculty code: </label>
                      <div class="col-sm-12">
                        <input
                          id="facultycode"
                          type="text"
                          class="form-control"
                          v-model="faculty.code" readonly
                        />
                      </div>
                      <label class="col-sm-2 control-label">Co-codinator's Name: </label>
                      <div class="col-sm-12">
                        <input
                          id="codinatorname"
                          type="text"
                          class="form-control"
                          v-model="faculty.codinatorname"
                        />
                      </div>
                    </div>
                    <div class="form-group text-center">
                      <div class="col-sm-offset-2 col-sm-12">
                        <router-link
                          to="/roles"
                          tag="button"
                          class="btn btn-primary"
                        >
                          Back
                        </router-link>
                        <button type="submit" class="btn btn-success" v-on:click="UpdateFaculty()">
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

export default {
  name: "RoleCreate",
  data() {
    return {
      faculty: {},
      errors: null,
      list_faculties: {},
      list_roles: {},
    };
  },
  created() {
    this.getFacultyList();
  },
  methods: {
    updateFaculty() {
      axios
        .post(UrlConstants.Faculty, this.faculty)
        .then((r) => {
          console.log(r);
          alert("Update Successfully");
          this.$router.push("/faculties");
        })
        .catch((error) => {
          this.errors = error.response;
          console.log(this.errors);
        });
    },
    showError(errors) {
      Object.keys(errors).forEach((error) => {
        let text = document.querySelector("#" + error);
        text.style.cssText = "border-color: red";
      });
    },
    getFacultyList() {
      axios
        .get(UrlConstants.Faculty, this.faculty)
        .then((response) => {
          this.list_faculties = response.data;
          console.log(this.list_faculties);
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
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
