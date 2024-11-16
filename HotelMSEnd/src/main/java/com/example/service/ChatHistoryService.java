package com.example.service;

import com.example.entity.ChatHistory;
import com.example.mapper.ChatHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatHistoryService {

    private final ChatHistoryMapper chatHistoryMapper;

    // 构造函数注入
    @Autowired
    public ChatHistoryService(ChatHistoryMapper chatHistoryMapper) {
        this.chatHistoryMapper = chatHistoryMapper;
    }

    public void saveChat(String from, String to, String message) {
        try {
            ChatHistory chatHistory = new ChatHistory(from, to, message, LocalDateTime.now());
            chatHistoryMapper.insertChatHistory(chatHistory);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息以帮助调试
        }
    }


    public List<ChatHistory> loadChatHistory(String user1, String user2) {
        try {
            return chatHistoryMapper.loadChatHistory(user1, user2);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace(); // 打印异常堆栈信息以进行调试
            return new ArrayList<>(); // 返回空列表
        }
    }

    public List<String> getChatParticipants(String username) {
        try {
            return chatHistoryMapper.getChatParticipants(username);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息
            return new ArrayList<>(); // 返回空列表
        }
    }

}





//package com.example.chat.service;
//
//import com.example.entity.ChatHistory;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ChatHistoryService {
//    // 存储每个用户的聊天历史记录
//    private static final Map<String, List<ChatHistory>> chatHistoryMap = new HashMap<>();
//
//    // 保存聊天记录
//    public void saveChat(String from, String to, String message) {
//        ChatHistory chatHistory = new ChatHistory(from, to, message, LocalDateTime.now());
//
//        // 定义聊天记录的键（只使用A到B的键）
//        String key = from + "_" + to;  // A到B的唯一键
//        String reverseKey = to + "_" + from; // B到A的唯一键
//
//        // 创建聊天记录列表（如果不存在）
//        chatHistoryMap.putIfAbsent(key, new ArrayList<>());
//        chatHistoryMap.putIfAbsent(reverseKey, new ArrayList<>());
//
//        // 检查是否已经存在这条记录以避免重复
//        if (!chatHistoryMap.get(key).contains(chatHistory)) {
//            chatHistoryMap.get(key).add(chatHistory); // 保存A到B的记录
//        }
//
//        if (!chatHistoryMap.get(reverseKey).contains(chatHistory)) {
//            chatHistoryMap.get(reverseKey).add(chatHistory); // 保存B到A的记录（确保双向存储）
//        }
//
//    }
//
//    /* // 载入聊天历史记录
//    public List<ChatHistory> loadChatHistory(String user1, String user2) {
//        String key = user1 + "_" + user2; // A到B
//        String reverseKey = user2 + "_" + user1; // B到A
//
//        List<ChatHistory> history = new ArrayList<>();
//        if (chatHistoryMap.containsKey(key)) {
//            history.addAll(chatHistoryMap.get(key));
//        }
//        if (chatHistoryMap.containsKey(reverseKey)) {
//            history.addAll(chatHistoryMap.get(reverseKey));
//        }
//        return history;
//    } */
//   // 载入聊天历史记录
//   public List<ChatHistory> loadChatHistory(String user1, String user2) {
//        String key = user1 + "_" + user2; // A到B
//        String reverseKey = user2 + "_" + user1; // B到A
//
//        List<ChatHistory> history = new ArrayList<>();
//        if (chatHistoryMap.containsKey(key)) {
//            history.addAll(chatHistoryMap.get(key));
//            return history;
//        }
//        if (chatHistoryMap.containsKey(reverseKey)) {
//            history.addAll(chatHistoryMap.get(reverseKey));
//            return history;
//        }
//        return history;
//    }
//
//}
//
