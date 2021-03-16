import { DefaultConstants } from "@/constant/DefaultConstant";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";

export const commonHelper = {
  data() {
    return {
      list_users: [],
      list_faculties: [],
      list_roles: [],
      list_errors: [],
      list_contributions:[],
      filter: {
        column: DefaultConstants.Column, //default column = 'id'
        sort: DefaultConstants.Sort, //default sort = 'asc'
        limit: DefaultConstants.Limit, //default limit = 15
        page: DefaultConstants.Page, //default page = 15
      },
    }
  },
  methods: {
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
    getUserList() {
      axios
        .post(UrlConstants.User + "/filter", this.filter)
        .then((response) => {
          this.list_users = response.data.data;
          this.list_users.currentPage = this.filter.page;
          this.list_users.lastPage = response.data.lastPage;
          console.log(this.list_users)
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    getContributionList() {
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
    // getContributionList() {
    //   axios
    //     .post(UrlConstants.Contribution , this.filter)
    //     .then((response) => {
    //       this.list_contributions = response.data;
    //       console.log(this.list_contributions)
    //     })
    //     .catch((error) => {
    //       this.errors = error.response.data;
    //     });
    // },
  },
}