<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Contribution Submit</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Submit Form</h5>
        <form v-on:submit.prevent="submitContribution()">
          <div class="position-relative form-group">
            <label for="exampleFile" class="col-sm-2 control-label">File</label>
            <div class="row">
              <div class="input-file col-sm-3">
                <input
                  name="file"
                  id="file"
                  type="file"
                  ref="file"
                  class="form-control-file"
                  v-on:change="onFileChange"
                />
              </div>
              <div class="col-sm-12">
                <p style="color: red" v-if="list_errors !== null">
                  {{ list_errors.file }}
                </p>
                <p class="form-text-file">
                  This is the area for student import file contribution to
                  upload
                </p>
              </div>
            </div>
          </div>
          <div class="position-relative form-group">
            <term-condition></term-condition>
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                value=""
                id="invalidCheck"
                required
              />
              <label class="form-check-label" for="invalidCheck">
                Agree to terms and conditions
              </label>
              
            </div>
          </div>

          <div class="col-sm-offset-2 col-sm-12 text-center">
            <router-link
              to="/contributions"
              tag="button"
              class="btn btn-primary"
            >
              Back
            </router-link>
            <button type="submit" class="btn btn-success">Submit</button>
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
import TermCondition from "../../components/TermCondition.vue";
export default {
  name: "ContributionSubmit",
  mixins: [commonHelper, validateHelper],
  components: {
    TermCondition,
  },
  data() {
    return {
      contribution: {},
      requireAttribute: {
        file: "Contribution file",
      },
    };
  },
  methods: {
    async submitContribution() {
      this.requiredValidate(this.requireAttribute, this.contribution);
      if (this.validate) {
        let formData = new FormData();
        formData.append("magazineId", this.$cookies.get("magazineContribution").magazineId);
        formData.append("file", this.contribution.file);
        await this.confirmAlert("create", "user");
        if (this.confirmResult) {
          axios
            .post(UrlConstants.Contribution, formData, {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            })
            .then((r) => {
              this.successAlert(); //This function are called from commonHelper.js file
              this.$router.push("/contributions");
            })
            .catch((error) => {
              this.list_errors = error.response.data.validate.input;
              this.showError(this.requireAttribute, this.list_errors);
            });
        }
      }
    },
    onFileChange() {
      const tfile = this.$refs.file.files[0];
      this.contribution.file = tfile;
    },
  },
  props: {},
};
</script>

<style scoped>
.card {
  margin: 0 0 30px;
}
.app-page-title {
  margin: -30px 0 0 -30px;
}
.text-center {
  padding: 20px 0px;
}
.from-text {
  margin: 15px;
}
.control-label {
  margin: 0px;
}
.input-file {
  margin-left: 30px;
  padding: 0;
  border-radius: 5px;
  border: 1px solid;
}
.form-text-file {
  margin: 10px 0 0 15px;
}
.form-check {
  margin-top: 10px;
  margin-left: 15px;
}
</style>
