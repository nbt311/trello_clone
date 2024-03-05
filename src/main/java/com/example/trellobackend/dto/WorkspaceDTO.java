package com.example.trellobackend.dto;

import com.example.trellobackend.models.workspace.Workspace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkspaceDTO {
    private Long id;
    private String name;
    private List<UserDTO> members;
    private List<BoardResponseDTO> boards;
    public WorkspaceDTO(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();

        this.members = workspace.getMembers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

        this.boards = workspace.getBoards().stream()
                .map(board -> new BoardResponseDTO(
                                board,
                                board.getVisibilities(),
                                board.getColumnOrderIds(),
                                board.getColumns().stream()
                                        .map(column -> new ColumnsDTO(column.getId(), column.getTitle()))
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());
    }
}
