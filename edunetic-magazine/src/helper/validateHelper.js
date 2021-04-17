import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
import moment from 'moment';
export const validateHelper = {
  data() {
    return {
      list_errors: null,
      validate: true,
      timeCheck: null,
      password_match: null,
    };
  },
  methods: {
    calculateAge(dob) {
      let currentDate = new Date();
      let birthDate = new Date(dob);
      let difference = currentDate - birthDate;
      let age = Math.floor(difference / 31557600000);
      return age;
    },
    checkPhone(phone) {
      let numbers = /^[0-9]+$/;
      if (!numbers.test(phone)) {
        this.list_errors.phoneNumber =
          "Phone number field only can contain 0-9 digits";
        this.validate = false;
      }
      if (phone.length < 9 || phone.length > 15) {
        this.list_errors.phoneNumber =
          "Phone number field must be from 9 to 15 digits";
        this.validate = false;
      }
    },
    checkEMail(email) {
      let format = /^([A-Za-z0-9_\-.+])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,})$/;

      if (!format.test(email)) {
        this.list_errors.email = "Invalid email format";
        this.validate = false;
      }
    },
    showError(attributes, error) {
      for (let key of Object.keys(attributes)) {
        let text = document.querySelector("#" + key);
        if (Object.prototype.hasOwnProperty.call(error, key)) {
          if (key === "facultyId") {
            text.style.cssText = "border: 1px solid red";
          }
          text.style.cssText = "border-color: red";
        } else {
          text.style.cssText = "border-color: #CED4DA";
        }
      }
    },
    requiredValidate(attributes, objectType) {
      this.list_errors = {};
      for (let [key, value] of Object.entries(attributes)) {
        if (
          !Object.prototype.hasOwnProperty.call(objectType, key) ||
          objectType[key] === "" ||
          objectType[key] === null
        ) {
          this.list_errors[key] = value + " field is required";
          this.validate = false;
        } else {
          this.validate = true;
        }
      }
    },
    userChangePasswordValidate(attributes, objectType) {
      //validate required attribute
      this.requiredValidate(attributes, objectType);

      //validate new_password length
      if (objectType.new_password) {
        if (objectType.new_password.length < 9) {
          this.list_errors.new_password =
            "Password must have more than 8 characters";
          this.validate = false;
        }
      }

      //validate current_password do not same new_password
      if (objectType.new_password === objectType.password) {
        this.list_errors.new_password =
          "New password can not be same as current password";
        this.validate = false;
      }
    },
    userValidate(attributes, objectType) {
      //validate required attribute
      this.requiredValidate(attributes, objectType);

      //validate email contain special characters
      if (objectType.email) {
        this.checkEMail(objectType.email);
      }

      //validate age older than 18
      if (objectType.dateOfBirth) {
        let age = this.calculateAge(objectType.dateOfBirth);
        if (age < 18) {
          this.list_errors.dateOfBirth = "User must be older than 18";
          this.validate = false;
        } else {
          this.validate = true;
        }
      }

      //validate phone input
      if (objectType.phoneNumber) {
        this.checkPhone(objectType.phoneNumber);
      }

      //validate password length
      if (objectType.password) {
        if (objectType.password.length < 6) {
          this.list_errors.password =
            "Password must have more than 5 characters";
          this.validate = false;
        }
      }

      //validate faculty when role is marketing coordinator
      if (
        objectType.roleId === DefaultConstants.MarketingCoordinator &&
        objectType.facultyId == ""
      ) {
        this.list_errors.facultyId =
          "Please choose faculty for this marketing coordinator";
        this.validate = false;
      }

      //validate faculty when role is marketing coordinator
      if (
        objectType.roleId === DefaultConstants.Student &&
        objectType.facultyId == ""
      ) {
        this.list_errors.facultyId = "Please choose faculty for this student";
        this.validate = false;
      }
    },
    checkPassword() {
      let pass = document.querySelector("#password");
      let cpass = document.querySelector("#confirm_password");
      let self = this;
      // clear timeout variable
      clearTimeout(this.timeCheck);
      this.timeCheck = setTimeout(function () {
        if (
          self.user.password !== "" &&
          self.user.confirm_password !== ""
        ) {
          if (self.user.password !== self.user.confirm_password) {
            self.password_match = false;
            pass.style.cssText = "border-color: red";
            cpass.style.cssText = "border-color: red";
            self.validate = false;
            self.password_match = false; //render don't match message
          } else {
            self.password_match = true;
            pass.style.cssText = "border-color: #CED4DA";
            cpass.style.cssText = "border-color: #CED4DA";
            self.validate = true;
            self.password_match = true; //render match message
          }
        }
      }, 1000);
    },
    magazineValidate(attributes, objectType) {
      //validate required attribute
      this.requiredValidate(attributes, objectType);


      //validate finished date must be later than days after created date least 14
      if (objectType.finished_at) {
        let finishDate = this.calculateDate(objectType.finished_at)
        if (finishDate < 14) {
          this.list_errors.finished_at = "Finish date must be later than today at least 14 days";
          this.validate = false;
        } else {
          this.validate = true;
        }
      }

      //validate publish date must be later than days finish created date least 14
      if (objectType.published_at) {
        let publishDate = this.calculateDate(objectType.published_at)
        console.log(publishDate)
        if (publishDate < 14) {
          this.list_errors.published_at = "Publish date must be later than finish date at least 14 days";
          this.validate = false;
        } else {
          this.validate = true;
        }
      }
    },
    calculateDate(date) {
      let inputDate = moment(date)
      let now = moment(new Date());
      let different = moment.duration(inputDate.diff(now))._data
      return different.days;
    }
  },

  //COMMON HELPER FUNCTIONS
  getRoleList() {
    axios
      .post(UrlConstants.Role + "/filter", this.filter)
      .then((response) => {
        this.list_roles = response.data.data;
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
      })
      .catch((error) => {
        this.errors = error.response.data.errors;
        this.showError(this.errors);
      });
  },
  getMagazineList() {
    axios
      .post(UrlConstants.Magazine + "/filter", this.filter)
      .then((response) => {
        this.list_magazines = response.data.data;
      })
      .catch((error) => {
        this.errors = error.response.data.errors;
        this.showError(this.errors);
      });
  },
};
