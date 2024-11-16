package com.example.mapper;

import com.example.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryMapper {

    void insertChatHistory(ChatHistory chatHistory);

    List<ChatHistory> loadChatHistory(String user1, String user2);

    List<String> getChatParticipants(String username);

}
