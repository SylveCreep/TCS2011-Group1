<template>
  <div class="app-main__inner" style="background-color: #f0f3f5">
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
            <router-link
              to="/contributions/submit"
              class="btn-shadow btn btn-info"
            >
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
          v-bind:id="'tab-' + status"
          data-toggle="tab"
          v-bind:href="'#tab-content-' + status"
          v-on:change="getStatus(status)"
        >
          <span>{{ index }}</span>
        </a>
      </li>
    </ul>
    <div class="card-title" style="padding:20px 20px 0;">
      <div class="row">
        <h4><b>Filter</b></h4>
      </div>
      <div class="row">
        <div class="form-group">
          <label>Code</label>
          <input
            class="form-control"
            type="text"
            placeholder="Search"
            aria-label="Search"
            v-model="filter.code"
            v-on:keyup="getFilter"
          />
        </div>
        <div class="form-group">
          <label>Student Name</label>
          <input
            class="form-control"
            type="text"
            placeholder="Search"
            aria-label="Search"
            v-model="filter.fullName"
            v-on:keyup="getFilter"
          />
        </div>
        <div class="form-group">
          <label>Faculty</label>
          <select
            class="form-control select2"
            id="faculty_id"
            name="faculty"
            v-model="filter.facultyId"
            v-on:change="getFilter"
          >
            <option value="" selected>All</option>
            <option
              v-for="faculty in list_faculties"
              :key="faculty.id"
              v-bind:value="faculty.facultyId"
            >
              {{ faculty.facultyName }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label>Submit date </label>
          <input
            class="form-control"
            type="date"
            placeholder="Search"
            aria-label="Search"
            v-model="filter.publishedAt"
            v-on:keyup="getFilter"
          />
        </div>
      </div>
    </div>
    <div class="tab-content">
      <div
        v-for="(status, index) of list_statuses"
        :key="index"
        class="tab-pane tabs-animation fade"
        v-bind:id="'tab-content-' + status"
        role="tabpanel"
      >
        <div class="row">
          <div class="col-md-4" v-for="user of list_users" :key="user.id">
            <div class="main-card mb-3 card">
              <div class="card-header">
                <i class="header-icon lnr-license icon-gradient bg-plum-plate">
                </i>
                code: {{ contribution.code }}
              </div>
              <div class="card-body">
                <p>StudentName: {{ contribution.fullName }}</p>
               
                <p>Faculty: {{ contribution.facultyName }}</p>
               
                <p>Submit Date: {{ contribution.createdAt | formatDate }}</p>
         
                <p v-if="status = 2">Approved by: {{ contribution.roleName }}</p>
                
                <p v-if="status = 0">Denied by: {{ contribution.roleName }}</p>
              </div>
              <div class="d-block text-right card-footer">
                <a
                  class="btn-wide btn contribution-detail"
                  v-on:click="showDetail(user.id)"
                  >Detail</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <strong> Items per page: </strong>
          <select v-on:change="getLimit($event)">
            <option value="9">9</option>
            <option value="15" selected>15</option>
            <option value="3">3</option>
          </select>
        </div>
        <div class="col-md-6">
          <the-pagination
            v-bind:pagination="list_users"
            v-on:currentPage="changePage"
          ></the-pagination>
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
  components: {
    ThePagination,
  },
  mixins: [commonHelper],
  data() {
    return {
      list_statuses: DefaultConstants.ContributionStatuses,
      list_contributions: [],
    };
  },

  mounted() {
    document.querySelector("#tab-1").click(); //default click to tab 1
  },
  created() {
    this.getContributionList();
    this.getFacultyList();
  },
  methods: {
    showDetail(contribution_id) {
      axios
        .get(UrlConstants.Contribution + "/" + contribution_id)
        .then((response) => {
          if (response.data.code === ResultConstants.Failure) {
            alert("error");
            this.getContributionList();
          } else {
            router.push("/contributions/" + contribution_id + "/detail");
          }
        });
    },
    getLimit(event) {
      this.getcommonLimit(event.target.value);
      this.getContributionList();
    },
    changePage(e) {
      this.changecommonPage(e);
      this.getContributionList();
    },
    getFilter() {
      this.filter.page = DefaultConstants.firstPage;
      this.getContributionList();
    },
    getStatus(status) {
      this.filter.status = status
      this.getContributionList();
    }
  },
};
</script>
<style scoped>
.contribution-detail {
  background-color: #3f6ad8;
  color: white !important;
}
.card-body {
  padding-left: 2rem !important;
}
.row .card-footer {
  background-color: #f0f3f5;
}
</style>
