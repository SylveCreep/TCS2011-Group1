<template>
  <div class="content">
    <div class="container">
      <div class="Chart__container" v-if="loaded">
        <div class="Chart__title">
          Total student's contribution per month
          <hr />
        </div>
        <div class="Chart__content">
          <line-chart
            v-if="loaded"
            :chartData="user.contribution"
            :chartLabels="user.weeks"
          ></line-chart>
        </div>
        <p class="text-center" style="color: red" v-if="showError">
          {{ errorMessage }}
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import LineChart from "../components/chart/LineChart.vue";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import router from "@/router";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  components: {
    LineChart,
  },
  mixins: [commonHelper],
  props: {},
  data() {
    return {
      //   package: null,
      //   packageName: "",
      //   period: "last-month",
      //   loaded: false,
      //   downloads: [],
      //   labels: [],
      //   showError: false,
      //   errorMessage: "Please enter a package name",
      user: {
        contribution: [],
        weeks: [],
      },
      loaded: false,
      showError: [],
      errorMessage: "Chart are not exist",
    };
  },
  created() {
    this.getUserList();
    this.getContributionList();
    this.getMagazineList();
    this.getUser();
    this.importChart();
    this.loaded = false;
  },
  methods: {
    // requestData() {
    //   axios
    //     .get(
    //       `https://api.npmjs.org/downloads/range/${this.period}/${this.package}`
    //     )
    //     .then((response) => {
    //       console.log(response.data);
    //       this.downloads = response.data.downloads.map(
    //         (download) => download.downloads
    //       );
    //       this.labels = response.data.downloads.map((download) => download.day);
    //       this.packageName = response.data.package;
    //       this.loaded = true;
    //     });
    // },
    importChart() {
      //   if (this.loginUser === 4) {
      //     axios
      //       .get(UrlConstants.User + "/" + this.$route.params.id, this.user)
      //       .then((response) => {
      //         console.log(response.data);
      //         this.user.chartData = this.response.data.data;
      //         this.user.chartOption = this.response.data.data;
      //         this.loaded = true;
      //       })
      //       .catch((error) => {
      //         this.errorMessage = error.response.data.error;
      //         this.showError = true;
      //       });
      //   }
      // },
      axios
        .get("https://6072f548e4e0160017ddf160.mockapi.io/users/line/1")
        .then((response) => {
          console.log(response.data);
          this.user = response.data;
          this.loaded = true;
          console.log(this.user.weeks);
        })
        .catch((error) => {
          this.errorMessage = error.response.data.error;
          this.showError = true;
        });
    },
    getUser() {
      this.user.id = this.loginUser.id;
    },
  },
};
</script>
