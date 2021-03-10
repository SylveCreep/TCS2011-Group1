export const validateHelper = {
    data() {
        return {
            list_errors: null,
            validate: true,
            timeCheck: null,
            password_match: null,
        }
    },
    methods: {
        calculateAge(dob) {
            let currentDate = new Date();
            let birthDate = new Date(dob);
            let difference = currentDate - birthDate;
            let age = Math.floor(difference / 31557600000);
            return age
        },
        checkPhone(string) {
            let numbers = /^[0-9]+$/;
            if (string.match(numbers)) {
                return true;
            } else {
                return false;
            }
        },
        showError(attributes, error) {
            for (let key of Object.keys(attributes)) {
                let text = document.querySelector("#" + key);
                if (Object.prototype.hasOwnProperty.call(error, key)) {
                    text.style.cssText = "border-color: red";
                } else {
                    text.style.cssText = "border-color: #CED4DA";
                }
            }
        },
        requiredValidate(atributes, objectType) {
            this.list_errors = {};
            for (let [key, value] of Object.entries(atributes)) {
                if (!Object.prototype.hasOwnProperty.call(objectType, key) || objectType[key] === "" || objectType[key] === null) {
                    this.list_errors[key] = value + " field is required";
                    this.validate = false;
                }
            }
        },
        userValidate(atributes, objectType) {
           
            //validate required attribute
            this.requiredValidate(atributes, objectType);

            //validate age older than 18
            if (objectType.dateOfBirth) {
                let age = this.calculateAge(objectType.dateOfBirth)
                if (age < 18) {
                    this.list_errors.dateOfBirth = "User must be older than 18";
                    this.validate = false;
                }
            }
            //validate phone input
            if (objectType.phoneNumber) {
                if (!this.checkPhone(objectType.phoneNumber)) {
                    this.list_errors.phoneNumber = "Phone number field only can contain 0-9 digits";
                    this.validate = false;
                }
            }

        },
        checkPassword() {
            let pass = document.querySelector("#password");
            let cpass = document.querySelector("#confirm_password");
            let self = this;
            // clear timeout variable
            clearTimeout(this.timeCheck);
            this.timeCheck = setTimeout(function () {
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
            }, 1000);
        },
    }
}