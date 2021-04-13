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
            type="ColumnChart"
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
      filter: {
        roleId: null
      },
      student: {
        name: [],
        contribution: [],
      },
      chartData: [
        ["Year", "Sales", "Expenses", "Profit"],
        ["2014", 1000],
        ["2015", 1170],
        ["2016", 660],
        ["2017", 1030],
      ],
      // chartData: [
      //   this.student,
      // ],
      chartOptions: {
        chart: {
          title: "Company Performance",
          subtitle: "Sales, Expenses, and Profit: 2014-2017",
        },
      },
    };
  },
  created() {
    this.getUser();
    this.importChart()
    // console.log(this.student.name);
  },
  methods: {
    importChart() {
      axios.post(UrlConstants.User + "/filter", this.filter).then((r) => {
        this.student = r.data.data;
        console.log(this.student);
      });
    },
    getUser() {
      this.filter.roleId = DefaultConstants.Role.Student;
    },
  },
};
</script>
