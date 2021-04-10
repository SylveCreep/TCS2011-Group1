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
              <div class="widget-heading">The number of Contributions</div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <span>{{ 100 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-arielle-smile">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading">The number of students</div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <span>{{ 200 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card mb-3 widget-content bg-grow-early">
          <div class="widget-content-wrapper text-white">
            <div class="widget-content-left">
              <div class="widget-heading">The number of magazines</div>
            </div>
            <div class="widget-content-right">
              <div class="widget-numbers_component text-white">
                <span>{{ 300 }}</span>
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
              <label class="select_label">Magazine</label>
              <select
                class="select_form_control"
                id="category_id"
                name="category"
                v-model="filter.magazineId"
                v-on:change="getFilter"
              >
                <option value="" selected>All</option>
                <option
                  v-for="magazine in list_magazines"
                  :key="magazine.id"
                  v-bind:value="magazine.magazineId"
                >
                  {{ magazine.magazineName }}
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
                  <th class="text">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user of list_users" :key="user.id">
                  <td>{{ user.code }}</td>
                  <td>{{ user.fullName }}</td>
                  <td>{{ user.facultyName }}</td>
                  <td>{{ user.totalContribution }}</td>
                  <td>
                    <p
                      class="click"
                      style="display: inline"
                      v-on:click="showUserContribution(user.id)"
                    >
                      <b>Detail</b>
                    </p>
                  </td>
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
import { ResultConstants } from "@/constant/ResultConstant";
import router from "@/router";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  name: "UserList",
  mixins: [commonHelper],
  data() {
    return {
      list_users: [],
    };
  },
  computed: {
    mmUserList() {
      return this.list_users.filter(user => user.roleId !== 1)
    },
  },
  created() {
    this.setStudentList();
    this.checkLoginRoleFilter();
    //These functions are called from commonHelper.js file
    this.getUserList();
    this.getRoleList();
    this.getFacultyList();
  },
  methods: {
    async checkUserExisted(user_id) {
      await axios.get(UrlConstants.User + "/" + user_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          this.canModify = false;
        } else {
          this.canModify = true;
        }
      });
    },
    async showUser(user_id) {
      await this.checkUserExisted(user_id)
        if (!this.canModify) {
          this.errorAlert('update', 'user');
          this.getUserList();
        } else {
          router.push("/users/" + user_id + "/detail");
        }
    },
    async deleteUser(user_id) {
      await this.checkUserExisted(user_id)
        if (!this.canModify) {
          this.errorAlert('delete', 'user');
          this.getUserList();
        }
        else {
          await this.confirmAlert('delete', 'user');
          if (this.confirmResult) {
            axios
              .delete(UrlConstants.User + "/" + user_id)
              .then((res) => {
                  this.successAlert();
                  this.getUserList();
              })
              .catch((error) => {
                this.errors = error.data;
              });
          }
        }
    },
    async showUserContribution(user_id) {
      await this.checkUserExisted(user_id)
        if (!this.canModify) {
          this.errorAlert('show contribution list', 'user');
          this.getUserList();
        } else {
          this.$cookies.set("studentContribution", user_id);
          router.push("/contributions");
        }
    },
    setStudentList() {
      if (this.$cookies.isKey("facultyStudent")) {
        this.filter.facultyId = this.$cookies.get("facultyStudent");
      }
    },
    getFilter() {
      this.filter.page = DefaultConstants.firstPage;
      this.getUserList();
    },
    checkLoginRoleFilter() {
      if (this.loginUser.roleId === DefaultConstants.Role.MarketingCoordinator) {
        this.filter.facultyId = this.loginUser.facultyId;
        this.filter.roleId = DefaultConstants.Role.Student
      }
      if (this.loginUser.roleId === DefaultConstants.Role.MarketingManager) {
        this.filter.roleId = DefaultConstants.Role.Student
      }
    },
    getSort($column) {
      this.getcommonSort($column);
      this.getUserList();
    },
    getLimit(event) {
      this.getcommonLimit(event.target.value);
      this.getUserList();
    },
    changePage(e) {
      this.changecommonPage(e);
      this.getUserList();
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
</style>
