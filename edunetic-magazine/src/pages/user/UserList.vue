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
            <h3>User List</h3>
          </div>
        </div>
        <div class="page-title-actions">
          <div class="d-inline-block dropdown" v-if="loginUser.roleId === 1">
            <!-- Only admin can create user -->
            <router-link to="/users/create" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Create User
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
                  <label>Full Name</label>
                  <input
                    class="form-control"
                    type="text"
                    placeholder="Search"
                    aria-label="Search"
                    v-model="filter.fullName"
                    v-on:keyup="getFilter"
                  />
                </div>
                <div class="form-group">
                  <label>Gender</label>
                  <select
                    class="form-control select2"
                    id="cate_id"
                    name="category"
                    v-model="filter.gender"
                    v-on:change="getFilter"
                  >
                    <option value="" selected>All</option>
                    <option value="1" selected>Male</option>
                    <option value="0" selected>Female</option>
                  </select>
                </div>
                <div class="form-group" v-if="loginUser.roleId !== 3">
                  <!-- coordinatio cannot filter by faculty-->
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
                <div class="form-group" v-if="loginUser.roleId === 1">
                  <!-- Only Admin can filter by role-->
                  <label>Role</label>
                  <select
                    class="form-control select2"
                    id="role_id"
                    name="role"
                    v-model="filter.roleId"
                    v-on:change="getFilter"
                  >
                    <option value="" selected="selected">All</option>
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
                    <th class="sort" v-on:click="getSort('full_name')">
                      Full name <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('faculty_id')">
                      Faculty <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('role_id')">
                      Role <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('email')">
                      Email <i class="fas fa-sort"></i>
                    </th>
                    <th>Action</th>
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
                        <b v-if="loginUser.roleId === 1">Update</b>
                        <b v-else>Detail</b>
                      </p>
                      |
                      <!--Only admin can delete user-->
                      <p
                        v-if="loginUser.roleId === 1"
                        class="click"
                        style="display: inline"
                        v-on:click="deleteUser(user.id)"
                      >
                        <b>Delete</b>
                      </p>
                      <!--Only marketing manager can view contribution list of student-->
                      <p
                        v-else
                        class="click"
                        style="display: inline"
                        v-on:click="showUserContribution(user.id)"
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
              <strong> Items per page: </strong>
              <select class="select-page" v-on:change="getLimit($event)">
                <option value="10">10</option>
                <option value="15" selected>15</option>
                <option value="1">1</option>
              </select>
            </div>
            <div class="col-lg-6">
              <the-pagination
                v-bind:pagination="list_users"
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
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import { ResultConstants } from "@/constant/ResultConstant";
import router from "@/router";
import ThePagination from "@/components/ThePagination";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  name: "UserList",
  components: {
    ThePagination,
  },
  mixins: [commonHelper],
  data() {
    return {
      list_users: [],
    };
  },
  computed: {
    mmUserList() {
      return this.list_users.filter((user) => user.roleId !== 1);
    },
  },
  destroyed() {
    this.$cookies.remove("facultyStudent");
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
      await this.checkUserExisted(user_id);
      if (!this.canModify) {
        this.errorAlert("update", "user");
        this.getUserList();
      } else {
        router.push("/users/" + user_id + "/detail");
      }
    },
    async deleteUser(user_id) {
      await this.checkUserExisted(user_id);
      if (!this.canModify) {
        this.errorAlert("delete", "user");
        this.getUserList();
      } else {
        await this.confirmAlert("delete", "user");
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
      await this.checkUserExisted(user_id);
      if (!this.canModify) {
        this.errorAlert("show contribution list", "user");
        this.getUserList();
      } else {
        this.getContributionList();
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
      if (
        this.loginUser.roleId === DefaultConstants.Role.MarketingCoordinator
      ) {
        this.filter.facultyId = this.loginUser.facultyId;
        this.filter.roleId = DefaultConstants.Role.Student;
      }
      if (this.loginUser.roleId === DefaultConstants.Role.MarketingManager) {
        this.filter.roleId = DefaultConstants.Role.Student;
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
  color: #7399f7;
}
.select-page {
  padding: 2px 5px;
}
</style>
