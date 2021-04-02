import { DefaultConstants } from "@/constant/DefaultConstant";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
import Swal from 'sweetalert2'
export const commonHelper = {
  data() {
    return {
      loginUser: {},
      list_users: [],
      list_faculties: [],
      list_roles: [],
      list_errors: [],
      list_contributions: [],
      list_magazines: [],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
      },
      avatarUrl: null,
      confirmResult: false,
      canModify: false,
    }
  },
  created() {
    this.loginUser = this.$cookies.get("loginUser")
  },
  methods: {
    logOut() {
      Swal.fire({
        title: 'Are you sure to logout ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Yes',
        denyButtonText: 'No',
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(UrlConstants.Logout)
            .then(r => {
              this.$cookies.remove("jwt");
              this.$cookies.remove("loginUser");
              this.$cookies.remove("id");
              this.$emit("user-logout", null);
              this.$router.push("/login");
            })

        }
      })
    },
    getcommonSort($column) {
      if (this.filter.sort === "asc") {
        this.filter.sort = "desc";
      } else if (this.filter.sort === "desc") {
        this.filter.sort = "asc";
      }
      this.filter.column = $column;
      this.filter
    },
    getcommonLimit(limit) {
      this.filter.limit = limit;
      this.filter.page = 1;
      this.filter
    },
    changecommonPage(e) {
      this.filter.page = e;
      this.filter
    },
    getRoleList() {
      axios
        .post(UrlConstants.Role + "/filter", this.filter)
        .then((response) => {
          this.list_roles = response.data.data;
          this.list_roles.currentPage = this.filter.page;
          this.list_roles.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
          this.showError(this.errors);
        });
    },
    getFacultyList() {

      axios
        .post(UrlConstants.Faculty + "/filter", this.filter)
        .then((response) => {
          this.list_faculties = response.data.data;
          this.list_faculties.currentPage = this.filter.page;
          this.list_faculties.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data.errors;
          this.showError(this.errors);
        });
    },
    successAlert() {
      Swal.fire({
        icon: 'success',
        title: 'Your work has been saved',
        showConfirmButton: false,
        timer: 1500
      })
    },
    errorAlert(type, resource) {
      Swal.fire({
        icon: 'error',
        title: 'Cannot ' + type + ' this ' + resource,
      })
    },
    async confirmAlert(type, resource) {
      await Swal.fire({
        title: 'Are you sure to ' + type + ' this ' + resource + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Yes',
        denyButtonText: 'No',
      }).then((result) => {
        if (result.isConfirmed) {
          this.confirmResult = true;
        } else if (result.isDenied) {
          this.confirmResult = false;
        }
      })
      return this.confirmResult;
    },
    async checkUserExisted(resource, id) {
      if (resource === 'faculty') {
        this.filter.facultyId = id;
      } else if (resource === 'role') {
        this.filter.roleId = id;
      }
      await axios
        .post(UrlConstants.User + "/filter", this.filter)
        .then((response) => {
          this.list_users = response.data.data;
          if (Object.keys(this.list_users).length === 0) {
            this.canModify = true;
          } else {
            this.canModify = false;
          }
        });
    },
    getUserList() {
      this.checkIsCoordinator();
      axios
        .post(UrlConstants.User + "/filter", this.filter)
        .then((response) => {
          this.list_users = response.data.data;
          this.list_users.currentPage = this.filter.page;
          this.list_users.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    checkIsCoordinator() {
      if (this.loginUser.roleId === DefaultConstants.Role.MarketingCoordinator) {
        this.filter.facultyId = this.loginUser.facultyId;
      }
    },
    getContributionList() {
      axios
        .post(UrlConstants.Contribution + "/filter", this.filter)
        .then((response) => {
          this.list_contributions = response.data.data;
          this.list_contributions.currentPage = this.filter.page;
          this.list_contributions.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getMagazineList() {
      axios
        .post(UrlConstants.Magazine + "/filter", this.filter)
        .then((response) => {
          this.list_magazines = response.data.data;
          this.list_magazines.currentPage = this.filter.page;
          this.list_magazines.lastPage = response.data.lastPage;
        })
        .catch((error) => {
          this.errors = error.response.data;
          
        });
    },

  },
}