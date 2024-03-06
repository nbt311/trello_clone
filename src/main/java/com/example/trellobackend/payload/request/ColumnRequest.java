package com.example.trellobackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnRequest {
    private String email;
    private String title;
    private Long workspaceId;
    private Long boardId;
//    private List<Long> cardOrderIds;
}
