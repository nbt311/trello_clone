package com.example.trellobackend.payload.request;

import com.example.trellobackend.models.board.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardRequest {
    private String email;
    private String title;
    private Long workspaceId;
    private Set<String> visibility;
}