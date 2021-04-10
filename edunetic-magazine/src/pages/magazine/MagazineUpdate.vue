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
        <form v-on:submit.prevent="updateMagazine()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Code: </label>
              <div class="col-sm-12">
                <input
                  id="code"
                  type="text"
                  class="form-control"
                  v-model="magazine.code"
                  readonly
                />
              </div>
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
              <label class="col-sm-2 control-label">Finished At: </label>
              <div class="col-sm-12">
                <input
                  id="close_at"
                  type="date"
                  class="form-control"
                  v-model="magazine.close_at"                  
                />
              </div>
              <label class="col-sm-2 control-label">Published At: </label>
              <div class="col-sm-12">
                <input
                  id="published_at"
                  type="date"
                  class="form-control"
                  v-model="magazine.published_at"                  
                />
              </div>             
          </div>
          <div class="col-sm-offset-2 col-sm-12 text-center">
            <router-link to="/magazines" tag="button" class="btn btn-primary">
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
import moment from 'moment';

export default {
  name: "MagazineUpdate",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      magazine: {},
      user:{},
      requireAttribute: {
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
          this.magazine = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    getStudent() {
      axios
        .get(UrlConstants.User + "/" + this.$route.params.id)
        .then((r) => {
          this.user = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    async updateMagazine() {
      this.requiredValidate(this.requireAttribute, this.magazine); //this function is called from helperMixin.js file
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
              this.list_errors = error.response.data.validate.input;
              this.showError(this.requireAttribute, this.list_errors);
            });
        }
      }
    },
    formatDate(time) {
      moment(time).format('YYYY-MM-DD')
    }
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
