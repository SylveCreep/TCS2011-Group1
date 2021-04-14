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
      <div class="theme-settings__inner" id="general-chat" v-show="showChat">
        <div class="row">
          <div class="col-12">
            <div class="chat-search-section">
              <input
                type="text"
                placeholder="search"
                class="form-control search-input"
                v-model="filter.fullName"
                v-on:keyup="searchStudent"
              />
              <i
                class="fas fa-search chat-search"
                v-on:click="searchStudent"
              ></i>
            </div>
          </div>
          <div class="col-12">
            <div class="list-chat">
              <div
                class="chat-item row"
                v-for="(user, index) of list_users"
                :key="user.id"
                :id="'chat-item-' + (index + 1)"
                v-on:click="getTargetContact(user)"
              >
                <div class="col-2 chat-avatar">
                  <img
                    v-bind:src="linkSource + user.avatar"
                    class="rounded-circle"
                    id="small-circle"
                    alt=""
                    width="42"
                  />
                  <i
                    class="fas fa-circle small-online-status"
                    v-if="user.isOnline"
                  ></i>
                  <i class="fas fa-circle small-offline-status" v-else></i>
                </div>
                <div class="col-10 chat-detail">
                  <p class="chat-name">{{ user.fullName }}</p>
                  <!--<p class="last-chat">Hello Simon</p>-->
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
            <h3 class="chat-name">{{ targetUser.fullName }}</h3>
            <p class="online-status" v-if="targetUser.isOnline">
              <span class="fas fa-circle"></span> Online
            </p>
            <p class="offline-status" v-else>
              <span class="fas fa-circle"></span> Offline
            </p>
          </div>
        </div>
        <div class="main-message">
          <div class="row" id="message-list">
            <div
              class="col-12"
              v-for="message of list_messages"
              :key="message.id"
            >
              <div
                class="message-item message-left"
                v-if="message.toUserId === loginUser.id"
              >
                <p class="d-inline-flex p-2">
                  {{ message.content }}
                </p>
              </div>
              <div
                class="message-item message-right"
                v-if="message.fromUserId === loginUser.id"
              >
                <p class="d-inline-flex p-2">
                  {{ message.content }}
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="message-input">
          <div class="row">
            <div class="col-10">
              <textarea
                id="comment-input-box"
                type="text"
                v-model="message.content"
                name="commentComment"
                class="message-text d-flex p-2"
                placeholder="Chat here.............."
              >
              </textarea>
            </div>
            <div class="col-2">
              <button class="btn btn-message" v-on:click="sendMessage">
                <i class="fas fa-paper-plane"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { commonHelper } from "@/helper/commonHelper";
import { UrlConstants } from "@/constant/UrlConstant";
import { DefaultConstants } from "@/constant/DefaultConstant";
export default {
  name: "ChatBox",
  mixins: [commonHelper],
  data() {
    return {
      message: {},
      showChat: false,
      chatInput: "",
      list_messages: [],
      toUserId: null,
      connected: false,
      linkSource: null,
      targetUser: {}
    };
  },
  created() {
    this.linkSource = UrlConstants.AvatarSource
    this.connect();
    this.getContactList();
  },
  destroyed() {
    this.disconnect();
  },
  methods: {
    showChatBox() {
      if (this.showChat) {
        this.showChat = false;
      } else {
        this.showChat = true;
        document.querySelector('#chat-item-1').click();
      }
    },
    getTargetContact(user) {
      this.targetUser = user
      this.getMessageList(user.id)
    },
    getMessageList(userId) {
      this.filter.limit = 100;
      this.toUserId = userId;
      this.filter.toUser = userId;
      axios.post(UrlConstants.Chat + "/get", this.filter).then((r) => {
        this.list_messages = r.data.data.chatMessageResponses;
      });
    },
    sendMessage() {
      if (this.message.content !== "") {
        (this.message.toUser = this.toUserId),
          axios.post(UrlConstants.Chat + "/send", this.message).then((r) => {
            this.getMessageList(this.toUserId);
            this.message.content = "";
          });
      }
    },
    getContactList() {
       this.filter.limit = 15;
       this.filter.facultyId = this.loginUser.facultyId;
      if (this.loginUser.roleId === DefaultConstants.Role.MarketingCoordinator) {
        this.filter.roleId = DefaultConstants.Role.Student;
      }
      if (this.loginUser.roleId === DefaultConstants.Role.Student) {
        this.filter.roleId = DefaultConstants.Role.MarketingCoordinator;
      }
       this.getUserList();
    },
    searchStudent() {
      this.filter.page = DefaultConstants.firstPage;
      this.getContactList();
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
          console.log(frame);
          this.connected = true;
          this.stompClient.subscribe("/user/queue/chat", (tick) => {
            this.getMessageList(this.toUserId);
            this.getContactList();
          });
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
.is-hide {
  display: none;
}
.is-display {
  display: inline !important;
}
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
  height: 40rem;
}
.list-chat {
  background-color: white;
  border-radius: 0px 0 0 20px;
  padding: 10px;
  overflow-y: scroll;
  overflow-x: hidden;
  height: 600px;
  margin-top: -10px;
  z-index: 2;
  position: relative;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
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
  cursor: pointer;
}
.chat-item {
  margin-bottom: 15px;
  padding: 5px;
}
.chat-item:hover {
  background-color: #e0f3ff;
  cursor: pointer;
}
h3 {
  margin-bottom: 0px;
}
p {
  margin-bottom: 0;
}
.small-online-status {
  z-index: 2;
  position: relative;
  font-size: 15px;
  margin-left: 30px;
  color: green;
}
.small-offline-status {
  z-index: 2;
  position: relative;
  font-size: 15px;
  margin-left: 30px;
}
.fa-circle {
  font-size: 10px;
}
.last-chat {
  font-size: smaller;
}
.main-message {
  overflow-y: scroll;
  overflow-x: hidden;
}
#message-list {
  padding: 15px;
  height: 450px;
}
.message-item {
  display: block;
}
.message-item p {
  background-color: #f5f5f5;
  padding: 10px !important;
  margin: 5px;
  width: auto;
  margin-bottom: 10px;
  border-radius: 20px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}
#small-circle {
  z-index: 1;
  position: relative;
  margin-bottom: -20px;
}
.message-right {
  display: block;
  float: right;
}
.detail-chat-name {
  background-color: #ffffff;
  padding: 30px 20px 20px 20px;
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  margin-right: 1px;
}
.online-status {
  color: green;
  margin-top: 0px;
}
.offline-status {
  margin-top: 0px;
}
.message-input {
  height: 70px;
  background-color: #ffffff;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 0 0 20px 0;
}
.message-text {
  height: 70px;
  width: 450px;
  border-radius: 0 0 20px 0;
  border: none !important;
}
.btn-message {
  margin: 0px;
}
.fa-paper-plane {
  font-size: 30px;
  margin-top: 10px;
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
  background: #888 !important;
  border-radius: 20px;
  height: 30px;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>