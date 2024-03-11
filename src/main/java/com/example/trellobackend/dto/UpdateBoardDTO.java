package com.example.trellobackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBoardDTO {
    private List<Long> columnOrderIds;
}
