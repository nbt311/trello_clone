package com.example.trellobackend.dto;

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
//    private EBoardVisibility visibility;
    private Set<Visibility> visibility;
    private List<Long> columnOrderIds;
    private List<ColumnsDTO> columns;

    public BoardResponseDTO(Board board, Set<Visibility> visibility, List<Long> columnOrderIds, List<ColumnsDTO> columns) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.visibility = visibility;
        this.columnOrderIds = columnOrderIds;
        this.columns = columns;
    }


    public static BoardResponseDTO fromEntity(Board board) {
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        responseDTO.setId(board.getId());
        responseDTO.setTitle(board.getTitle());

        List<ColumnsDTO> columnsDTOList = board.getColumns()
                .stream()
                .map(ColumnsDTO::fromEntity)
                .collect(Collectors.toList());

        responseDTO.setColumns(columnsDTOList);
        responseDTO.setColumnOrderIds(board.getColumnOrderIds());
        responseDTO.setVisibility(board.getVisibilities());

        return responseDTO;
    }
}
