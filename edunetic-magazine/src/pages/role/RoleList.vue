<template>
  <div class="app-main__inner">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-drawer icon-gradient bg-happy-itmeo"> </i>
          </div>
          <div>
            <h3>Role List</h3>
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
            <router-link to="/roles/create" class="btn-shadow btn btn-info">
              <span class="btn-icon-wrapper pr-2 opacity-7">
                <i class="fa fa-business-time fa-w-20"></i>
              </span>
              Create Role
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="main-card mb-3 card">
          <div class="card-body">
            <!--FILTER SECTION-->
            <div class="card-title" style="padding:20px;">
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
            <!--/.FILTER SECTION-->
            <div class="table-responsive">
              <table class="mb-0 table">
                <thead>
                  <tr>
                    <th class="sort" v-on:click="getSort('code')">
                      Code <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort" v-on:click="getSort('name')">
                      Name <i class="fas fa-sort"></i>
                    </th>
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
          </div>
          <div class="card-footer">
            <div class="col-lg-6">
              <strong> Per page: </strong>
              <select v-on:change="getLimit($event)">
                <option value="10">10</option>
                <option value="15" selected>15</option>
                <option value="1">1</option>
              </select>
            </div>
            <div class="col-lg-6">
              <the-pagination
                v-bind:pagination="list_roles"
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
  name: "RoleList",
  components: {
    ThePagination
  },
  mixins: [commonHelper],
  
  methods: {
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
    getSort($column) {
      this.getcommonSort($column);
      this.getRoleList();
    },
    getLimit(event) {
      this.getcommonLimit(event.target.value);
      this.getRoleList();
    },
    changePage(e){
      this.changecommonPage(e);
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