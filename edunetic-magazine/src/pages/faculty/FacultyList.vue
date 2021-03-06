<template>
  <div>
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h2 class="card-title">Faculty List</h2>
                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 150px">
                    <router-link
                      to="/faculties/create"
                      tag="button"
                      class="btn btn-success"
                    >
                      Create New <i class="fas fa-plus fa-fw"></i>
                    </router-link>
                  </div>
                </div>
              </div>
              <!--FILTER SECTION-->
              <div class="card-header">
                <div class="row">
                  <h3 class="card-title">Filter</h3>
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
                      v-model="filter.name"
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
                      v-model="filter.cordinatorname"
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
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
                <table class="table table-hover text-nowrap">
                  <thead>
                    <tr>
                      <th class="sort">Code <i class="fas fa-sort"></i></th>
                      <th class="sort">Name <i class="fas fa-sort"></i></th>
                      <th class="sort">
                        Co-cordinator's name <i class="fas fa-sort"></i>
                      </th>
                      <th>Action <i class="fas fa-sort"></i></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="faculty of list_faculties" :key="faculty.id">
                      <td>{{ faculty.code }}</td>
                      <td>{{ faculty.name }}</td>
                      <td>{{ faculty.cordinator }}</td>
                      <td>
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="showFaculty(faculty.id)"
                        >
                          <b>Update</b>
                        </p>
                        |
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="deleteFaculty(Faculty.id)"
                        >
                          <b>Delete</b>
                        </p>
                        |
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="showStundent(faculty.id)"
                        >
                          <b>Stundent's list</b>
                        </p>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer">
                <div class="row">
                  <div class="col-sm-6">
                    <div>
                      <strong> Per Page: </strong>
                      <select>
                        <option value="10">10</option>
                        <option value="15" selected>15</option>
                        <option value="1">1</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <!--                    <the-pagination v-bind:pagination="list_users" v-on:click.native="getRoleList"></the-pagination>-->
                  </div>
                </div>
              </div>
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
  </div>
</template>

<script>
import axios from "axios";
import { DefaultConstants } from "@/constant/DefaultConstant";
import { UrlConstants } from "@/constant/UrlConstant";
import { ResultConstants } from "@/constant/ResultConstant";
import { RoleConstants } from "@/constant/RoleConstants";
import router from "@/router";
//import ThePagination from "@/components/ThePagination";
export default {
  name: "FacultyList",
  components: {
    //ThePagination
  },
  data() {
    return {
      list_faculties: [],
      list_users: [],
      errors: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 1
        roleId: RoleConstants.Stundent, //default role_id of MarketingCoordinator = 4
      },
    };
  },
  created() {
    this.getFacultyList();
    this.getUserList();
  },
  methods: {
    getFacultyList() {
      axios
        .post(UrlConstants.Faculty + "/filter", this.filter)
        .then((response) => {
          this.list_faculties = response.data;
          console.log(this.list_faculties);
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getUserList() {
      axios
        .post(UrlConstants.User)
        .then((response) => {
          this.list_users = response.data.data;
          console.log(this.list_users);
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    deleteFaculty(faculty_id) {
      axios.get(UrlConstants.Faculty + "/" + faculty_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getRoleList();
        } else {
          if (confirm("Are you sure to delete this faculty ?")) {
            axios
              .delete(UrlConstants.Faculty + "/" + faculty_id)
              .then((res) => {
                if (res.data.code === ResultConstants.Sucess) {
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
      router.push("/faculties/update");
    },
    showStundent(faculty_id) {
      axios.get(UrlConstants.Faculty + "/" + faculty_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("This faculty is null");
          this.getFacultyList();
        } else {
          
          router.push("/faculties/" + faculty_id + "/studentlist");
        }
      });
      router.push("/faculties/studentlist");
    },
    checkStudent() {

    },
    getFilter() {
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
