<template>
  <div class="app-main__inner">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-drawer icon-gradient bg-happy-itmeo"> </i>
          </div>
          <div>
            <h3>Contribution List</h3>  
          </div>
        </div>
        <div class="page-title-actions">
          <button
            type="button"
            data-toggle="tooltip"
            title="Example Tooltip"
            data-placement="bottom"
            class="btn-shadow mr-3 btn btn-dark"
          >
            <i class="fa fa-star"></i>
          </button>
          <div class="d-inline-block dropdown">
           <router-link to="/contributions/submit" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Submit
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <ul class="body-tabs body-tabs-layout tabs-animated body-tabs-animated nav">
      <li
        class="nav-item "
        v-for="(status, index) of list_statuses"
        :key="index"
      >
        <a
          role="tab"
          class="nav-link"
          v-bind:id="'tab-'+status"
          data-toggle="tab"
          v-bind:href="'#tab-content-' + status"
        >
          <span>{{ index }}</span>
        </a>
      </li>
    </ul>
    <div class="tab-content">
      <div
        v-for="(status, index) of list_statuses"
        :key="index"
        class="tab-pane tabs-animation fade"
        v-bind:id="'tab-content-' + status"
        role="tabpanel"
      >
        <div class="row">
          <div class="col-md-4">
            <div class="main-card mb-3 card">
              <div class="card-header">
                <i class="header-icon lnr-license icon-gradient bg-plum-plate">
                </i>
                Contribution code: C0001
              </div>
              <div class="card-body">
               
                    StudentName: TanNguyen
                    <br>
                    Faculty: IT
                    <br>
                    Submit Date: 16/03/2021
                    <br>
                    Approved by: Admin
             
              </div>
              <div class="d-block text-right card-footer">
                <a href="javascript:void(0);" class="btn-wide btn btn-success"
                  >Detail</a
                >
              </div>
            </div>
          </div>
           <div class="col-md-4">
            <div class="main-card mb-3 card">
              <div class="card-header">
                <i class="header-icon lnr-license icon-gradient bg-plum-plate">
                </i>
                Contribution code: C0001
              </div>
              <div class="card-body">
                <p>
                    StudentName: TanNguyen
                    <br>
                    Faculty: IT
                    <br>
                    Submit Date: 16/03/2021
                    <br>
                    Approved by: Admin
                </p>
              </div>
              <div class="d-block text-right card-footer">
                <a href="javascript:void(0);" class="btn-wide btn btn-success"
                  >Detail</a
                >
              </div>
            </div>
          </div>
           <div class="col-md-4">
            <div class="main-card mb-3 card">
              <div class="card-header">
                <i class="header-icon lnr-license icon-gradient bg-plum-plate">
                </i>
                Contribution code: C0001
              </div>
              <div class="card-body">
                <p>
                    StudentName: TanNguyen
                    <br>
                    Faculty: IT
                    <br>
                    Submit Date: 16/03/2021
                    <br>
                    Approved by: Admin
                </p>
              </div>
              <div class="d-block text-right card-footer">
                <a href="javascript:void(0);" class="btn-wide btn btn-success"
                  >Detail</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
import { ResultConstants } from "@/constant/ResultConstant";
import router from "@/router";
import ThePagination from "@/components/ThePagination";
export default {
  name: "ContributionList",
  data() {
    return {
      list_statuses: DefaultConstants.ContributionStatuses,
    };
  },
  
  mounted() {
      document.querySelector("#tab-1").click() //default click to tab 1
  },
   created() {
      
  },
  methods: {

  },
};
</script>
<style scoped>
    .card-body {
        padding-left: 2rem !important;
    }
</style>
