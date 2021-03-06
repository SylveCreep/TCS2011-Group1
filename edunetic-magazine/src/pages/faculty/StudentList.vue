<template>
  <div>
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h2 class="card-title">Student List</h2>
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
                    <label>Full Name</label>
                    <input
                      class="form-control"
                      type="text"
                      placeholder="Search"
                      aria-label="Search"
                      v-model="filter.fullName"
                      v-on:keyup="getUserList"
                    />
                  </div>
                  <div class="form-group">
                    <label>Gender</label>
                    <select
                      class="form-control select2"
                      id="cate_id"
                      name="category"
                      v-model="filter.is_male"
                      v-on:change="getFilter"
                    >
                      <option value="" selected>All</option>
                      <option value="1" selected>Male</option>
                      <option value="0" selected>Female</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Faculty</label>
                    <select
                      class="form-control select2"
                      id="faculty_id"
                      name="faculty"
                      v-model="filter.faculty_id"
                      v-on:change="getFilter()"
                    >
                      <option value="" selected>All</option>
                      <option
                        v-for="faculty in list_faculties"
                        :key="faculty.id"
                        v-bind:value="faculty.id"
                      >
                        {{ faculty.name }}
                      </option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Role</label>
                    <select
                      class="form-control select2"
                      id="role_id"
                      name="role"
                      v-model="filter.role"
                      v-on:change="getFilter()"
                    >
                      <option value="" selected>All</option>
                      <option
                        v-for="role in list_roles"
                        :key="role.id"
                        v-bind:value="role.id"
                      >
                        {{ role.name }}
                      </option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Email</label>
                    <input
                      class="form-control"
                      type="text"
                      placeholder="Search"
                      aria-label="Search"
                      v-model="filter.email"
                      v-on:keyup="getFilter"
                    />
                  </div>
                  <div class="form-group">
                    <label>Date of birth </label>
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
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
                <table class="table table-hover text-nowrap">
                  <thead>
                    <tr>
                      <th class="sort">Code <i class="fas fa-sort"></i></th>
                      <th class="sort" v-on:click="getSort('fullName')">
                        Full name <i class="fas fa-sort"></i>
                      </th>
                      <th class="sort">Faculty <i class="fas fa-sort"></i></th>
                      <th class="sort">Role <i class="fas fa-sort"></i></th>
                      <th class="sort" v-on:click="getSort('email')">
                        Email <i class="fas fa-sort"></i>
                      </th>
                      <th>Action <i class="fas fa-sort"></i></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="user of list_users" :key="user.id">
                      <td>{{ user.code }}</td>
                      <td>{{ user.fullName }}</td>
                      <td>{{ user.facultyName }}</td>
                      <td>{{ user.roleName }}</td>
                      <td>{{ user.email }}</td>
                      <td>
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="showUser(user.id)"
                        >
                          <b>Update</b>
                        </p>
                        |
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="deleteUser(user.id)"
                        >
                          <b>Delete</b>
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
                      <select v-on:change="getLimit($event)">
                        <option value="10">10</option>
                        <option value="15" selected>15</option>
                        <option value="1">1</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <!--                    <the-pagination v-bind:pagination="list_users" v-on:click.native="getUserList"></the-pagination>-->
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
import router from "@/router";
//import ThePagination from "@/components/ThePagination";

export default {
  name: "UserList",
  components: {
    //ThePagination
  },
  data() {
    return {
      list_users: [],
      list_roles: [],
      list_faculties: [],
      errors: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
      },
    };
  },
  created() {
    this.getUserList();
    //this.getRoleList();
    this.getFacultyList();
  },
  methods: {
    getUserList() {
      axios
        .post(UrlConstants.User + "/filter", this.filter)
        .then((response) => {
          this.list_users = response.data.data;
          console.log(this.list_users);
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getRoleList() {
      axios
        .get(UrlConstants.Role)
        .then((response) => {
          this.list_roles = response.data;
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
          this.showError(this.errors);
        });
    },
    getFacultyList() {
      axios
        .get(UrlConstants.Faculty)
        .then((response) => {
          this.list_faculties = response.data;
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
          this.showError(this.errors);
        });
    },
    showUser(user_id) {
      axios.get(UrlConstants.User + "/" + user_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getUserList();
        } else {
          router.push("/users/" + user_id + "/update");
        }
      });
    },
    deleteUser(user_id) {
      axios.get(UrlConstants.User + "/" + user_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getUserList();
        } else {
          if (confirm("are you sure to delete this user ?")) {
            axios
              .delete(UrlConstants.User + "/" + user_id)
              .then((res) => {
                if (res.data.code === ResultConstants.Success) {
                  alert("success");
                  console.log(res.data)
                  this.getUserList();
                }
                if (res.data.code === ResultConstants.Failure) {
                  alert("error");
                  this.getUserList();
                }
              })
              .catch((error) => {
                this.errors = error.data;
              });
          }
        }
      });
    },
    getFilter() {
      this.getUserList();
    },
    getSort($column) {
      if (this.filter.sort === "asc") {
        this.filter.sort = "desc";
      } else if (this.filter.sort === "desc") {
        this.filter.sort = "asc";
      }
      this.filter.column = $column;
      this.getUserList();
    },
    getLimit(event) {
      this.filter.limit = event.target.value;
      this.filter.page = 1;
      this.getUserList();
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