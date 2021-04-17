<template>
  <div class="Chart__content">
    <!--Only MarkettingManager & MarketingCodinator can access this chart -->
    <GChart type="ColumnChart" :data="chartData" :options="chartOptions" />
    <p class="text-center" style="color: red" v-if="list_errors.showErrorMcMm">
      {{ errorMessage }}
    </p>
  </div>
</template>
<script>
import axios from "axios";
import { GChart } from "vue-google-charts";
import { UrlConstants } from "@/constant/UrlConstant";

export default {
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
      chartData: [["Faculty", "Contributions"]],
      chartOptions: {
        title: "Total contribution of each faculty",
        width: "500",
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
        .get(UrlConstants.Faculty + "/getContributionCountByFaculty")
        .then((r) => {
          let result = r.data.data;
          result.forEach((element) => {
            this.chartData.push([
              element.facultyName,
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
