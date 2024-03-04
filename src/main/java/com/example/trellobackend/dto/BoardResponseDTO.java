package com.example.trellobackend.dto;

import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardResponseDTO {
    private Long id;
    private String title;
    private EBoardVisibility visibility;
    private List<Long> columnIds;
    private List<ColumnsDTO> columns;

    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }


    public static BoardResponseDTO fromEntity(Board board) {
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        responseDTO.setId(board.getId());
        responseDTO.setTitle(board.getTitle());

        List<ColumnsDTO> columnsDTOList = board.getColumns()
                .stream()
                .map(column -> {
                    ColumnsDTO columnsDTO = new ColumnsDTO();
                    columnsDTO.setId(column.getId());
                    columnsDTO.setTitle(column.getTitle());
                    // Map other properties as needed
                    return columnsDTO;
                })
                .collect(Collectors.toList());

        responseDTO.setColumns(columnsDTOList);
        responseDTO.setColumnIds(board.getColumnOrderIds());

        if (!board.getVisibilities().isEmpty()) {
            responseDTO.setVisibility(board.getVisibilities().iterator().next().getName());
        }
        return responseDTO;
    }
}
