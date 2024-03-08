package com.example.trellobackend.dto;

import lombok.Data;

import java.util.List;
@Data
public class UpdateColumnDTO {
    private List<Long> cardOrderIds;
}
