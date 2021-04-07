<template>
  <div class="ui-theme-settings">
    <button
      type="button"
      id="TooltipDemo"
      class="btn-open-options btn btn-warning"
      v-on:click="showChatBox"
    >
      <i class="fas fa-comments fa-w-16 fa-2x"></i>
    </button>
    <div class="row">
      <div class="theme-settings__inner" id="general-chat" v-if="showChat">
        <div class="row">
          <div class="col-12">
            <div class="chat-search-section">
              <input type="text" placeholder="search" class="form-control" />
              <i class="fas fa-search chat-search"></i>
            </div>
          </div>
          <div class="col-12">
            <div class="list-chat">
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
              <div class="chat-item row">
                <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="42"
                  />
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">Simon</p>
                  <p class="last-chat">Hello Simon handsomeeeeeeeeeeeeeeee</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="theme-settings__inner" id="specific-chat">
        <div class="row">
          <div class="col-4">tổng</div>
          <div class="col-8">riêng</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "ChatBox",
  data() {
    return {
      showChat: false,
    };
  },
  methods: {
    showChatBox() {
      if (this.showChat) {
        this.showChat = false;
      } else {
        this.showChat = true;
      }
    },
  },
};
</script>

<style scoped>
#general-chat {
  width: 300px !important;
  border-radius: 20px 0;
  padding: 20px 10px 10px 10px;
}
#specific-chat {
  margin-right: 30px;
  border-radius: 0 20px 20px 0;
}
.theme-settings__inner {
  margin-top: 20px;
  background: lightgrey;
}
.list-chat {
  background-color: white;
  margin-top: 10px;
  border-radius: 5px;
  padding: 10px;
  overflow-y: scroll;
  overflow-x: hidden;
}
.chat-search {
  float: right;
  margin-top: -25px;
  margin-right: 10px;
}
.chat-item{
    margin-bottom: 15px;
    padding: 5px;
}
.chat-item:hover {
    background-color: beige;
}
p {
  margin-bottom: 0;
}
.last-chat {
  font-size: smaller;
}
</style>