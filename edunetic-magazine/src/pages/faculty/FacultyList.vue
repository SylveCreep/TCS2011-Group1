<template>
  <div class="app-main__inner">
    <div
      class="app-page-title"
      style="margin: 0; background-color: #f0f3f5; padding: 5px;"
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
      <div class="col-lg-12" style="padding: 0;">
        <div class="main-card mb-3 card">
          <div class="card-body">
            <!--FILTER SECTION-->
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
                    v-on:keyup="getFacultyList"
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
                    v-on:keyup="getFacultyList"
                  />
                </div>
                <div class="form-group">
                  <label>Co-cordinator's name</label>
                  <input
                    class="form-control"
                    type="text"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.managername"
                    v-on:keyup="getFacultyList"
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
                    v-on:keyup="getFacultyList"
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
                    <th>Action <i class="fas fa-sort"></i></th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="faculty of list_faculties"
                    :key="faculty.faculty_id"
                  >
                    <td>{{ faculty.code }}</td>
                    <td>{{ faculty.faculty_name }}</td>
                    <td>{{ faculty.manager_name }}</td>
                    <td>
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="showFaculty(faculty.faculty_id)"
                      >
                        <b>Update</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="deleteFaculty(faculty.faculty_id)"
                      >
                        <b>Delete</b>
                      </p>
                      |
                      <p
                        class="click"
                        style="display: inline"
                        v-on:click="
                          showStundent(faculty.faculty_id, faculty.faculty_name)
                        "
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
              <select v-on:change="getLimit($event)">
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
import router from "@/router";
import ThePagination from "@/components/ThePagination";
import { commonHelper } from "@/helper/commonHelper";
export default {
  data() {
    return {};
  },
  name: "FacultyList",
  components: {
    ThePagination,
  },
  mixins: [commonHelper],

  methods: {
    deleteFaculty(faculty_id) {
      axios.get(UrlConstants.Faculty + "/" + faculty_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getFacultyList();
        } else {
          if (confirm("Are you sure to delete this faculty ?")) {
            axios
              .delete(UrlConstants.Faculty + "/" + faculty_id)
              .then((res) => {
                if (res.data.code === ResultConstants.Success) {
                  alert("sucess");
                  this.getFacultyList();
                }
                if (res.data.code === ResultConstants.Failure) {
                  alert("error");
                  this.getFacultyList();
                }
              })
              .catch((error) => {
                this.errors = error.data;
              });
          }
        }
      });
    },
    showFaculty(faculty_id) {
      axios.get(UrlConstants.Faculty + "/" + faculty_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getFacultyList();
        } else {
          router.push("/faculties/" + faculty_id + "/update");
        }
      });
    },
    showStundent(faculty_id, faculty_name) {
      axios.get(UrlConstants.Faculty + "/" + faculty_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("This faculty is null");
          this.getFacultyList();
        } else {
          let student = {
            facultyId: faculty_id,
            facultyName: faculty_name,
          };
          console.log(student);
          this.$cookies.set("facultyStudent", student);
          this.$router.push("/users");
        }
      });
    },
    checkStudent() {},
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
