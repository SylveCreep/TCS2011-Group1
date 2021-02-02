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
                  <div class="input-group input-group-sm" style="width: 150px;">
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
                        <router-link to="/roles/update"
                          ><p class="click" style="display: inline">
                            <b>Update</b>
                          </p>
                          |</router-link
                        >
                        <router-link to="/roles"
                          ><p class="click" style="display: inline">
                            <b v-on:click="deleteRole(role.id)">Delete</b>
                          </p></router-link
                        >
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
                        <option value="10" selected>10</option>
                        <option value="15">15</option>
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
//import ThePagination from "@/components/ThePagination";
export default {
  name: "RoleList",
  components: {
    //ThePagination
  },
  data() {
    return {
      column: DefaultConstants.column, //default column = 'name'
      sort: DefaultConstants.sort, //default sort = 'asc'
      limit: DefaultConstants.limit, //default limit = 15
      list_roles: [],
      errors: [],
    };
  },
  created() {
    this.getRoleList();
  },
  methods: {
    getUrl() {
      return (
        UrlConstants.Role + '?column=' + this.column
                          + '&limit=' + this.limit
                          + '&sort=' + this.sort
      );
    },
    getRoleList() {
      axios
        .get("https://601956c3fa0b1f0017acce88.mockapi.io/roles")
        .then((response) => {
          this.list_roles = response.data;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    deleteRole(id) {
      axios
        // .delete(UrlConstants.role + this.role)
        .delete('https://601956c3fa0b1f0017acce88.mockapi.io/roles/'+ id)
        .then((id) => {
          console.log(id);
          if (confirm("Are you sure to Delete this Role?")) {
            alert("Delete Successfully");
            this.getRoleList();
          }
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
  },
};
</script>

<style scoped></style>
