package com.example.controller;

import com.example.entity.ChatHistory;
import com.example.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatHistoryService chatHistoryService;

    @Autowired
    public ChatController(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    @PostMapping("/history")
    public void saveChat(@RequestParam String from, @RequestParam String to, @RequestParam String message) {
        chatHistoryService.saveChat(from, to, message);
    }

    @GetMapping("/history")
    public List<ChatHistory> loadChatHistory(@RequestParam String user1, @RequestParam String user2) {
        return chatHistoryService.loadChatHistory(user1, user2);
    }

    @GetMapping("/participants")
    public List<String> getChatParticipants(@RequestParam String username) {
        return chatHistoryService.getChatParticipants(username);
    }
}
