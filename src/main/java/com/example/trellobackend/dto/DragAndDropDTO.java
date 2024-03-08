package com.example.trellobackend.dto;

import lombok.Data;

import java.util.List;
@Data
public class DragAndDropDTO {
    private Long currentCardId;
    private Long prevColumnId;
    private List<Long> prevCardOrderIds;
    private Long nextColumnId;
    private List<Long> nextCardOrderIds;
}
