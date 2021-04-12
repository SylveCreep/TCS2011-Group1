<template>
  <div class="app-main__inner">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-car icon-gradient bg-mean-fruit"> </i>
          </div>
          <div>
            Analytics Dashboard
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
            <button
              type="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
              class="btn-shadow dropdown-toggle btn btn-info"
            >
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Buttons
            </button>
            <div
              tabindex="-1"
              role="menu"
              aria-hidden="true"
              class="dropdown-menu dropdown-menu-right"
            >
              <ul class="nav flex-column">
                <li class="nav-item">
                  <a href="javascript:void(0);" class="nav-link">
                    <i class="nav-link-icon lnr-inbox"></i>
                    <span>
                      Inbox
                    </span>
                    <div class="ml-auto badge badge-pill badge-secondary">
                      86
                    </div>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="javascript:void(0);" class="nav-link">
                    <i class="nav-link-icon lnr-book"></i>
                    <span>
                      Book
                    </span>
                    <div class="ml-auto badge badge-pill badge-danger">5</div>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="javascript:void(0);" class="nav-link">
                    <i class="nav-link-icon lnr-picture"></i>
                    <span>
                      Picture
                    </span>
                  </a>
                </li>
                <li class="nav-item">
                  <a
                    disabled=""
                    href="javascript:void(0);"
                    class="nav-link disabled"
                  >
                    <i class="nav-link-icon lnr-file-empty"></i>
                    <span>
                      File Disabled
                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-midnight-bloom">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading">
                <h3>The number of Contributions:</h3>
              </div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h3>{{ totalValue.totalContributions }}</h3>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-arielle-smile">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading"><h3>The number of students:</h3></div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h3>{{ totalValue.totalStudents }}</h3>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-grow-early">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading">
                <h3>The number of magazines:</h3>
              </div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h3>{{ totalValue.totalMagazines }}</h3>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12 col-lg-6">
        <div class="mb-3 card">
          <div class="card-header-tab card-header-tab-animation card-header">
            <div class="card-header-title">
              <i class="header-icon lnr-apartment icon-gradient bg-love-kiss">
              </i>
              Sales Report
            </div>
          </div>
          <div class="card-body">
            <div class="tab-content">
              <div class="tab-pane fade show active" id="tabs-eg-77">
                <div
                  class="card mb-3 widget-chart widget-chart2 text-left w-100"
                >
                  <div class="widget-chat-wrapper-outer">
                    <div
                      class="widget-chart-wrapper widget-chart-wrapper-lg opacity-10 m-0"
                    >
                      <div
                        class="chartjs-size-monitor"
                        style="position: absolute; inset: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
                      >
                        <div
                          class="chartjs-size-monitor-expand"
                          style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"
                        >
                          <div
                            style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"
                          ></div>
                        </div>
                        <div
                          class="chartjs-size-monitor-shrink"
                          style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"
                        >
                          <div
                            style="position:absolute;width:200%;height:200%;left:0; top:0"
                          ></div>
                        </div>
                      </div>
                      <canvas
                        id="canvas"
                        width="418"
                        height="209"
                        class="chartjs-render-monitor"
                        style="display: block; width: 418px; height: 209px;"
                      ></canvas>
                    </div>
                  </div>
                </div>
                <h6
                  class="text-muted text-uppercase font-size-md opacity-5 font-weight-normal"
                >
                  Top Authors
                </h6>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-12 col-lg-6">
        <div class="mb-3 card">
          <div class="card-header-tab card-header">
            <div class="card-header-title">
              <i class="header-icon lnr-rocket icon-gradient bg-tempting-azure">
              </i>
              Bandwidth Reports
            </div>
          </div>
          <div class="tab-content">
            <div class="tab-pane fade active show" id="tab-eg-55">
              <div class="widget-chart p-3">
                <div style="height: 350px">
                  <div
                    class="chartjs-size-monitor"
                    style="position: absolute; inset: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
                  >
                    <div
                      class="chartjs-size-monitor-expand"
                      style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"
                    >
                      <div
                        style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"
                      ></div>
                    </div>
                    <div
                      class="chartjs-size-monitor-shrink"
                      style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"
                    >
                      <div
                        style="position:absolute;width:200%;height:200%;left:0; top:0"
                      ></div>
                    </div>
                  </div>
                  <canvas
                    id="line-chart"
                    width="426"
                    height="350"
                    class="chartjs-render-monitor"
                    style="display: block; width: 426px; height: 350px;"
                  ></canvas>
                </div>
                <div class="widget-chart-content text-center mt-5">
                  <div class="widget-description mt-0 text-warning">
                    <i class="fa fa-arrow-left"></i>
                    <span class="pl-1">175.5%</span>
                    <span class="text-muted opacity-8 pl-1"
                      >increased server resources</span
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="main-card mb-3 card">
          <div class="card-header">
            Top 5 students most contribution
            <div class="btn-actions-pane-right">
              <label
                class="label click"
                style="margin-right: 20px;"
                v-on:click="getTopStudentAll"
                >Total Magazine</label
              >
              <label class="select_label">Magazine</label>
              <select
                class="select_form_control"
                id="magazine_id"
                name="magazine"
                v-model="filter.id"
                v-on:change="getFilter"
              >
                <option
                  v-for="magazine in list_magazines"
                  :key="magazine.id"
                  v-bind:value="magazine.id"
                >
                  {{ magazine.theme }}
                </option>
                <!-- select option-->
              </select>
            </div>
          </div>
          <div class="table-responsive">
            <table
              class="align-middle mb-0 table table-borderless table-striped table-hover"
            >
              <thead>
                <tr>
                  <th class="text">Code</th>
                  <th class="text">Student's Name</th>
                  <th class="text">Faculty</th>
                  <th class="text">Total Contribution</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="topStudentAll of list_topStudentAlls"
                  :key="topStudentAll.id"
                >
                  <td>{{ topStudentAll.code }}</td>
                  <td>{{ topStudentAll.studentName }}</td>
                  <td>{{ topStudentAll.facultyName }}</td>
                  <td>{{ topStudentAll.totalContribution }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div
          v-if="loginUser.roleId === 2 || loginUser.roleId === 3"
          class="main-card mb-3 card"
        >
          <div class="card-header">
            Contributions have no comment on each magazine
            <div class="btn-actions-pane-right">
              <label class="select_label">Magazine</label>
              <select
                class="select_form_control"
                id="category_id"
                name="category"
              >
                <option selected>All</option>
                <option
                  v-for="magazine in list_magazines"
                  :key="magazine.id"
                  v-bind:value="magazine.magazineId"
                >
                  {{ magazine.theme }}
                </option>
                <!-- select option-->
              </select>
            </div>
          </div>
          <div class="table-responsive">
            <table
              class="align-middle mb-0 table table-borderless table-striped table-hover"
            >
              <thead>
                <tr>
                  <th class="text">Code</th>
                  <th class="text">Co-codinator</th>
                  <th class="text">Faculty</th>
                  <th class="text">Student's Name</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="contribution of list_contributions"
                  :key="contribution.id"
                >
                  <td>{{ contribution.code }}</td>
                  <td>{{ contribution.totalContribution }}</td>
                  <td>{{ contribution.facultyName }}</td>
                  <td>{{ contribution.studentName }}</td>
                </tr>
              </tbody>
            </table>
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
import router from "@/router";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  name: "UserList",
  mixins: [commonHelper],
  data() {
    return {
      list_users: [],
      totalValue: {},
      list_topStudentAlls: {},
      list_contributions: {},
      list_magazines: [],
      filerDashboard: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
        status: 0,
      },
    };
  },
  // computed: {
  //   mmUserList() {
  //     return this.list_users.filter(user => user.roleId !== 1)
  //   },
  // },
  created() {
    //These functions are called from commonHelper.js file
    // this.getUserList();
    // this.getRoleList();
    // this.getFacultyList();
    this.getTotalValue();
    this.getMagazineListDashboard();
    this.getTopStudentAll();
  },
  methods: {
    getMagazineListDashboard() {
      axios
        .post(UrlConstants.Magazine + "/filter", this.filerDashboard)
        .then((response) => {
          this.list_magazines = response.data.data;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getTotalValue() {
      axios
        .get(UrlConstants.BaseUrl + "/" + "totalvalues")
        .then((response) => {
          this.totalValue = response.data.data;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getTopStudentAll() {
      axios
        .get(UrlConstants.BaseUrl + "/" + "getTopStudent")
        .then((response) => {
          this.list_topStudentAlls = response.data.data;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getTopStudentByMagazine(magazine_id) {
      let url = "/getContributionsByMagazineId?magazineId=";
      axios
        .get(UrlConstants.Contribution + url + magazine_id)
        .then((r) => {
          this.list_topStudentAlls = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getFilter() {
      let magazine_id = this.filter.id;
      this.getTopStudentByMagazine(magazine_id);
    },
  },
};
</script>

<style>
.widget-numbers_component {
  margin-left: 5px;
}
.click {
  cursor: pointer;
}
.click :hover {
  color: #7399f7;
}
h3 {
  font-weight: bold;
}
#category_id {
  padding: 2px;
}
.click {
  cursor: pointer;
}
.click :hover {
  color: #7399f7 !important;
}
</style>
