package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.enums.EBoardMemberRole;
import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.enums.WorkSpaceType;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.BoardMembers;
import com.example.trellobackend.models.board.Visibility;
import com.example.trellobackend.models.workspace.Members;
import com.example.trellobackend.models.workspace.Type;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VisibilityRepository visibilityRepository;
    @Autowired
    private BoardMembersRepository boardMembersRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public Iterable<Board> findAll() {
        return null;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Board board) {

    }

    @Override
    public void remove(Long id) {

    }


    @Override
    public Board createBoard(BoardRequest boardRequest) {
        Optional<User> userOptional = userRepository.findByEmail(boardRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(boardRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()){
                Workspace workspace = workspaceOptional.get();
                Board board = new Board();
                board.setTitle(boardRequest.getTitle());
                board.setWorkspace(workspace);
                Set<String> strVisibilities = boardRequest.getVisibility();
                Set<Visibility> visibilities = new HashSet<>();
                strVisibilities.forEach(visibility -> {
                    switch (visibility) {
                        case "private":
                            Visibility privateVisibility = visibilityRepository.findByName(EBoardVisibility.PRIVATE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(privateVisibility);

                            break;
                        case "public":
                            Visibility publicVisibility = visibilityRepository.findByName(EBoardVisibility.PUBLIC)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(publicVisibility);

                            break;
                        default:
                            Visibility workspaceVisibility = visibilityRepository.findByName(EBoardVisibility.WORKSPACE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(workspaceVisibility);
                    }
                });
                board.setVisibilities(visibilities);
                boardRepository.save(board);
                addMemberToBoard(board,creator,UserRole.ROLE_ADMIN);
                return board;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public BoardResponseDTO getBoardById(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();

            // Fetch Columns associated with the Board
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

            BoardResponseDTO responseDTO = BoardResponseDTO.fromEntity(board);
            responseDTO.setColumns(columnsDTOList);
            responseDTO.setColumnIds(board.getColumnOrderIds());

            return responseDTO;
        }
        throw new NoSuchElementException("Board not found");
    }



    @Override
    public BoardResponseDTO createNewBoard(BoardRequest boardRequest) {
        Optional<User> userOptional = userRepository.findByEmail(boardRequest.getEmail());
        if (userOptional.isPresent()) {
            User creator = userOptional.get();
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(boardRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()) {
                Workspace workspace = workspaceOptional.get();
                Board board = new Board();
                board.setTitle(boardRequest.getTitle());
                board.setWorkspace(workspace);
                Set<String> strVisibilities = boardRequest.getVisibility();
                Set<Visibility> visibilities = new HashSet<>();
                strVisibilities.forEach(visibility -> {
                    switch (visibility) {
                        case "private":
                            Visibility privateVisibility = visibilityRepository.findByName(EBoardVisibility.PRIVATE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(privateVisibility);
                            break;
                        case "public":
                            Visibility publicVisibility = visibilityRepository.findByName(EBoardVisibility.PUBLIC)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(publicVisibility);
                            break;
                        default:
                            Visibility workspaceVisibility = visibilityRepository.findByName(EBoardVisibility.WORKSPACE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(workspaceVisibility);
                    }
                });
                board.setVisibilities(visibilities);
                boardRepository.save(board);
                addMemberToBoard(board, creator, UserRole.ROLE_ADMIN);

                // Create a response DTO
                BoardResponseDTO responseDTO = new BoardResponseDTO();
                responseDTO.setId(board.getId());
                responseDTO.setTitle(board.getTitle());
                responseDTO.setColumns(Collections.emptyList()); // Initialize the list of Columns
                responseDTO.setColumnIds(Collections.emptyList()); // Initialize the columnIds list

                return responseDTO;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public List<ColumnsDTO> getAllColumnsDTOByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            // Chuyển đổi danh sách cột sang danh sách DTO và trả về
            return board.getColumns().stream()
                    .map(column -> new ColumnsDTO(column.getId(), column.getTitle()))
                    .collect(Collectors.toList());
        } else {
            // Nếu không tìm thấy bảng, có thể xử lý theo ý của bạn, ví dụ: ném một ngoại lệ.
            throw new RuntimeException("Không tìm thấy bảng với ID: " + boardId);
        }
    }

    public void addMemberToBoard(Board board, User user, UserRole userRole) {
        BoardMembers boardMembers = new BoardMembers();
        boardMembers.setBoard(board);
        boardMembers.setUser(user);
        boardMembers.setRole(userRole);
        boardMembersRepository.save(boardMembers);
    }
}
