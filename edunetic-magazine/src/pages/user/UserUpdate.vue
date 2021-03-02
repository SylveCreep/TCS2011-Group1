<template>
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h2>User Update</h2>
                        </div>
                        <div class="card-body">
                            <div class="tab-content">
                                <div class="tab-pane active show" id="settings">
                                    <div class="alert alert-danger" v-if="errors !== null">
                                        <ul>
                                            <li v-for="(v, k) in errors" :key="k">
                                                {{ v.toString() }}
                                            </li>
                                        </ul>

                                    </div>
                                    <form class="form-horizontal" v-on:submit.prevent="updateUser()">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Full Name: </label>
                                            <div class="col-sm-12">
                                                <input id="username" type="text" class="form-control" v-model="user.fullName"/>
                                            </div>
                                        </div>
                                      <div class="form-group">
                                        <label class="col-sm-2 control-label">Code: </label>
                                        <div class="col-sm-12">
                                          <input id="code" type="text" class="form-control" v-model="user.code" readonly/>
                                        </div>
                                      </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Role: </label>
                                            <div class="col-sm-12">
                                                <select class="form-control select2"  id="role_id" name="category"  v-model="user.role_id">
                                                  <option v-for="role in list_roles" :key="role.id"
                                                          v-bind:value="role.id">
                                                    {{role.name}}
                                                  </option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Faculty: </label>
                                            <div class="col-sm-12">
                                                <select class="form-control select2"  id="faculty_id" name="category"  v-model="user.faculty_id">
                                                    <option v-for="faculty in list_faculties" :key="faculty.id"
                                                            v-bind:value="faculty.id">
                                                      {{faculty.name}}
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Email: </label>
                                            <div class="col-sm-12">
                                                <input id="email" type="text" class="form-control" v-model="user.email" readonly/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Address: </label>
                                            <div class="col-sm-12">
                                                <input id="address" type="text" class="form-control" v-model="user.address"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Phone Number: </label>
                                            <div class="col-sm-12">
                                                <input id="phone" type="text" class="form-control" v-model="user.phone"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Date of birth: </label>
                                            <div class="col-sm-12">
                                                <input id="DoB" type="date" class="form-control" v-model="user.dateOfBirth"/>
                                            </div>
                                        </div>
                                        <div class="form-group text-center">
                                            <div class="col-sm-offset-2 col-sm-12">
                                                <router-link to="/users" tag="button" class="btn btn-primary">
                                                    Back
                                                </router-link>
                                                <button type="submit" class="btn btn-success">Create</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <!-- /.tab-pane -->
                            </div>
                            <!-- /.tab-content -->
                        </div>
                        <!-- /.card-body -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</template>

<script>
import axios from "axios";
import {UrlConstants} from "@/constant/UrlConstant";

export default {
  name: "UserUpdate",
  data(){
    return{
      user: {},
      errors: null,
      list_faculties: {},
      list_roles: {},
    }
  },
  created() {
    this.getUser();
    this.getFacultyList();
    this.getRoleList();
  },
  methods: {
    getUser(){
      axios.get(UrlConstants.User + '/' + this.$route.params.id)
          .then(r =>{
            this.user = r.data.data
          })
          .catch(error =>{
              this.errors = error.response;
          })
    },
    showError(errors){
      Object.keys(errors).forEach(error=>{
        let text = document.querySelector('#'+error);
        text.style.cssText = 'border-color: red'
      })
    },
    getRoleList() {
      axios.get(UrlConstants.Role)
          .then(response => {
            this.list_roles = response.data
            console.log(this.list_roles);
          })
          .catch(error =>{
            this.errors = error.response.data.errors;
            this.showError(this.errors);
          })
    },
    getFacultyList() {
      axios.get(UrlConstants.Faculty)
          .then(response => {
            this.list_faculties = response.data
          })
          .catch(error =>{
            this.errors = error.response.data.errors;
            this.showError(this.errors);
          })
    },
    updateUser() {
      axios.patch(UrlConstants.User + '/' + this.$route.params.id, this.user )
          .then(response => {
            console.log(response)
            alert('success')
            this.$router.push('/products')
          })
          .catch(error =>{
            this.errors = error.response.data.errors;
            this.showError(this.errors);
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
