<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Magazine Update</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Update Form</h5>
        <form v-on:submit.prevent="updateFaculty()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Student's name: </label>
            <div class="col-sm-12">
              <input
                id="magazineName"
                type="text"
                class="form-control"
                v-model="filter.fullName"
                v-on:keyup="getFilter"
                readonly
              />
            </div>
            <label class="col-sm-2 control-label">Faculty: </label>
            <div class="col-sm-12">
              <input
                id="facultyName"
                type="text"
                class="form-control"
                v-model="faculty.facultyName"                
              />
            </div>
            <label class="col-sm-2 control-label">Code: </label>
              <div class="col-sm-12">
                <input
                  id="magazineCode"
                  type="text"
                  class="form-control"
                  v-model="magazine.magazineCode"
                  readonly
                />
              </div>
            <label class="col-sm-2 control-label">Closure date: </label>
              <div class="col-sm-12">
                <input
                  id="magazineClosure"
                  type="date"
                  class="form-control"
                  v-model="magazine.magazineClosure"
                  readonly
                />
              </div>
            <label class="col-sm-2 control-label">Published date: </label>
              <div class="col-sm-12">
                <input
                  id="magazinePublished"
                  type="date"
                  class="form-control"
                  v-model="magazine.magazinePublished"
                  readonly
                />
              </div>
          </div>
          <div class="col-sm-offset-2 col-sm-12 text-center">
            <router-link to="/faculties" tag="button" class="btn btn-primary">
              Back
            </router-link>
            <button type="submit" class="btn btn-success">
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
  name: "MagazineUpdate",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      magazine: {},
      requireAttribute: {
        magazineName: "Magazine Name",
      },
    };
  },
  created() {
    this.getMagazine();
  },
  methods: {
    getMagazine() {
      axios
        .get(UrlConstants.Magazine + "/" + this.$route.params.id)
        .then((r) => {
          this.faculty = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    async updateMagazine() {
      this.requiredValidate(this.requireAttribute, this.faculty); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        await this.confirmAlert("update", "magazine");
        if (this.confirmResult) {
          axios
            .patch(UrlConstants.Magazine, this.magazine)
            .then((r) => {
              this.successAlert();
              this.$router.push("/magazines");
            })
            .catch((error) => {
              this.errors = error.response;
            });
        }
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
  margin: -30px 0 0 -30px;
}
</style>
