<template>
  <div class="app-main__inner" style="background-color: #fff">
    <div class="row">
      <p class="comment-total">Comments ({{ totalComment }}):</p>
    </div>
    <div class="comment-list">
      <div
        class="comment-parent"
        v-for="comment of parent_comments"
        :key="comment.id"
      >
        <!--Parent detail comment part-->
        <div class="comment-item row">
          <div class="comment-user-avatar">
            <img
              v-bind:src="linkSource + comment.avatar"
              class="rounded-circle"
              alt=""
              width="42"
            />
          </div>
          <div class="comment-detail col-10">
            <div class="comment-user-name">
              {{ comment.userName }}
              <span class="comment-time">{{
                calculateDate(comment.createdDate)
              }}</span>
            </div>
            <div class="comment-text d-flex p-2">
              <span>
                {{ comment.content }}
              </span>
            </div>
          </div>
        </div>
        <!--End of parent detail comment part-->

        <!--Action comment part-->
        <div class="comment-action">
          <p
            class="comment-action-item"
            v-on:click="showReplyComments(comment.id)"
          >
            Show replied Comments
          </p>
          <p
            class="comment-action-item"
            v-on:click="showReplyCommentInput(comment.id)"
          >
            | Reply
          </p>
          <p
            class="comment-action-item"
            v-on:click="showEditParentCommentById(comment.id, comment.content)"
            v-if="comment.userId === loginUser.id"
          >
            | Edit
          </p>
          <p
            class="comment-action-item"
            v-on:click="deleteCommentById(comment.id)"
            v-if="comment.userId === loginUser.id"
          >
            | Delete
          </p>
        </div>
        <!--End of action comment part-->

        <!--Children comment part-->
        <div class="is-hide" v-bind:id="'comment-' + comment.id">
          <div
            class="comment-children"
            v-for="ch of children_comments"
            :key="ch.id"
          >
            <div class="comment-item row" v-if="ch.parentId === comment.id">
              <div class="comment-user-avatar">
                <img
                  :src="linkSource + ch.avatar"
                  class="rounded-circle"
                  alt=""
                  width="42"
                />
              </div>
              <div class="comment-detail col-10">
                <div class="comment-user-name">
                  {{ ch.userName }}
                  <span class="comment-time">{{
                    calculateDate(ch.createdDate)
                  }}</span>
                </div>
                <div class="comment-text d-flex p-2">
                  <span>
                    {{ ch.content }}
                  </span>
                </div>
              </div>
            </div>
            <!--Action children comment part-->
            <div class="comment-action" v-if="ch.parentId === comment.id">
              <p
                class="comment-action-item"
                v-on:click="showEditCommentById(ch.id, ch.parentId, ch.content)"
                v-if="ch.userId === loginUser.id"
              >
                Edit
              </p>
              <p
                class="comment-action-item"
                v-on:click="deleteCommentById(ch.id)"
                v-if="ch.userId === loginUser.id"
              >
                | Delete
              </p>
            </div>
            <!--End of action children comment part-->
          </div>
        </div>
        <!--End of children comment part-->

        <!--Reply comment part-->
        <div
          class="your-reply-comment is-hide"
          v-bind:id="'reply-comment-' + comment.id"
        >
          <div class="comment-item row">
            <div class="comment-user-avatar">
              <img
                v-bind:src="linkSource + loginUser.avatar"
                class="rounded-circle"
                alt=""
                width="42"
              />
            </div>
            <div class="comment-detail col-10">
              <div class="comment-user-name">
                {{ loginUser.fullName }}
              </div>
              <div class="row comment-reply">
                <input
                  type="text"
                  editid=""
                  name="replyComment"
                  v-bind:id="'input-comment-' + comment.id"
                  class="comment-text d-flex p-2 col-10"
                  placeholder="Reply.........."
                />
                <button
                  class="btn btn-reply"
                  v-on:click="replyCommentParent(comment.id)"
                  v-bind:id="'btn-reply-' + comment.id"
                >
                  Reply
                </button>
                <button
                  class="btn btn-edit"
                  v-on:click="editCommentById(comment.id)"
                  v-bind:id="'btn-edit-' + comment.id"
                >
                  Edit
                </button>
              </div>
            </div>
          </div>
        </div>
        <!--End of reply comment part-->
      </div>
      <!--End of parent comment-->

      <!--New Comment-->
      <div class="your-comment">
        <div class="comment-item row">
          <div class="comment-user-avatar">
            <img
              v-bind:src="linkSource + loginUser.avatar"
              class="rounded-circle"
              alt=""
              width="42"
            />
          </div>
          <div class="comment-detail col-10">
            <div class="comment-user-name">
              {{ loginUser.fullName }}
            </div>
            <div class="row comment-reply">
              <input
                id="comment-input-box"
                type="text"
                editId=""
                name="commentComment"
                class="comment-text d-flex p-2 col-9"
                placeholder="Comment.............."
                v-on:keyup="CommentByEnter"
                required
              />
              <button
                class="btn btn-reply"
                v-on:click="addComment"
                v-if="!editParentShow"
              >
                Comment
              </button>
              <button
                class="btn btn-reply"
                v-on:click="editParentCommentById"
                v-else
              >
                Edit
              </button>
              <p class="comment-alert is-hide" style="color: red">
                Comment cannot be blank
              </p>
            </div>
          </div>
        </div>
      </div>
      <!--End of new Comment-->
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";
import moment from "moment";
export default {
  name: "CommentList",
  mixins: [commonHelper],
  data() {
    return {
      linkSource: null,
      comment: {},
      parent_comments: [],
      children_comments: [],
      editParentShow: false,
      totalComment: 0,
    };
  },
  created() {
    this.linkSource = UrlConstants.AvatarSource;
    this.connect();
    this.getCommentList();
  },
  destroyed() {
    this.disconnect();
  },
  methods: {
    getCommentList() {
      this.filter.contributionId = this.$route.params.id;
      axios
        .post(UrlConstants.Comment + "/filter", this.filter)
        .then((response) => {
          let comment_lists = response.data.data.list;
          this.parent_comments = comment_lists.filter(
            (e) => e.parentId === null
          );
          this.children_comments = comment_lists.filter(
            (e) => e.parentId !== null
          );
          this.totalComment = response.data.data.totalElements;
        })
        .catch((error) => {
          this.errors = error.response.data;
        });
    },
    calculateDate(time) {
      let commentTime = moment(time);
      let now = moment(new Date());
      let different = moment.duration(now.diff(commentTime))._data;

      //less than 1 minutes
      if (
        different.minutes == 0 &&
        different.hours == 0 &&
        different.days == 0
      ) {
        commentTime = "few seconds ago";
      }

      //less than 1 hour
      else if (
        different.minutes > 0 &&
        different.hours == 0 &&
        different.days == 0
      ) {
        commentTime = different.minutes + " minutes ago";
      }

      //less than 1 day
      else if (different.hours > 0 && different.days == 0) {
        commentTime = different.hours + " hours ago";

        //less than 10 days
      } else if (0 < different.days <= 7) {
        commentTime = different.days + " days ago";
      }
      //more than 10 days
      else {
        commentTime = moment(time).format("MM/DD/YYYY");
      }
      return commentTime;
    },
    addComment() {
      let commentValue = document.querySelector("#comment-input-box").value;
      if (commentValue !== "") {
        let data = {
          contributionId: this.$route.params.id,
          content: commentValue,
        };
        axios.post(UrlConstants.Comment + "/send", data);
        document.querySelector("#comment-input-box").value = "";
        this.getCommentList();
      } else {
        let classLists = document.querySelector(".comment-alert").classList;
        classLists.remove("is-hide");
        classLists.add("is-display");
      }
    },
    showEditCommentById(commentId, parentId, content) {
      //assigned value to reply input to update
      document.querySelector("#input-comment-" + parentId).value = content;
      document
        .querySelector("#input-comment-" + parentId)
        .setAttribute("editId", commentId);

      //change reply button to edit button
      document.querySelector("#btn-reply-" + parentId).style.cssText =
        "display: none";
      document.querySelector("#btn-edit-" + parentId).style.cssText =
        "display: block";
    },
    showEditParentCommentById(commentId, content) {
      document.querySelector("#comment-input-box").value = content;
      document
        .querySelector("#comment-input-box")
        .setAttribute("editId", commentId);
      this.editParentShow = true;
    },
    async editCommentById(commentId) {
      await this.confirmAlert("edit", "comment");
      if (this.confirmResult) {
        let data = {
          id: document
            .querySelector("#input-comment-" + commentId)
            .getAttribute("editId"),
          content: document.querySelector("#input-comment-" + commentId).value,
        };
        axios.post(UrlConstants.Comment + "/update", data).then((r) => {
          this.successAlert();
          this.getCommentList();
          //reset reply input
          document
            .querySelector("#input-comment-" + commentId)
            .setAttribute("editId", "");
          document.querySelector("#input-comment-" + commentId).value = "";
          //change edit button to reply button
          document.querySelector("#btn-reply-" + commentId).style.cssText =
            "display: none";
          document.querySelector("#btn-edit-" + commentId).style.cssText =
            "display: block";
          this.editParentShow = false;
        });
      }
    },
    async editParentCommentById() {
      await this.confirmAlert("edit", "comment");
      if (this.confirmResult) {
        let data = {
          id: document
            .querySelector("#comment-input-box")
            .getAttribute("editId"),
          content: document.querySelector("#comment-input-box").value,
        };
        axios.post(UrlConstants.Comment + "/update", data).then((r) => {
          this.successAlert();
          this.getCommentList();
          //reset reply input
          document
            .querySelector("#comment-input-box")
            .setAttribute("editId", "");
          document.querySelector("#comment-input-box").value = "";
          //change edit button to reply button
          this.editParentShow = false;
        });
      }
    },
    async deleteCommentById(commentId) {
      await this.confirmAlert("delete", "comment");
      if (this.confirmResult) {
        let data = {
          id: commentId,
        };
        axios.post(UrlConstants.Comment + "/delete", data).then((r) => {
          this.successAlert();
          this.getCommentList();
        });
      }
    },
    showReplyCommentInput(commentId) {
      let classLists = document.querySelector("#reply-comment-" + commentId)
        .classList;
      classLists.remove("is-hide");
      classLists.add("is-display");
    },
    showReplyComments(commentId) {
      let classLists = document.querySelector("#comment-" + commentId)
        .classList;
      classLists.remove("is-hide");
      classLists.add("is-display");
      this.showReplyCommentInput(commentId);
    },
    replyCommentParent(parentId) {
      let content = document.querySelector("#input-comment-" + parentId).value;
      let data = {
        contributionId: this.$route.params.id,
        parentId: parentId,
        content: content,
      };
      axios.post(UrlConstants.Comment + "/send", data);
      this.showReplyComments(parentId);
    },
    CommentByEnter(e) {
      // Number 13 is the "Enter" key on the keyboard
      if (e.keyCode === 13) {
        console.log(document.querySelector("#comment-input-box").value);
        // Trigger the button element with a click
        this.addComment();
      }
    },
    connect() {
      this.stompClient = Stomp.over(
        new SockJS(UrlConstants.BaseUrl + "/stomp")
      );
      this.stompClient.connect(
        {
          Authorization: "Bearer " + this.$cookies.get("jwt"),
        },
        (frame) => {
          this.connected = true;
          this.stompClient.subscribe(
            "/channel/contribution/" + this.$route.params.id,
            (tick) => {
              this.getCommentList();
            }
          );
        },
        (error) => {
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
  },
};
</script>

<style scoped>
.comment-action-item {
  display: inline;
  font-size: 12px;
  margin-left: 5px;
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer;
}
.comment-total {
  font-weight: bold;
}
.is-hide {
  display: none;
}
.is-display {
  display: block !important;
}
.comment-children {
  margin-left: 3.5rem;
}
.your-reply-comment {
  margin-left: 3.5rem;
}
.comment-item {
  margin-top: 1rem;
}
.comment-action {
  margin-left: 2.5rem;
}
.comment-user-name {
  font-size: 1.2rem;
  font-weight: bold;
}
.comment-time {
  font-size: 0.8rem;
  font-weight: normal;
}
.comment-reply {
  margin-left: 0.1rem;
}
.btn-reply {
  margin-left: 10px;
  background-color: #3f6ad8;
  color: white;
  border-radius: 10px;
}
.comment-text {
  border: 1px solid #ced4da;
  border-radius: 10px;
  padding: 0.7rem;
}
.btn-edit {
  display: none;
  margin-left: 10px;
  background-color: #3f6ad8;
  color: white;
  border-radius: 10px;
}
</style>