<template>
  <div class="app-main__inner">
    <div
      class="app-page-title"
      style="margin: 0; background-color: #f0f3f5; padding: 5px"
    >
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-drawer icon-gradient bg-happy-itmeo"> </i>
          </div>
          <div>
            <h3>Magazine List</h3>
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
          <div class="d-inline-block dropdown" v-if="loginUser.roleId === 2">
            <!--Only MarketingCoordinator can access this route -->
            <router-link to="/magazines/create" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Create Magazine
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12" style="padding: 0">
        <div class="main-card mb-3 card">
          <div class="card-body">
            <!--FILTER SECTION-->
            <div class="card-title" style="padding: 20px 20px 0">
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
                  <label>Theme</label>
                  <input
                    class="form-control"
                    type="text"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.theme"
                    v-on:keyup="getFilter"
                  />
                </div>
                <div class="form-group">
                  <label>Created Date</label>
                  <input
                    class="form-control"
                    type="date"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.currentDate"
                    v-on:keyup="getFilter"
                  />
                </div>
                <!-- <div class="form-group">
                  <label>Closed At</label>
                  <input
                    class="form-control"
                    type="date"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.closeAt"
                    v-on:keyup="getFilter"
                  />
                </div> -->
                <!-- <div class="form-group">
                  <label>Published At</label>
                  <input
                    class="form-control"
                    type="date"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.publishedAt"
                    v-on:keyup="getFilter"
                  />
                </div> -->
              </div>
            </div>
            <ul
              class="body-tabs body-tabs-layout tabs-animated body-tabs-animated nav"
            >
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
            <!--/.FILTER SECTION-->
            <div class="table-responsive">
              <table class="mb-0 table">
                <thead>
                  <tr>
                    <th class="sort" v-on:click="getSort('code')">
                      Code <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('theme')">
                      Theme <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('created_at')">
                      Open At <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('close_at')">
                      Finished At <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('published_at')">
                      Published At <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-if="status === 3" v-on:click="getSort('published_at')">
                      Closed At<i class="fas fa-sort"></i>
                    </th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <!---->
                  <tr v-for="magazine of list_magazines" :key="magazine.id">
                    <td>{{ magazine.code }}</td>
                    <td>{{ magazine.theme }}</td>
                    <td>{{ magazine.created_at | formatDate }}</td>
                    <td>{{ magazine.finished_at | formatDate }}</td>
                    <td>{{ magazine.published_at | formatDate }}</td>
                    <td>
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showMagazine(magazine.id)"
                        v-if="loginUser.roleId === 2 && status === 0"
                      >
                        <b>Update | </b>
                        <!--Only MarketingCoordinator can access this route -->
                      </p>
                      <!-- <p
                        class="click"
                        style="display: inline"
                        v-on:click="deleteMagazine(magazine.id)"
                        v-if="loginUser.roleId === 2 && status === 0"
                      >
                        <b>Delete | </b>
                      </p> -->
                      <p
                        class="click"
                        style="display: inline"
                        v-if="loginUser.roleId === 2 && status === 2"
                      >
                        <b>Close | </b>
                      </p>
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showContribution(magazine.id)"
                      >
                        <b>Contribution list</b>
                      </p>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="card-footer">
            <div class="col-lg-6">
              <strong> Per Page: </strong>
              <select class="select-page" v-on:change="getLimit($event)">
                <option value="10">10</option>
                <option value="15" selected>15</option>
                <option value="1">1</option>
              </select>
            </div>
            <div class="col-lg-6">
              <the-pagination
                v-bind:pagination="list_magazines"
                v-on:currentPage="changePage"
              ></the-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
import { ResultConstants } from "@/constant/ResultConstant";
import router from "@/router";
import ThePagination from "@/components/ThePagination";
import { commonHelper } from "@/helper/commonHelper";
export default {
  name: "MagazineList",
  components: {
    ThePagination,
  },
  mixins: [commonHelper],
  data() {
    return {
      list_statuses: DefaultConstants.MagazineStatuses,
      list_magazines: [],
      status: 0,
    };
  },
  mounted() {
    document.querySelector("#tab-0").click(); //default click to tab 0
  },
  created() {
    this.filter.status = 0;
    this.getMagazineList();
  },
  methods: {
    async checkMagazineExisted(magazine_id) {
      await axios
        .get(UrlConstants.Magazine + "/" + magazine_id)
        .then((response) => {
          if (response.data.code === ResultConstants.Failure) {
            this.canModify = false;
          } else {
            this.canModify = true;
          }
        });
    },
    async showMagazine(magazine_id) {
      await this.checkMagazineExisted(magazine_id);
      if (!this.canModify) {
        this.errorAlert("update", "magazine"); //this function is called from commonHelper.js file
        this.getMagazineList();
      } else {
        router.push("/magazines/" + magazine_id + "/update");
      }
    },
    // async deleteMagazine(magazine_id) {
    //   await this.checkMagazineExisted(magazine_id);
    //   if (!this.canModify) {
    //     this.errorAlert("delete", "magazine");
    //     this.getMagazineList();
    //   } else {
    //     await this.confirmAlert("delete", "magazine");
    //     if (this.confirmResult) {
    //       axios
    //         .delete(UrlConstants.Magazine + "/" + magazine_id)
    //         .then((res) => {
    //           this.successAlert(); //this function is called from commonHelper.js file
    //           this.getMagazineList();
    //         })
    //         .catch((error) => {
    //           this.errors = error.data;
    //         });
    //     }
    //   }
    // },
    showContribution(magazine_id) {
      axios.get(UrlConstants.Magazine + "/" + magazine_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("This magazine is null");
          this.getMagazineList();
        } else {
          this.$cookies.set("magazineContribution", magazine_id);
          this.$router.push("/contributions");
        }
      });
    },
    getFilter() {
      this.filter.page = DefaultConstants.firstPage;
      this.getMagazineList();
    },
    getSort($column) {
      this.getcommonSort($column);
      this.getMagazineList();
    },
    getLimit(event) {
      this.getcommonLimit(event.target.value);
      this.getMagazineList();
    },
    getStatus(status) {
      this.filter.status = status;
      this.status = status;
      this.getMagazineList();
    },
    changePage(e) {
      this.changecommonPage(e);
      this.getMagazineList();
    },
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
.select-page {
  padding: 2px 5px;
}
</style>
