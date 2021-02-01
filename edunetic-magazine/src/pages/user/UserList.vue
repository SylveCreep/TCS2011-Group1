<template>
  <div>
    <!-- Main content -->
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h2 class="card-title">User List</h2>
                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 150px;">
                    <router-link to="/users/create" tag="button" class="btn btn-success">
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
                    <th class="sort">
                      Code <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort">
                      Full name <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort">
                      Faculty <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort">
                      Role <i class="fas fa-sort"></i>
                    </th>
                    <th class="sort">
                      Email <i class="fas fa-sort"></i>
                    </th>
                    <th>
                      Action <i class="fas fa-sort"></i>
                    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="user of list_users" :key="user.id">
                    <td>{{user.code}}</td>
                    <td>{{user.name}}</td>
                    <td>{{user.faculty}}</td>
                    <td>{{user.role}}</td>
                    <td>{{user.email}}</td>
                    <td>
                      <p class="click" style="display: inline"><b>Update</b></p> |
                      <p class="click" style="display: inline"><b>Delete</b></p>
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
import {DefaultConstants} from "@/constant/DefaultConstant";
import {UrlConstants} from "@/constant/UrlConstant";
//import ThePagination from "@/components/ThePagination";

export default {
  name: "UserList",
  components: {
    //ThePagination
  },
  data() {
    return  {
      column: DefaultConstants.column, //default column = 'name'
      sort: DefaultConstants.sort, //default sort = 'asc'
      limit: DefaultConstants.limit, //default limit = 15
      list_users: [],
      errors: []
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    getUrl() {
      return UrlConstants.User + '?column=' + this.column
                               + '&limit=' + this.limit
                               + '&sort=' + this.sort
    },
    getUserList() {
      axios.get('https://60113b6e91905e0017be482b.mockapi.io/users')
          .then(response => {
            this.list_users = response.data;
          })
          .catch(error => {
            this.errors = error.response.data
          })
    }
  }
}
</script>

<style scoped>
.card{
  margin:20px;
}
</style>