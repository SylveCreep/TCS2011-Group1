<template>
  <div
    class="Chart__content"
    v-if="loginUser.roleId == 2 || loginUser.roleId == 3"
  >
    <!--Only MarkettingManager & MarketingCodinator can access this chart -->
    <GChart type="LineChart" :data="chartData" :options="chartOptions" />
    <p class="text-center" style="color: red" v-if="list_errors.showErrorMcMm">
      {{ errorMessage }}
    </p>
  </div>
</template>
<script>
import axios from "axios";
import { GChart } from "vue-google-charts";
import { UrlConstants } from "@/constant/UrlConstant";
import { commonHelper } from "@/helper/commonHelper";

export default {
  mixins: [commonHelper],
  components: {
    GChart,
  },
  props: {},
  data() {
    return {
      list_errors: {
        showErrorMcMm: false,
      },
      errorMessage: "This chart is nor exist",
      chartData: [["Faculty", "students"]],
      chartOptions: {
        title: "Total contribution in magazine",
        width: "700",
        height: "400",
      },
    };
  },
  created() {
    this.importChart();
  },
  methods: {
    importChart() {
      axios
        .get(UrlConstants.Magazine + "/getContributionCountByMagazine")
        .then((r) => {
          let result = r.data.data;
          result.forEach((element) => {
            this.chartData.push([
              element.magazineName,
              element.totalContributions,
            ]);
          });
        })
        .catch((e) => {
          this.errorMessage = e.response.data.error;
          this.list_errors.showErrorMcMm = true;
        });
    },
  },
};
</script>
<style scoped>
.Chart-content {
  height: 400px;
}
</style>
