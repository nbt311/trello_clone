package com.example.trellobackend.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
public class CardListener {
    private String username;
    private String userAvatar;
    private String content;
    private String notification;
}
