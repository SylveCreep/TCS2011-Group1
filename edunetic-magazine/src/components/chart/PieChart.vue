<template>
  <div class="Chart__content">
    <!--Only MarkettingManager & MarketingCodinator can access this chart -->
    <GChart type="PieChart" :data="chartData" :options="chartOptions" />
    <p class="text-center" style="color: red" v-if="list_errors.showErrorMcMm">
      {{ list_errors.errorMessage }}
    </p>
  </div>
</template>
<script>
import axios from "axios";
import { GChart } from "vue-google-charts";
import { UrlConstants } from "@/constant/UrlConstant";
import { commonHelper } from "@/helper/commonHelper";

export default {
  components: {
    GChart,
  },
  props: {},
  data() {
    return {
      list_errors: {
        showErrorMcMm: false,
        errorMessage: "This chart is nor exist",
      },
      chartData: [["Faculty", "students"]],
      chartOptions: {
        "title": "Student in faculty",
        "width": "700",
        "height": "400",
      },
    };
  },
  created() {
    this.importChart();
  },
  methods: {
    importChart() {
      axios
        .get(UrlConstants.Faculty + "/getStudentCountByFaculty")
        .then((r) => {
          let result = r.data.data;
          result.forEach((element) => {
            this.chartData.push([element.facultyName, element.totalStudents]);
          });
        })
        .catch((e) => {
          this.errorMessage = e.response.data.error;
          this.list_erroes.showError = true;
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
