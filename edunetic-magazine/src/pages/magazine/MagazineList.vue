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
          <div class="d-inline-block dropdown">
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
                  <label>Closure Date</label>
                  <input
                    class="form-control"
                    type="date"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.closure"
                    v-on:keyup="getFilter"
                  />
                </div>
                <div class="form-group">
                  <label>Published Date</label>
                  <input
                    class="form-control"
                    type="date"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.published"
                    v-on:keyup="getFilter"
                  />
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
                    <th class="sort" v-on:click="getSort('published')">
                      Published Date <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('closure')">
                      Closure Date <i class="fas fa-sort"></i>
                    </th>
                    <th>Action </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="magazine of list_magazines"
                    :key="magazine.magazine_id"
                  >
                    <td>{{ magazine.magazineCode }}</td>
                    <td>{{ magazine.magazineTheme }}</td>
                    <td>{{ magazine.magazinePublished }}</td>
                    <td>{{ magazine.magazineClosure }}</td>
                    <td>
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showMagazine(magazine.magazineId)"
                      >
                        <b>Update</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="closeMagazine(magazine.magazineId)"
                      >
                        <b>Close</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="deleteMagazine(magazine.magazineId)"
                      >
                        <b>Close</b>
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
              <select v-on:change="getLimit($event)">
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
  data(){
    return{
      list_statuses: DefaultConstants.MagazineStatuses,
      list_magazines:[]
    };
  },
  mounted() {
    document.querySelector("#tab-1").click(); //default click to tab 1
  },
  created() {
    this.getMagazineList();
  },
  methods:{
    async checkMagazineExisted(magazineId){
      await axios.get(UrlConstants.Magazine + "/" + magazineId)
      .then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          this.canModify = false;
        } else {
          this.canModify = true;
        }
      });
    },
    async showMagazine(magazineId){
      await this.checkFacultyExisted(magazineId);
      if (!this.canModify) {
        this.errorAlert("update", "magazine"); //this function is called from commonHelper.js file
        this.getFacultyList();
      } else {
        router.push("/magazines/" + magazineId + "/update");
      }
    },
    closeMagazine(){

    },
    async deleteMagazine(magazineId){
      await this.preCheckFaculty(magazineId);
      if (!this.canModify) {
        this.errorAlert("delete", "magazine");
        this.getFacultyList();
      } else {
        await this.confirmAlert("delete", "magazine");
        if (this.confirmResult) {
          axios
            .delete(UrlConstants.Magazine + "/" + magazineId)
            .then((res) => {
              this.successAlert(); //this function is called from commonHelper.js file
              this.getFacultyList();
            })
            .catch((error) => {
              this.errors = error.data;
            });
        }
      }
    },
    getFilter() {
      this.filter.page = 1;
      this.getFacultyList();
    },
    getSort($column) {
      this.getcommonSort($column);
      this.getFacultyList();
    },
    getLimit(event) {
      this.getcommonLimit(event.target.value);
      this.getFacultyList();
    },
    changePage(e) {
      this.changecommonPage(e);
      this.getFacultyList();
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
</style>
