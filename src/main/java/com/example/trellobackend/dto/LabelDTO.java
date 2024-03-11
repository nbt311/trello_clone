package com.example.trellobackend.dto;

import com.example.trellobackend.models.board.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabelDTO {
    private Long id;
    private String color;

    public LabelDTO (Label label){
        this.color = label.getColor();
    }
}
