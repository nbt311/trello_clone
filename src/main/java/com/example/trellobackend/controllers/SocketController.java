package com.example.trellobackend.controllers;

import com.example.trellobackend.socket.CardListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public CardListener sendMessage(@Payload CardListener cardListener) {
        CardListener notificationMessage = new CardListener();
        notificationMessage.setUsername(cardListener.getUsername());
        notificationMessage.setUserAvatar(cardListener.getUserAvatar());
//        notificationMessage.setContent(cardListener.getContent());
        notificationMessage.setNotification(cardListener.getUsername() + " đã thêm 1 bình luận mới");

        return notificationMessage;
    }
}
