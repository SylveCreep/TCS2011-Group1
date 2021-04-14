<template>
  <div class="content">
    <div class="container">
      <div class="Chart__container">
        <div class="Chart__title">
          Total student's contribution per month
          <hr />
        </div>
        <div class="Chart__content">
          <GChart
            type="PieChart"
            :data="chartData"
            :options="chartOptions"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import LineChart from "@/components/chart/LineChart";
import { commonHelper } from "@/helper/commonHelper";
import { GChart } from "vue-google-charts";
import { UrlConstants } from "@/constant/UrlConstant";
import router from "@/router";
import { DefaultConstants } from "@/constant/DefaultConstant";

export default {
  components: {
    GChart,
  },
  mixins: [commonHelper],
  props: {},
  data() {
    return {
      studentByFaculty: [],
      chartData: [
        ["Faculty", "students"],
      ],
      chartOptions: {
        chart: {
          title: "Company Performance",
          subtitle: "Sales, Expenses, and Profit: 2014-2017",
        },
      },
    };
  },
  created() {
    this.importChart()
  },
  methods: {
    importChart() {
      axios.get(UrlConstants.Faculty + "/getStudentCountByFaculty")
      .then((r) => {
        let result = r.data.data
        console.log(result)
        result.forEach(element => {
            this.chartData.push([element.facultyName, element.totalStudents])
        });
      });
    },
  },
};
</script>
