<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Magazine Create</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Create Form</h5>
        <form v-on:submit.prevent="createMagazine()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Theme: </label>
            <div class="col-sm-12">
              <input
                id="magazineTheme"
                type="text"
                class="form-control"
                v-model="magazine.magazineTheme"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.magazineTheme }}
              </p>
            </div>
            <label class="col-sm-2 control-label">Closure date: </label>
            <div class="col-sm-12">
              <input
                id="magazineClosure"
                type="date"
                class="form-control"
                v-model="magazine.magazineClosure"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.magazineClosure }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group text-center">
            <div class="col-sm-offset-2 col-sm-12">
              <router-link to="/magazines" tag="button" class="btn btn-primary">
                Back
              </router-link>
              <button type="submit" class="btn btn-success">
                Create
              </button>
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
  name: "MagazineCreate",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      magazine: {},
      requireAttribute: {
        magazineTheme: "Magazine Theme",
      },
    };
  },
  methods: {
    async createMagazine() {
      this.requiredValidate(this.requireAttribute, this.magazine); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {  
        await this.confirmAlert("create", "magazine");
        if (this.confirmResult) {
          axios
            .post(UrlConstants.Magazine, this.magazine)
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
