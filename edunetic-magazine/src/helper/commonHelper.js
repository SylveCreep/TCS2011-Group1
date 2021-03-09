import moment from "moment";
export const commonHelper = {
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
        preFormatDate(date) {
            return moment(String(date)).format(
                "yyyy-MM-DD HH:mm:ss"
            );

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
            let list_errors = {};
            for (let [key, value] of Object.entries(atributes)) {
                if (!Object.prototype.hasOwnProperty.call(objectType, key) || objectType[key] === "") {
                    list_errors[key] = value + " field is required";
                }
            }
            return list_errors;
        },
        userValidate(atributes, objectType) {
            let list_errors = {};
            //validate required attribute
            list_errors = this.requiredValidate(atributes, objectType);

            //validate age older than 18
            if (objectType.dateOfBirth) {
                let age = this.calculateAge(objectType.dateOfBirth) 
                if (age < 18) {
                    list_errors.dateOfBirth = "User must be older than 18";
                }
            }
            //validate phone input
            if (objectType.phoneNumber) {
                if (!this.checkPhone(objectType.phoneNumber)) { 
                    this.list_errors.phoneNumber = "Phone number field only can contain 0-9 digits";
                }
            }
            return list_errors;
        }
    }
}