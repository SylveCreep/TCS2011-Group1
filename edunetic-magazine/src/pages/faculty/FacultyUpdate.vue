<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Faculty Update</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Update Form</h5>
        <form v-on:submit.prevent="updateFaculty()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Name: </label>
            <div class="col-sm-12">
              <input
                id="facultyName"
                type="text"
                class="form-control"
                v-model="faculty.facultyName"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.facultyName }}
              </p>
            </div>
          </div>
          <label class="col-sm-2 control-label">Faculty code: </label>
          <div class="col-sm-12">
            <input
              id="facultycode"
              type="text"
              class="form-control"
              v-model="faculty.code"
              readonly
            />
          </div>
          <div class="col-sm-offset-2 col-sm-12 text-center">
            <router-link to="/faculties" tag="button" class="btn btn-primary">
              Back
            </router-link>
            <button
              type="submit"
              class="btn btn-success"
            >
              Update
            </button>
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
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      faculty: {},
      requireAttribute: {
        facultyName: "Faculty Name",
      },
    };
  },
  created() {
    this.getFaculty();
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
    updateFaculty() {
      this.requiredValidate(this.requireAttribute, this.faculty); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        axios
          .patch(
            UrlConstants.Faculty, this.faculty
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