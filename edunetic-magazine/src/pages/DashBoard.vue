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
      </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-midnight-bloom">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading">
                <h5><b>The number of Contributions:</b></h5>
              </div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h5>{{ totalValue.totalContributions }}</h5>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-arielle-smile">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading"><h5><b>The number of Students:</b></h5></div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h5>{{ totalValue.totalStudents }}</h5>
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
                <h5><b>The number of magazines:</b></h5>
              </div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <h5>{{ totalValue.totalMagazines }}</h5>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row" v-if="loginUser.roleId == 1 || loginUser.roleId == 2 || loginUser.roleId == 3">
      <div class="col-md-12 col-lg-6">
        <div class="mb-3 card">
          <div class="card-header-tab card-header-tab-animation card-header">
            <div class="card-header-title">
              <i class="header-icon lnr-apartment icon-gradient bg-love-kiss">
              </i>
              Total students of each faculty
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
                     <pie-chart></pie-chart>
                     <!--Pie Chart for MM & MC -->
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-12 col-lg-6">
        <div class="mb-3 card">
          <div class="card-header-tab card-header-tab-animation card-header">
            <div class="card-header-title">
              <i class="header-icon lnr-apartment icon-gradient bg-love-kiss">
              </i>
              Total contributions of each faculty
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
                     <bar-chart></bar-chart>
                     <!--Bar Chart for MM & MC -->
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-12 col-lg-6">
        <div class="mb-3 card">
          <div class="card-header-tab card-header-tab-animation card-header">
            <div class="card-header-title">
              <i class="header-icon lnr-apartment icon-gradient bg-love-kiss">
              </i>
              Total contributions of each magaizne
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
                     <line-chart></line-chart>
                     <!--Bar Chart for MM & MC -->
                    </div>
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
              <label class="select_label">Magazine List</label>
              <select
                class="select_form_control"
                id="magazine_id"
                name="magazine"
                v-model="filter.id"
                v-on:change="getFilter"
              >
                <option value="0" selected>All</option>
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
                <tr v-for="topStudent of list_topStudents" :key="topStudent.id">
                  <td>{{ topStudent.code }}</td>
                  <td>{{ topStudent.studentName }}</td>
                  <td>{{ topStudent.facultyName }}</td>
                  <td>{{ topStudent.totalContribution }}</td>
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
              <label class="select_label">Magazine List</label>
              <select
                class="select_form_control"
                id="magazine_id"
                name="category"
                v-model="filterContribution.id"
                v-on:change="getFilterContribution"
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
import PieChart from "@/components/chart/PieChart"
import BarChart from "@/components/chart/BarChart"
import LineChart from '../components/chart/LineChart.vue';


export default {
  name: "UserList",
  mixins: [commonHelper],
  components :{
    PieChart,
    BarChart,
    LineChart
  },
  data() {
    return {
      list_users: [],
      totalValue: {},
      list_topStudents: {},
      list_contributions: {},
      list_magazines: [],
      filterContribution: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
      },
      filter: {
        status: DefaultConstants.MagazineStatuses.Opening,
      },
    };
  },
  created() {
    this.filter.status = 0
    // this.filterContribution.magazineId = 1; // selected the magazine have ID = 1
    this.getTotalValue();
    this.getMagazineListDashboard();
    this.getContributionHasNoComment();
    this.getTopStudentByMagazine(0); // selected the top 5 student has most contribution of the univercity
  },
  methods: {
    getMagazineListDashboard() {
      axios
        .post(UrlConstants.Magazine + "/filter", this.filter)
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
    getTopStudentByMagazine(magazine_id) {
      if (magazine_id == 0) {
        axios
          .get(UrlConstants.BaseUrl + "/" + "getTopStudent")
          .then((response) => {
            this.list_topStudents = response.data.data;
          })
          .catch((error) => {
            this.errors = error.response.data;
          });
      } else {
        let url = "/getTopStudent/?magazineId=";
        axios
          .get(UrlConstants.BaseUrl + url + magazine_id)
          .then((response) => {
            this.list_topStudents = response.data.data;
          })
          .catch((error) => {
            this.errors = error.response.data;
          });
      }
    },
    getContributionHasNoComment() {
      let url = "/getContributionsHasNoComment";
      axios
        .post(UrlConstants.Contribution + url, this.filterContribution)
        .then((response) => {
          this.list_contributions = response.data.data.list;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getFilter() {
      let magazine_id = this.filter.id;
      this.getTopStudentByMagazine(magazine_id);
    },
    getFilterContribution() {
      this.filterContribution.magazineId = this.filterContribution.id;
      this.getContributionHasNoComment();
    },
  },
};
</script>

<style>
.widget-numbers_component {
  margin-left: 10px;
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
#magazine_id {
  padding: 5px;
  margin-left: 10px;
}
.click {
  cursor: pointer;
}
.click :hover {
  color: #7399f7 !important;
}
</style>
