<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="app-page-title">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="pe-7s-display1 icon-gradient bg-premium-dark"> </i>
          </div>
          <div>
            <h2>Contribution Detail</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main-card mb-3 card">
      <div class="card-header">
        <i class="header-icon lnr-license icon-gradient bg-plum-plate"> </i>
        code: {{ contribution.code }}
      </div>
      <div class="card-body">
        <p><b>Student Name:</b> {{ contribution.studentName }}</p>

        <p><b>Student Email:</b> {{ contribution.email }}</p>

        <p><b>Faculty:</b> {{ contribution.facultyName }}</p>

        <p><b>Submit Date:</b> {{ contribution.createdAt | formatDate }}</p>

        <p>
          <b>File:</b>
          <span class="file-source" v-on:click="downloadContribution">{{
            contribution.linkSource
          }}</span>
        </p>
        <input
          type="file"
          v-bind:value="contribution.file"
          multiple
          v-if="loginUser.roleId === 4"
        />
        <!--Only student can edit their contribution-->
        <p v-if="contribution.status == 0">
          <b>Pending:</b> Waiting for reviewing
        </p>

        <p v-if="contribution.status == 1">
          <b>Approved by:</b>{{ contribution.checkedByName }}
        </p>

        <p v-if="contribution.status == -1">
          <b>Denied by: </b> {{ contribution.checkedByName }}
        </p>
      </div>
      <div class="d-block text-right card-footer">
        <p
          v-if="loginUser.roleId !== 4 && contribution.status === 0"
          class="btn btn-danger"
          v-on:click="updateContributionStatus(contribution.id, -1, 'deny')"
        >
          Deny
        </p>
        <p
          v-if="loginUser.roleId !== 4 && contribution.status === 0"
          class="btn btn-success"
          v-on:click="updateContributionStatus(contribution.id, 1, 'approve')"
        >
          Approve
        </p>
        <router-link to="/contributions" class="btn back-btn">
          Back
        </router-link>
      </div>
    </div>
    <comment-list></comment-list>
  </div>
</template>


<script>
import axios from "axios";
import { UrlConstants } from "@/constant/UrlConstant";
import { validateHelper } from "@/helper/validateHelper";
import { commonHelper } from "@/helper/commonHelper";
import FileSaver from "file-saver";
import CommentList from "@/pages/comment/CommentList";
export default {
  name: "ContributionDetail",
  mixins: [commonHelper, validateHelper],
  components: {
    CommentList,
  },
  data() {
    return {
      contribution: {},
    };
  },
  created() {
    this.getContribution();
  },
  methods: {
    getContribution() {
      axios
        .get(UrlConstants.Contribution + "/" + this.$route.params.id)
        .then((r) => {
          this.contribution = r.data.data;
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    downloadContribution() {
      axios
        .get(
          UrlConstants.Contribution +
            "/download?contributionId=" +
            this.$route.params.id,
          { responseType: "blob" }
        )
        .then((r) => {
          FileSaver.saveAs(r.data, "contribution.zip");
        })
        .catch((error) => {
          this.errors = error.response;
        });
    },
    async updateContributionStatus(contribution_id, statusId, statusName) {
      await this.confirmAlert(statusName, "this contribution");
      if (this.confirmResult) {
        let contributionUpdate = {
          id: contribution_id,
          status: statusId,
        };
        axios
          .post(UrlConstants.Contribution + "/updateStatus", contributionUpdate)
          .then((r) => {
            this.successAlert();
            this.getContribution();
          });
      }
    },
  },

  props: {},
};
</script>


<style scoped>
.contribution-detail {
  background-color: #3f6ad8;
  color: white !important;
}
.card-body {
  padding-left: 2rem !important;
}
.row .card-footer {
  background-color: #f0f3f5;
}
.file-source {
  color: #3f6ad8;
  text-decoration: underline;
  cursor: pointer;
}
.btn {
  margin-left: 10px;
}
.back-btn {
  margin-bottom: 1rem;
  background-color: #3f6ad8;
  color:white
}
</style>
