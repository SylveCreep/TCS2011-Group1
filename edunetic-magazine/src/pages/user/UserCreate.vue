<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>User Create</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-body">
        <h5 class="card-title">Create Form</h5>
        <form v-on:submit.prevent="createUser()">
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Full Name: </label>
            <div class="col-sm-12">
              <input
                id="fullName"
                type="text"
                class="form-control"
                v-model="user.fullName"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.fullName }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Role: </label>
            <div class="col-sm-12">
              <select
                class="form-control"
                id="roleId"
                name="role"
                v-model="user.roleId"
                v-on:change="selectFaculty"
              >
                <option
                  v-for="role in roleList"
                  :key="role.id"
                  v-bind:value="role.id"
                >
                  {{ role.name }}
                </option>
              </select>
            </div>
          </div>
          <div
            class="position-relative form-group"
            v-if="this.user.roleId === 3"
          >
            <label class="col-sm-2 control-label">Faculty: </label>
            <div class="col-sm-12">
              <select
                class="form-control select2"
                id="facultyId"
                name="category"
                v-model="user.facultyId"
              >
                <option value="" disabled selected>
                  Please choose faculty
                </option>
                <option
                  v-for="faculty in newFaculty"
                  :key="faculty.facultyId"
                  v-bind:value="faculty.facultyId"
                >
                  {{ faculty.facultyName }}
                </option>
              </select>
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.facultyId }}
              </p>
            </div>
          </div>
          <div
            class="position-relative form-group"
            v-else-if="this.user.roleId === 4"
          >
            <label class="col-sm-2 control-label">Faculty: </label>
            <div class="col-sm-12">
              <select
                class="form-control select2"
                id="facultyId"
                name="category"
                v-model="user.facultyId"
              >
                <option value="" disabled selected>
                  Please choose faculty
                </option>
                <option
                  v-for="faculty in list_faculties"
                  :key="faculty.facultyId"
                  v-bind:value="faculty.facultyId"
                >
                  {{ faculty.facultyName }}
                </option>
              </select>
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.facultyId }}
              </p>
            </div>
          </div>

          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Email: </label>
            <div class="col-sm-12">
              <input
                id="email"
                type="email"
                class="form-control"
                v-model="user.email"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.email }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Password: </label>
            <div class="col-sm-12">
              <input
                id="password"
                type="password"
                class="form-control"
                v-model="user.password"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.password }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Confirm password: </label>
            <div class="col-sm-12">
              <input
                id="confirm_password"
                type="password"
                class="form-control"
                v-model="user.confirm_password"
                v-on:keyup="checkPassword"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.confirm_password }}
              </p>
              <p v-if="password_match == false" style="color: red">
                password don't match
              </p>
              <p v-else-if="password_match == true" style="color: green">
                password matched
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Address: </label>
            <div class="col-sm-12">
              <input
                id="address"
                type="text"
                class="form-control"
                v-model="user.address"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.address }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Gender </label>
            <div class="col-sm-12">
              <input
                type="radio"
                id="rmale"
                v-model="user.gender"
                value="1"
                checked
              />
              <label for="male" class="label-gender">Male</label>
              <input
                type="radio"
                id="rfemale"
                v-model="user.gender"
                value="0"
              />
              <label for="female" class="label-gender">Female</label>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Phone Number: </label>
            <div class="col-sm-12">
              <input
                id="phoneNumber"
                type="tel"
                class="form-control"
                v-model="user.phoneNumber"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.phoneNumber }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label class="col-sm-2 control-label">Date of birth: </label>
            <div class="col-sm-12">
              <input
                id="dateOfBirth"
                type="date"
                class="form-control"
                v-model="user.dateOfBirth"
              />
              <p style="color: red" v-if="list_errors !== null">
                {{ list_errors.dateOfBirth }}
              </p>
            </div>
          </div>
          <div class="position-relative form-group">
            <label for="exampleFile" class="col-sm-2 control-label">Avatar</label>
            <div class="col-3">
              <input
                name="file"
                id="exampleFile"
                type="file"
                ref="file"
                class="form-control-file"
                v-on:change="onFileChange"
              />
            </div>
            <div class="col-3">
              <div id="preview">
                <img v-if="previewImageUrl" :src="previewImageUrl" />
              </div>
            </div>
          </div>
          <div class="position-relative form-group text-center">
            <div class="col-sm-offset-2 col-sm-12">
              <router-link to="/users" tag="button" class="btn btn-primary">
                Back
              </router-link>
              <button type="submit" class="btn btn-success">Create</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { commonHelper } from "@/helper/commonHelper";
import { validateHelper } from "@/helper/validateHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
export default {
  name: "UserCreate",
  mixins: [validateHelper, commonHelper],
  data() {
    return {
      user: {
        gender: 1,
        roleId: 1,
        facultyId: "",
      },
      requireAttribute: {
        fullName: "Fullname",
        address: "Address",
        phoneNumber: "Phone number",
        dateOfBirth: "Date of birth",
        email: "email",
        password: "password",
        confirm_password: "Confirm password",
      },
      previewImageUrl: null,
    };
  },
  computed: {
    roleList() {
      return this.list_roles.filter((role) => role.id !== 5);
    },
    newFaculty() {
      return this.list_faculties.filter(
        (faculty) => faculty.managerId === null
      );
    },
  },
  created() {
    this.getRoleList();
    this.getFacultyList();
  },
  methods: {
    async createUser() {
      let formData = new FormData();
      this.userValidate(this.requireAttribute, this.user); //this function is called from helperMixin.js file
      this.showError(this.requireAttribute, this.list_errors); //this function is called from helperMixin.js file
      if (this.validate) {
        for (const [key, value] of Object.entries(this.user)) {
          if (key !== "confirm_password") {
            formData.append(key, value);
          }
        }
        await this.confirmAlert('create', 'user');
        if (this.confirmResult) {
             axios
          .post(UrlConstants.User, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((r) => {
            this.successAlert(); //This function are called from commonHelper.js file
            this.$router.push("/users");
          })
          .catch((error) => {
            this.list_errors = error.response.data.validate.input;
            this.showError(this.requireAttribute, this.list_errors);
          });
      }
        }
    },
    selectFaculty() {
      if (
        this.user.roleId === DefaultConstants.Role.Student ||
        this.user.roleId === DefaultConstants.Role.MarketingCoordinator
      ) {
        if (this.user.facultyId === undefined) {
          this.user.facultyId = 1;
        }
      } else {
        if (this.user.facultyId !== undefined) {
          delete this.user.facultyId;
        }
      }
    },
    onFileChange() {
      const tfile = this.$refs.file.files[0];
      this.user.file = tfile;
      this.previewImageUrl = URL.createObjectURL(tfile);
    },
  },
};
</script>

<style scoped>
.label-gender {
  padding-left: 5px;
  padding-right: 20px;
}
.app-page-title {
  margin: -30px 0 0 -30px;
}
.control-label {
  margin: 0px;
}
.input-file {
  margin-left: 30px;
  padding: 0;
  border-radius: 5px;
  border: 1px solid;
}
.form-text-file {
  margin: 10px 0 0 15px;
}
#preview {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 300px;
}

#preview img {
  max-width: 100%;
  max-height: 300px;
}
</style>
