<template>
  <div class="app-main__inner">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-drawer icon-gradient bg-happy-itmeo"> </i>
          </div>
          <div>
            Tabs
            <div class="page-title-subheading">
              Tabs are used to split content between multiple sections. Wide
              variety available.
            </div>
          </div>
        </div>
        <div class="page-title-actions">
          <button
            type="button"
            data-toggle="tooltip"
            title=""
            data-placement="bottom"
            class="btn-shadow mr-3 btn btn-dark"
            data-original-title="Example Tooltip"
          >
            <i class="fa fa-star"></i>
          </button>
          <div class="d-inline-block dropdown">
            <router-link to="/contributions" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Create Contribution
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <ul class="body-tabs body-tabs-layout tabs-animated body-tabs-animated nav">
      <li class="nav-item">
        <a
          role="tab"
          class="nav-link show active"
          id="tab-0"
          data-toggle="tab"
          href="#tab-content-0"
          aria-selected="true"
        >
          <span>Card Tabs</span>
        </a>
      </li>
      <li class="nav-item">
        <a
          role="tab"
          class="nav-link show"
          id="tab-1"
          data-toggle="tab"
          href="#tab-content-1"
          aria-selected="false"
        >
          <span>Animated Lines</span>
        </a>
      </li>
      <li class="nav-item">
        <a
          role="tab"
          class="nav-link show"
          id="tab-2"
          data-toggle="tab"
          href="#tab-content-2"
          aria-selected="false"
        >
          <span>Basic</span>
        </a>
      </li>
    </ul>
    <!-- <div class="card-title" style="padding:20px 20px 0;">
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
            v-on:keyup="getFilter"
          />
        </div>
        <div class="form-group">
          <input
            id="facultycode"
            type="text"
            class="form-control"
            v-model="faculty.code"
            readonly
          />
        </div>
        <div class="form-group">
          <label>Submit date </label>
          <input
            class="form-control"
            type="date"
            placeholder="Search"
            aria-label="Search"
            v-model="filter.date_of_birth"
            v-on:keyup="getFilter"
          />
        </div>
      </div>
    </div> -->
    <div class="tab-content">
      <div
        class="tab-pane tabs-animation fade active show"
        id="tab-content-0"
        role="tabpanel"
      >
        <div class="col-md-12">
          <div class="row">
            <div
              class="main-card col-md-3 card"
              v-for="user of list_users"
              :key="user.id"
            >
              <div class="card-header">Code: {{ user.code }}</div>
              <div class="card-body">
                <div class="tab-content">
                  <div
                    class="tab-pane active show"
                    id="tab-eg1-0"
                    role="tabpanel"
                  >
                    <p>Stundent Name: {{ user.fullName }}</p>
                    <p>Faculty Name: {{ user.facultyName }}</p>
                    <p>Submit date: {{ user.date_of_birth }}</p>
                    <p>Approved by: {{ user.role }}</p>
                  </div>
                </div>
              </div>
              <div class="d-block text-right card-footer">
                <a class="btn-wide btn btn-success">Save</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
// import { ResultConstants } from "@/constant/ResultConstant";
import router from "@/router";
// import ThePagination from "@/components/ThePagination";

export default {
  name: "ContributionList",
  components: {
    // ThePagination,
  },
  mixins: [commonHelper],
  data() {
    return {};
  },
  created() {
    // this.getContributionList();
    this.getUserList();
  },
  methods: {
    // getFilter() {
    //   this.filter.page = 1;
    //   this.getUserList();
    // },

  },
};
</script>

<style scoped>
.card {
  margin: 20px;
}
.sort {
  cursor: pointer;
}
.click {
  cursor: pointer;
}
.click :hover {
  color: #d10024;
}
.main-card {
  padding: 0;
  margin: 20px 60px;
}
</style>
