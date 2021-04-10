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
              <input type="text" placeholder="search" class="form-control search-input" />
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
        <div class="row detail-chat-name">
            <div class="col-2 chat-avatar">
                  <img
                    src="assets/images/avatars/1.jpg"
                    class="rounded-circle"
                    alt=""
                    width="70"
                  />
                </div>
                <div class="col-10 px-3 chat-detail">
                  <h3 class="chat-name">Simon</h3>
                  <p class="status">Online</p>
                </div>
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
  border-radius: 20px 0 0 20px;
}
#specific-chat {
  margin-right: 30px;
  border-radius: 0 20px 20px 0;
}
.theme-settings__inner {
  margin-top: 20px;
  background: #e0f3ff;
  height:40rem
}
.list-chat {
  background-color: white;
  border-radius: 0px 0 0 20px;
  padding: 10px;
  overflow-y: scroll;
  overflow-x: hidden;
  height: 600px;
  margin-top:-10px;
  z-index: 2;
  position: relative;
}
.search-input {
  border-radius: 10px;
  height: 50px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  z-index: 3;
  position: relative;
}
.chat-search {
  float: right;
  margin-top: -30px;
  margin-right: 10px;
  position: relative;
  z-index: 3;
}
.chat-item{
    margin-bottom: 15px;
    padding: 5px;
}
.chat-item:hover {
    background-color: #e0f3ff;
}
p {
  margin-bottom: 0;
}
.last-chat {
  font-size: smaller;
}
.detail-chat-name {
  background-color: #ffffff;
  padding: 30px 20px 20px 20px;
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  margin-right: 1px;
}
::-webkit-scrollbar {
    width: 5px;
    
  }
  /* Track */
  ::-webkit-scrollbar-track {
    background: #fff; 
  }
   
  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: #888;
    border-radius: 20px;
    height: 30px 
  }
  
  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: #555; 
  }
</style>