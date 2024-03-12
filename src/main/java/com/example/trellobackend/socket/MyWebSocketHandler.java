package com.example.trellobackend.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý thông điệp nhận được từ client
        String payload = message.getPayload();
        // ...
        session.sendMessage(new TextMessage("Server received your message: " + payload));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Khi một kết nối WebSocket được thiết lập
        System.out.println("Client connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Khi một kết nối WebSocket bị đóng
        System.out.println("Client disconnected");
    }
}
