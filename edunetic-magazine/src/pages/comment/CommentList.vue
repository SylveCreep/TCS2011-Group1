<template>
  <div class="app-main__inner" style="background-color: #fff">
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
          <div class="your-comment">
              <div class="comment-item row">
            <div class="comment-user-avatar">
              <img
                v-bind:src="avatarUrl"
                class="rounded-circle"
                alt=""
                width="42"
              />
            </div>
            <div class="comment-detail col-10">
              <div class="comment-user-name">
                {{loginUser.fullName}}
              </div>
              <div class="row comment-reply">
                <input type="text" name="replyComment" v-model="replyComment" class="comment-text d-flex p-2 col-10" placeholder="Reply..........">
                <button class="btn btn-reply" v-on:click="replyCommentParent" >Reply</button>
              </div>
                
            </div>
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
       <div class="your-comment">
              <div class="comment-item row">
            <div class="comment-user-avatar">
              <img
                v-bind:src="avatarUrl"
                class="rounded-circle"
                alt=""
                width="42"
              />
            </div>
            <div class="comment-detail col-10">
              <div class="comment-user-name">
                {{loginUser.fullName}}
              </div>
              <div class="row comment-reply">
                <input type="text" name="commentComment" v-model="newComment" class="comment-text d-flex p-2 col-9" placeholder="Comment..............">
                <button class="btn btn-reply" v-on:click="addComment">Comment</button>
              </div>
                
            </div>
          </div>
          </div>
    </div>
  </div>
</template>

<script>
import { UrlConstants } from "@/constant/UrlConstant";
import axios from "axios";

export default {
  name: "CommentList",
  data() {
    return {
      commentList: [],
      avatarUrl: null,
      loginUser: {},
      newComment: null,
      replyComment: null,
    };
  },
  created() {
    this.getLoginUser()
  },
  methods: {
    getLoginUser() {
      this.loginUser = this.$cookies.get("loginUser")
    this.avatarUrl= UrlConstants.AvatarSource + this.$cookies.get("loginUser").avatar
    },
    addComment() {
        console.log(this.newComment)
    },
    replyCommentParent() {
      console.log(this.replyComment)
    }
  }
};
</script>

<style scoped>
.comment-total {
  font-weight: bold;
}
.comment-children {
  margin-left: 3.5rem;
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
.comment-reply {
  margin-left: 0.1rem
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
</style>