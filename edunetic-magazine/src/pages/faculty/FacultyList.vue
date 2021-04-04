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
            <h3>Faculty List</h3>
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
            <router-link to="/faculties/create" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Create Faculty
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
                  <label>Name</label>
                  <input
                    class="form-control"
                    type="text"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.facultyName"
                    v-on:keyup="getFilter"
                  />
                </div>
                <div class="form-group">
                  <label>Co-cordinator's name</label>
                  <input
                    class="form-control"
                    type="text"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.managerName"
                    v-on:keyup="getFilter"
                  />
                </div>
                <div class="form-group">
                  <label>Created date</label>
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
            </div>
            <!--/.FILTER SECTION-->
            <div class="table-responsive">
              <table class="mb-0 table">
                <thead>
                  <tr>
                    <th class="sort" v-on:click="getSort('code')">
                      Code <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('faculty_name')">
                      Name <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('manager_name')">
                      Co-cordinator's name <i class="fas fa-sort"></i>
                    </th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="faculty of list_faculties"
                    :key="faculty.faculty_id"
                  >
                    <td>{{ faculty.code }}</td>
                    <td>{{ faculty.facultyName }}</td>
                    <td>{{ faculty.managerName }}</td>
                    <td>
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showFaculty(faculty.facultyId)"
                      >
                        <b>Update</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="deleteFaculty(faculty.facultyId)"
                      >
                        <b>Delete</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showStundent(faculty.facultyId)"
                      >
                        <b>Stundent's list</b>
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
                v-bind:pagination="list_faculties"
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
import { ResultConstants } from "@/constant/ResultConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
import router from "@/router";
import ThePagination from "@/components/ThePagination";
import { commonHelper } from "@/helper/commonHelper";
export default {
  name: "FacultyList",
  components: {
    ThePagination,
  },
  mixins: [commonHelper],
  created() {
    this.getFacultyList();
  },
  methods: {
    async checkFacultyExisted(facultyId) {
      await axios.get(UrlConstants.Faculty + "/" + facultyId).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          this.canModify = false;
        } else {
          this.canModify = true;
        }
      });
    },
    async preCheckFaculty(facultyId) {
      await this.checkFacultyExisted(facultyId);
      if (this.canModify) {
        await this.checkUserExisted("faculty", facultyId);
      }
      this.filter.facultyId = "";
    },
    async deleteFaculty(facultyId) {
      await this.preCheckFaculty(facultyId);
      if (!this.canModify) {
        this.errorAlert("delete", "faculty");
        this.getFacultyList();
      } else {
        await this.confirmAlert("delete", "faculty");
        if (this.confirmResult) {
          axios
            .delete(UrlConstants.Faculty + "/" + facultyId)
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
    async showFaculty(facultyId) {
      await this.checkFacultyExisted(facultyId);
      if (!this.canModify) {
        this.errorAlert("update", "faculty"); //this function is called from commonHelper.js file
        this.getFacultyList();
      } else {
        router.push("/faculties/" + facultyId + "/update");
      }
    },

    showStundent(facultyId) {
      axios.get(UrlConstants.Faculty + "/" + facultyId).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("This faculty is null");
          this.getFacultyList();
        } else {
          this.$cookies.set("facultyStudent", facultyId);
          this.$router.push("/users");
        }
      });
    },
    getFilter() {
      this.filter.page = DefaultConstants.firstPage;
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
  color: #3f6ad8;
}
.select-page {
  padding: 2px 5px;
}
</style>
