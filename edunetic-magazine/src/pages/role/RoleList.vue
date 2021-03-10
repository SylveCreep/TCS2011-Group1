<template>
  <div>
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h2 class="card-title">Role List</h2>
                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 150px">
                    <router-link
                      to="/roles/create"
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
                      v-on:keyup="getRoleList"
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
                      v-on:keyup="getRoleList"
                    />
                  </div>
                  <div class="form-group">
                    <label>Date created </label>
                    <input
                      class="form-control"
                      type="date"
                      placeholder="Search"
                      aria-label="Search"
                      v-model="filter.date_of_birth"
                      v-on:keyup="getRoleList"
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
                      <th>Action <i class="fas fa-sort"></i></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="role of list_roles" :key="role.id">
                      <td>{{ role.code }}</td>
                      <td>{{ role.name }}</td>
                      <td>
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="showRole(role.id)"
                        >
                          <b>Update</b>
                        </p>
                        |
                        <p
                          class="click"
                          style="display: inline"
                          v-on:click="deleteRole(role.id)"
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
                    <the-pagination v-bind:pagination="list_roles" v-on:currentPage="changePage"></the-pagination>
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
import ThePagination from "@/components/ThePagination";
export default {
  name: "RoleList",
  components: {
    ThePagination
  },
  data() {
    return {
      list_roles: [],
      errors: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 1
      },
    };
  },
  created() {
    this.getRoleList();
  },
  methods: {
    getRoleList() {
      axios
        .post(UrlConstants.Role + "/filter", this.filter)
        .then((response) => {
          this.list_roles = response.data.data;
          this.list_roles.currentPage = this.filter.page;
          this.list_roles.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    deleteRole(role_id) {
      axios.get(UrlConstants.Role + "/" + role_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getRoleList();
        } else {
          if (confirm("Are you sure to delete this role ?")) {
            axios
              .delete(UrlConstants.Role + "/" + role_id)
              .then((res) => {
                if (res.data.code === ResultConstants.Success) {
                  alert("sucess");
                  this.getRoleList();
                }
                if (res.data.code === ResultConstants.Failure) {
                  alert("error");
                  this.getRoleList();
                }
              })
              .catch((error) => {
                this.errors = error.data;
              });
          }
        }
      });
    },
    showRole(role_id) {
      axios.get(UrlConstants.Role + "/" + role_id).then((response) => {
        if (response.data.code === ResultConstants.Failure) {
          alert("error");
          this.getRoleList();
        } else {
          router.push("/roles/" + role_id + "/update");
        }
      });
    },
    getLimit(event) {
      this.filter.limit = event.target.value;
      this.filter.page = 1;
      this.getRoleList();
    },
    changePage(e){
      this.filter.page = e;
      this.getRoleList();
    }
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
