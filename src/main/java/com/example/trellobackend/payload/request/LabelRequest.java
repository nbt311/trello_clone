package com.example.trellobackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabelRequest {
    private Long cardId;
    private Long labelId;
    private String name;
    private String color;
}