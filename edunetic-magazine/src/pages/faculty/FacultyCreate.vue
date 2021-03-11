<template>
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h2>Faculty Create</h2>
            </div>
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active show" id="settings">
                  <form
                    class="form-horizontal"
                    v-on:submit.prevent="createFaculty()"
                  >
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Name: </label>
                      <div class="col-sm-12">
                        <input
                          id="facultyName"
                          type="text"
                          class="form-control"
                          v-model="faculty.faculty_name"
                        />
                        <p style="color: red" v-if="list_errors !== null">
                          {{ list_errors.faculty_name }}
                        </p>
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
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";

export default {
  name: "FacultyCreate",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      faculty: {},
      requireAttribute: {
        faculty_name: "Faculty Name",
      },
    };
  },
  methods: {
    createFaculty() {
      this.userValidate(this.requireAttribute, this.faculty); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        axios
          .post(UrlConstants.Faculty, this.faculty)
          .then((r) => {
            alert("Create Successfully");
            this.$router.push("/faculties");
          })
          .catch((error) => {
            this.errors = error.response;
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
