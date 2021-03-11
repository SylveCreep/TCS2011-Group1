<template>
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              <h2>Role Create</h2>
            </div>
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane active show" id="settings">
                  <form
                    class="form-horizontal"
                    v-on:submit.prevent="createRole()"
                  >
                    <div class="form-group">
                      <label class="col-sm-2 control-label">Name: </label>
                      <div class="col-sm-12">
                        <input
                          id="name"
                          type="text"
                          class="form-control"
                          v-model="role.name"
                        />
                        <p style="color: red" v-if="list_errors !== null">
                          {{ list_errors.name }}
                        </p>
                      </div>
                    </div>
                    <div class="form-group text-center">
                      <div class="col-sm-offset-2 col-sm-12">
                        <router-link
                          to="/roles"
                          tag="button"
                          class="btn btn-primary"
                        >
                          Back
                        </router-link>
                        <button type="submit" class="btn btn-success">
                          Create
                        </button>
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
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";

export default {
  name: "RoleCreate",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      role: {},
      requireAttribute: {
        name: "Role Name",
      },
    };
  },
  methods: {
    createRole() {
      this.userValidate(this.requireAttribute, this.role); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        axios
          .post(UrlConstants.Role, this.role) 
          .then((r) => {
            alert("Create Successfully");
            this.$router.push("/roles");
          })
          .catch((error) => {
            this.list_errors = error.response;
          });
      }
    },
  },
};
</script>

<style scoped>
.card {
  margin: 20px;
}
.label-gender {
  padding-left: 5px;
  padding-right: 20px;
}
</style>
