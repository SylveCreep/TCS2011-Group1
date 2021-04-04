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
export default {
  name: "CommentList",
  data() {
    return {
      newComment: {},
      commentList: [],
    };
  },
};
</script>

<style scoped>
.comment-total {
  font-weight: bold;
}
.comment-children {
  margin-left: 4.5rem
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