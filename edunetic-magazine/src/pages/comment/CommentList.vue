<template>
  <div class="app-main__inner">
    <div class="row">
      <p class="comment-total">Comments (3):</p>
    </div>
    <div class="comment-list">
      <div class="comment-parent">
        <div class="comment-item row">
          <div class="comment-user-avatar">
            <img
              src="assets/images/avatars/1.jpg"
              class="rounded-circle"
              alt=""
              width="42"
            />
          </div>
          <div class="comment-detail col-10">
            <div class="comment-user-name">
              Simon
              <span class="comment-time">Few second ago</span>
            </div>
            <div class="comment-text d-flex p-2">
              <span>
                ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="comment-children">
        <div class="comment-item row">
          <div class="comment-user-avatar">
            <img
              src="assets/images/avatars/1.jpg"
              class="rounded-circle"
              alt=""
              width="42"
            />
          </div>
          <div class="comment-detail col-10">
            <div class="comment-user-name">
              Simon
              <span class="comment-time">Few second ago</span>
            </div>
            <div class="comment-text d-flex p-2">
              <span>
                ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="comment-parent">
        <div class="comment-item row">
          <div class="comment-user-avatar">
            <img
              src="assets/images/avatars/1.jpg"
              class="rounded-circle"
              alt=""
              width="42"
            />
          </div>
          <div class="comment-detail col-10">
            <div class="comment-user-name">
              Simon
              <span class="comment-time">Few second ago</span>
            </div>
            <div class="comment-text d-flex p-2">
              <span> ggggggggggggggggggggggggggg </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
export default {
  name: "CommentList",
  mixins: [commonHelper],
  data() {
    return {
      newComment: {},
      list_comments: [],
    };
  },
  created() {
    this.connect();
    this.getCommentList();
  },
  methods:{
    getCommentList() {
      axios
        .post(UrlConstants.Comment + "/filter", this.filter)
        .then((response) => {
          this.list_comments = response.data.data;
          this.list_comments.currentPage = this.filter.page;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    sendComment() {
      
      axios.post(UrlConstants.BaseUrl + "comment/send", )
    },
    connect() {
      this.stompClient = Stomp.over(new SockJS("http://b7b5cf66f9e8.ngrok.io/stomp"));
      this.stompClient.connect(
        {
          Authorization:
            "Bearer " + this.$cookies.get("jwt")
        },
        (frame) => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/channel/contribution/1", (tick) => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
          });
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
  }
};
</script>

<style scoped>
.comment-total {
  font-weight: bold;
}
.comment-children {
  margin-left: 3.5rem
}
.comment-item {
  margin-top: 1rem;
}
.comment-user-name {
  font-size: 1.2rem;
  font-weight: bold;
}
.comment-time {
  font-size: 0.8rem;
  font-weight: normal;
}
.comment-text {
  border: 1px solid #ced4da;
  border-radius: 10px;
  padding: 0.7rem;
}
</style>