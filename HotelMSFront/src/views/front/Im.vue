<template>
  <div class="chat-page">
    <div class="chat-container">
      <div class="header">
        <h2>在线咨询</h2>
      </div>
      <div class="content">
        <div class="user-list">
          <h3 @click="BackToPublic()">在线用户</h3>
          <h4 class="instruction" @click="BackToPublic()">（点击您需要对话的用户）</h4>
          <ul>
            <li
              v-for="user in filteredUserList"
              :key="user"
              @click="selectUser(user)"
              :class="{ selected: selectedUser === user }"
            >
              {{ user }}
            </li>
          </ul>
        </div>
        <div class="chat-window">
          <div ref="messagesContainer" class="messages">
            <div
              v-for="msg in messages"
              :key="msg.id"
              :class="[
                msg.from === username ? 'my-message' : 'other-message'
              ]"
            >
              <div
                :class="{
                  'message-header': true,
                  'my-message-header': msg.from === username,
                  'other-message-header': msg.from !== username,
                }"
              >
                <span class="message-username">{{ msg.from }}</span>
                <span class="message-time">{{ msg.time }}</span>
              </div>
              <div class="message-bubble">{{ msg.msg }}</div>
            </div>
          </div>
          <div v-if="selectedUser" class="current-user">私信给: {{ selectedUser }}</div>
          <div class="message-input-container">
            <input
              v-model="newMessage"
              @keyup.enter="sendMessage"
              placeholder="输入消息..."
              class="message-input"
            />
            <button @click="sendMessage" class="send-button">发送</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      socket: null,
      messages: [],
      newMessage: "",
      userList: [],
      username: "", // 当前用户的用户名
      selectedUser: null, // 选中的用户，用于私信
    };
  },
  created() {
    const storedUser = JSON.parse(localStorage.getItem("xm-user") || "{}");
    if (storedUser && storedUser.name) {
      this.username = storedUser.name;
      this.connectWebSocket();
    } else {
      this.$router.push("/login");
    }
  },
  beforeDestroy() {
    if (this.socket) {
      this.socket.close();
    }
  },
  watch: {
    messages() {
      // 每当消息变化，滚动到底部
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },
  },
  mounted() {
    // 初始挂载时滚动到底部
    this.scrollToBottom();
  },
  updated() {
    // 数据更新后确保滚动
    this.scrollToBottom();
  },
  computed: {
    filteredUserList() {
      return this.userList.filter((user) => user !== this.username);
    },
  },
  methods: {
    connectWebSocket() {
      this.socket = new WebSocket(`ws://localhost:9091/socket/${this.username}`);
      this.socket.onopen = () => {
        console.log("WebSocket 已连接");
      };
      this.socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        if (data.userNames) {
          this.userList = data.userNames;
        } else {
          if (data.from === this.username || this.selectedUser === data.from) {
            this.messages.push(data);
          } else {
            console.log(`来自${data.from}的新消息`);
            alert(`来自 ${data.from} 的新消息: ${data.msg}`);
          }
        }
      };
      this.socket.onclose = () => {
        console.log("WebSocket 已关闭");
      };
      this.socket.onerror = (error) => {
        console.error("WebSocket 发生错误: ", error);
      };
    },
    sendMessage() {
      if (!this.newMessage.trim()) {
        alert("发送消息不能为空");
        return;
      }
      const message = {
        time: new Date().toLocaleTimeString(),
        to: this.selectedUser,
        from: this.username,
        msg: this.newMessage,
      };
      this.socket.send(JSON.stringify(message));
      this.newMessage = "";
    },
    selectUser(user) {
      this.selectedUser = user;
      this.messages = [];
      console.log("私信给:", user);
      this.requestChatHistory(user);
    },
    requestChatHistory(user) {
      const message = {
        time: new Date().toLocaleTimeString(),
        to: user,
        from: this.username,
        msg: "history",
      };
      this.socket.send(JSON.stringify(message));
    },
    BackToPublic() {
      this.selectedUser = null;
      this.messages = [];
    },
    scrollToBottom() {
      const container = this.$refs.messagesContainer;
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    },
  },
};
</script>

<style scoped>
.chat-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffffff;
}

.chat-container {
  display: flex;
  flex-direction: column;
  width: 850px;
  height: 600px;
  border: 1px solid #007bff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: -90px;
  background-color: white;
}

.header {
  background-color: #007bff;
  color: white;
  padding: 15px;
  text-align: center;
}

.content {
  display: flex;
  flex-grow: 1;
  overflow: hidden; /* 防止内容溢出 */
}

.user-list {
  width: 30%;
  padding: 10px;
  background-color: #f1f3f5;
  border-right: 1px solid #007bff;
  overflow-y: auto;
}

.user-list h3 {
  cursor: pointer;
  text-align: center;
}

.user-list ul {
  list-style: none;
  padding: 0;
}

.user-list h4.instruction {
  cursor: pointer;
  text-align: center;
  font-weight: normal;
  margin: 10px 0;
}

.user-list li {
  padding: 10px;
  cursor: pointer;
  transition: background 0.3s;
  border-bottom: 1px solid #dcdcdc;
}

.user-list li.selected {
  background-color: rgba(255, 255, 255, 0.5);
  color: #007bff;
}

.user-list li:last-child {
  border-bottom: none;
}

.user-list li:hover {
  background-color: rgba(100, 100, 255, 0.2);
}

.chat-window {
  width: 70%;
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.messages {
  flex-grow: 1;
  overflow-y: auto; /* 允许消息部分滚动 */
  background-color: #ffffff;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.my-message {
  text-align: right;
}

.other-message {
  text-align: left;
}

.message-bubble {
  display: inline-block;
  padding: 10px 15px;
  border-radius: 15px;
  max-width: 60%;
  margin: 5px 0;
}

.my-message .message-bubble {
  background-color: #007bff;
  color: white;
}

.other-message .message-bubble {
  background-color: #f1f1f1;
  color: black;
}

.message-header {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #666;
}

.my-message-header {
  justify-content: flex-end;
}

.other-message-header {
  justify-content: flex-start;
}

.message-username {
  font-weight: bold;
}

.message-time {
  margin-left: 10px;
}

.current-user {
  font-weight: bold;
  margin: 10px 0;
}

.message-input-container {
  display: flex;
  margin-top: 10px;
  align-items: center; /* 垂直对齐项居中 */
}

.message-input {
  flex-grow: 1;
  height: 50px;
  border: none;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px 0 0 5px;
  outline: none;
  font-size: 16px;
}


.send-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 0 5px 5px 0;
  transition: background 0.3s;
}

.send-button:hover {
  background-color: #0056b3;
}
</style>
