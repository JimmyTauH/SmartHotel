package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.entity.ChatHistory;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服务器
 */
@Component
@ServerEndpoint("/socket/{username}")
public class WebSocketService {

    // 存储当前连接的用户会话
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private static ChatHistoryService chatHistoryService;

    private static String recep_name = "服务员";

    @Autowired
    public void setChatHistoryService(ChatHistoryService chatHistoryService) {
        WebSocketService.chatHistoryService = chatHistoryService;
    }

    // 建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("连接成功");
        System.out.println("当前用户名: " + username);
        sessionMap.put(username, session);
        sendMessage(setUserList(username),sessionMap.get(username));
        //sendMessage(setUserList("前台"),sessionMap.get("前台"));
    }

    // 关闭连接
    @OnClose
    public void onClose(@PathParam("username") String username) {
        sessionMap.remove(username);
        //sendAllMessage(setUserList(username));
    }

    // 接收处理客户端发来的数据
    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到消息");
        Message msg = JSON.parseObject(message, Message.class);
        if (StringUtils.isEmpty(msg.getTo())) {
            sendAllMessage(JSON.toJSONString(msg));
        } else if ("history".equals(msg.getMsg())) { // 请求历史记录
            handleChatHistoryRequest(msg);
        } else {
            Session sessionTo = sessionMap.get(msg.getTo());
            sendMessage(message, sessionTo);
            sendMessage(message, sessionMap.get(msg.getFrom()));

            // 保存聊天记录到数据库
            chatHistoryService.saveChat(msg.getFrom(), msg.getTo(), msg.getMsg());
            sendMessage(setUserList(msg.getTo()),sessionMap.get(msg.getTo()));
        }
    }

    // 处理聊天记录请求
    private void handleChatHistoryRequest(Message msg) {
        String user1 = msg.getFrom(); // 当前用户
        String user2 = msg.getTo();   // 请求的目标用户

        // 获取聊天记录
        List<ChatHistory> history = chatHistoryService.loadChatHistory(user1, user2);
            for (ChatHistory chat : history) {
            Message historyMessage = new Message();
            historyMessage.setFrom(chat.getFrom());
            historyMessage.setTo(chat.getTo());
            historyMessage.setMsg(chat.getMessage());
            historyMessage.setTime(chat.getTimestamp().toString());

            // 发送历史消息给请求者
            sendMessage(JSON.toJSONString(historyMessage), sessionMap.get(user1));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    // 设置用户列表
    private String setUserList(String currentUsername) {
        ArrayList<String> list = new ArrayList<>();

        // 使用 chatHistoryService 获取当前用户的对话参与者
        List<String> participants = chatHistoryService.getChatParticipants(currentUsername);
        list.addAll(participants); // 添加对话的另一方用户

        // 确保 "前台" 始终存在
        if (!list.contains(recep_name)) {
            list.add(0, recep_name);
        }

        // 创建 Message 并返回 JSON
        Message message = new Message();
        message.setUserNames(list);
        return JSON.toJSONString(message);
    }


//    // 设置用户列表
//    private String setUserList() {
//        ArrayList<String> list = new ArrayList<>();
//        for (String key : sessionMap.keySet()) {
//            list.add(key);
//        }
//        if (!list.contains("前台")) {
//            list.add(0, "前台");
//        }        Message message = new Message();
//        message.setUserNames(list);
//            return JSON.toJSONString(message);
//    }

    // 服务端发送消息给指定客户端
    private void sendMessage(String message, Session toSession) {
        try {
            if (toSession != null) {
                toSession.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 服务端发送消息给所有客户端
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



//package com.example.chat.component;
//
//
//import com.alibaba.fastjson.JSON;
//import com.example.entity.Message;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.util.StringUtils;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//import com.example.service.ChatHistoryService; // 添加的导入
//import com.example.entity.ChatHistory; // 添加的导入
//
//
///**
// * 注意在websocket通信中只能传string
// */
//@Component
//@ServerEndpoint("/socket/{username}")
//public class WebSocketServer {
////    存储当前对象
//    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
//
//// 新增
//    private static ChatHistoryService chatHistoryService = new ChatHistoryService();
//
//
////    建立连接
//    /***
//     * 1.把登录用户存到sessionMap中
//     * 2.发送给所有人当前登录人员信息
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("username") String username) {
//        System.out.println("当前用户名=="+username);
//        sessionMap.put(username, session);
//        // 发送登录人员消息给所有的客户端
//        sendAllMessage(setUserList());
//
//        //
//
//
//    }
//
//    //关闭连接
//    /**
//     * 1.把登出的用户从sessionMap中剃除
//     * 2.发送给所有人当前登录人员信息
//     */
//    @OnClose
//    public void onClose(@PathParam("username") String username) {
//        sessionMap.remove(username);
//        sendAllMessage(setUserList());
//    }
//
//    /**
//     * 接收处理客户端发来的数据
//     */
//    @OnMessage
//    public void onMessage(String message) {
//        // 解析消息为 java 对象
//        Message msg = JSON.parseObject(message, Message.class);
//        if (StringUtils.isEmpty(msg.getTo())) {
//            sendAllMessage(JSON.toJSONString(msg));
//        } else if ("history".equals(msg.getMsg())) { // 请求历史记录
//            handleChatHistoryRequest(msg); // 调用请求聊天记录处理方法
//        } else {
//            Session sessionTo = sessionMap.get(msg.getTo());
//            sendMessage(message, sessionTo);
//
//            // 发送回发送者
//            sendMessage(message, sessionMap.get(msg.getFrom()));
//
//            // 保存聊天记录
//            chatHistoryService.saveChat(msg.getFrom(), msg.getTo(), msg.getMsg());
//        }
//    }
//
//    // 新增：处理聊天记录请求
//    private void handleChatHistoryRequest(Message msg) {
//        String user1 = msg.getFrom(); // 当前用户
//        String user2 = msg.getTo();   // 请求的目标用户
//
//        // 获取聊天记录
//        List<ChatHistory> history = chatHistoryService.loadChatHistory(user1, user2);
//        for (ChatHistory chat : history) {
//            Message historyMessage = new Message();
//            historyMessage.setFrom(chat.getFrom());
//            historyMessage.setTo(chat.getTo());
//            historyMessage.setMsg(chat.getMessage());
//            historyMessage.setTime(chat.getTimestamp().toString());
//
//            // 发送历史消息给请求者
//            sendMessage(JSON.toJSONString(historyMessage), sessionMap.get(user1));
//        }
//    }
//
//
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    private String setUserList(){
//        ArrayList<String> list = new ArrayList<>();
//        for(String key:sessionMap.keySet()){
//            list.add(key);
//        }
//        Message message = new Message();
//        message.setUserNames(list);
//        return JSON.toJSONString(message);
//    }
//
////    服务端发送消息给指定客户端
//    private void sendMessage(String message, Session toSession) {    //sendMessage(message,sessionTo);
//        try {
//            //toSession.getBasicRemote().sendText("6666");
//            toSession.getBasicRemote().sendText(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////   服务端发送消息给所有客户端
//    private void sendAllMessage(String message) {
//        try {
//            for (Session session : sessionMap.values()) {
//                session.getBasicRemote().sendText(message);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
