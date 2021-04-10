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
          <div class="d-inline-block dropdown" v-if="loginUser.roleId === 4">
            <!--Only student can access this route -->
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
          <div class="d-inline-block dropdown" v-if="loginUser.roleId === 2">
            <!--Only MM can access this route -->
            <button
              class="btn-shadow btn btn-info"
              v-if="this.cookiesModified"
              v-on:click="showAllContributionList"
              style="margin-right: 10px"
            >
              Show all contributions list
            </button>
          </div>
          <div
            class="d-inline-block dropdown"
            v-if="loginUser.roleId === 2 || loginUser.roleId === 3"
          >
            <!--Only MM & MC can access this route -->
            <button
              class="btn-shadow btn btn-info"
              v-on:click="downloadAllContribution"
            >
              Download all contributions
            </button>
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
          v-on:click="getStatus(status)"
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
        <div class="form-group" v-if="loginUser.roleId !== 4">
          <!-- Role student can not use this filter-->
          <label>Student Name</label>
          <input
            class="form-control"
            type="text"
            placeholder="Search"
            aria-label="Search"
            v-model="filter.studentName"
            v-on:keyup="getFilter"
          />
        </div>
        <div class="form-group" v-if="loginUser.roleId === 2">
          <!-- only MM can use this filter-->
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
            v-model="filter.createdAt"
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
          <div
            class="col-md-4"
            v-for="contribution of list_contributions"
            :key="contribution.id"
          >
            <div class="main-card mb-3 card">
              <div class="card-header">
                <i class="header-icon lnr-license icon-gradient bg-plum-plate">
                </i>
                code: {{ contribution.code }}
              </div>
              <div class="card-body">
                <p><b>Student Name:</b> {{ contribution.studentName }}</p>
                <p><b>Faculty:</b> {{ contribution.facultyName }}</p>
                <p>
                  <b>Submit Date:</b> {{ contribution.createdAt | formatDate }}
                </p>
                <p v-if="status === 1">
                  <b>Approved by:</b> {{ contribution.checkedByName }}
                </p>
                <p v-if="status === 2">
                  <b>Denied by:</b> {{ contribution.checkedByName }}
                </p>
              </div>
              <div class="d-block text-right card-footer">
                <a
                  class="btn btn-primary contribution-detail"
                  v-on:click="showDetail(contribution.id)"
                  >Detail</a
                >
                <a
                  class="btn btn-danger contribution-delete"
                  v-on:click="deleteContribution(contribution.id)"
                  v-if="loginUser.roleId === 4 && magazine.status === 0"
                  >Delete</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <strong> Items per page: </strong>
          <select class="select-page" v-on:change="getLimit($event)">
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
import FileSaver from "file-saver";
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
      magazine: {},
      cookiesModified: false,
    };
  },
  mounted() {
    document.querySelector("#tab-0").click(); //default click to tab 1
  },
  created() {
    this.filter.status = 0;
    this.setStudentContribution();
    this.checkMagazine();
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
    async deleteContribution(contribution_id) {
      await this.confirmAlert("delete", "contribution");
      if (this.confirmResult) {
        axios
          .delete(UrlConstants.Contribution + "/" + contribution_id)
          .then((res) => {
            this.successAlert();
            this.getContributionList();
          })
          .catch((error) => {
            this.errors = error.data;
          });
      }
    },
    setStudentContribution() {
      if (this.$cookies.isKey("studentContribution")) {
        this.filter.studentId = this.$cookies.get("studentContribution");
        this.cookiesModified = true;
      }
    },
    showAllContributionList() {
      if (this.$cookies.isKey("studentContribution")) {
        this.$cookies.remove("studentContribution");
        delete this.filter.studentId;
        this.cookiesModified = false;
        this.getContributionList();
      }
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
      this.filter.status = status;
      this.getContributionList();
    },
    checkMagazine() {
      if (this.$cookies.isKey("magazine")) {
        this.magazine = this.$cookies.get("magazine");
        this.filter.magazineId = this.magazine.id;
      }
    },
    downloadAllContribution() {
      axios
        .get(
          UrlConstants.Contribution +
            "/download?magazineId=" +
            this.magazine.id,
          { responseType: "blob" }
        )
        .then((r) => {
          FileSaver.saveAs(r.data, "contribution.zip");
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
  },
};
</script>
<style scoped>
.contribution-detail {
  color: white !important;
}
.contribution-delete {
  margin-left: 10px;
  color: white !important;
}
.card-body {
  padding-left: 2rem !important;
}
.row .card-footer {
  background-color: #f0f3f5;
}
.select-page {
  padding: 2px 5px;
}
</style>
