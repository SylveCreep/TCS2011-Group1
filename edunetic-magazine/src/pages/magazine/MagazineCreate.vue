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
                id="theme"
                type="text"
                class="form-control"
                v-model="magazine.theme"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.theme }}
              </p>
            </div>       
            <label class="col-sm-2 control-label">Closed At: </label>
            <div class="col-sm-12">
              <input
                id="close_at"
                type="date"
                class="form-control"
                v-model="magazine.close_at"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.close_at }}
              </p>
            </div>     
            <label class="col-sm-2 control-label">Published At: </label>
            <div class="col-sm-12">
              <input
                id="published_at"
                type="date"
                class="form-control"
                v-model="magazine.published_at"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.published_at }}
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
        theme: "Theme",
        close_at: "Close date",
        published_at:"Published date"
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
              this.list_errors = error.response.data.validate.input;
              this.showError(this.requireAttribute, this.list_errors);
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
