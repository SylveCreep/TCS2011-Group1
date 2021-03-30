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
                  id="exampleFile"
                  type="file"
                  class="form-control-file"
                />
              </div>
              <div class="col-sm-12">
                <p class="form-text-file">
                  This is the area for student import file contribution to upload
                </p>
              </div>
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
            <button type="submit" class="btn btn-success">
              Submit
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
  name: "ContributionSubmit",
  mixins: [commonHelper, validateHelper],
  data() {
    return {
      user: [],
    };
  },
  methods: {
    
  },
  props: {},
};
</script>

<style scoped>
.card {
  margin: 0 0 30px;
}
.app-page-title {
  margin:-30px 0 0 -30px;
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
</style>
