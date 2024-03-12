package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.enums.MemberRole;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.Role;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.*;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

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
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    public BoardResponseDTO getBoardById(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            return BoardResponseDTO.fromEntity(board);
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
                        case "PRIVATE":
                            Visibility privateVisibility = visibilityRepository.findByName(EBoardVisibility.PRIVATE)
                                    .orElseThrow(() -> new RuntimeException("Error: Visibility is not found."));
                            visibilities.add(privateVisibility);
                            break;
                        case "PUBLIC":
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
                addMemberToBoard(board, creator, MemberRole.ADMIN);

                // Create a response DTO
                BoardResponseDTO responseDTO = new BoardResponseDTO();
                responseDTO.setId(board.getId());
                responseDTO.setTitle(board.getTitle());
                responseDTO.setVisibility(board.getVisibilities());
                responseDTO.setColumns(Collections.emptyList()); // Initialize the list of Columns
                responseDTO.setColumnOrderIds(Collections.emptyList()); // Initialize the columnIds list

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
                    .map(columns -> {
                List<Long> cardOrderIds = columns.getCardOrderIds();
                List<CardDTO> cards = columns.getCards()
                        .stream().map(card ->
                                new CardDTO(card.getId(),
                                        card.getBoard().getId(),
                                        card.getColumn().getId(),
                                        card.getTitle(),
                                        card.getAttachments()
                                        ))
                        .collect(Collectors.toList());
                return new ColumnsDTO(columns, cardOrderIds, cards);
            })
                    .collect(Collectors.toList());
        } else {
            // Nếu không tìm thấy bảng, có thể xử lý theo ý của bạn, ví dụ: ném một ngoại lệ.
            throw new RuntimeException("Không tìm thấy bảng với ID: " + boardId);
        }
    }

    @Override
    public BoardResponseDTO updateBoardColumnOrderIds(Long boardId, UpdateBoardDTO updateData) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setColumnOrderIds(updateData.getColumnOrderIds());
            boardRepository.save(board);

            Set<Visibility> visibility = board.getVisibilities();
            List<ColumnsDTO> columns = board.getColumns()
                    .stream()
                    .map(column -> {
                        ColumnsDTO columnsDTO = new ColumnsDTO();
                        columnsDTO.setId(column.getId());
                        columnsDTO.setTitle(column.getTitle());
                        // Map other properties as needed
                        return columnsDTO;
                    })
                    .collect(Collectors.toList());

            return new BoardResponseDTO(board, visibility, updateData.getColumnOrderIds(), columns);
        } else {
            // Xử lý trường hợp không tìm thấy Board
            throw new RuntimeException("Board not found with id: " + boardId);
        }
    }

    public void addMemberToBoard(Board board, User user, MemberRole memberRole) {
        BoardMembers boardMembers = new BoardMembers();
        boardMembers.setBoard(board);
        boardMembers.setUser(user);
        boardMembers.setRole(memberRole);
        boardMembersRepository.save(boardMembers);
    }

    public List<UserDTO> getBoardMembers(Long boardId){
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if(boardOptional.isPresent()){
            Board board = boardOptional.get();

            return null;
        }
        throw new RuntimeException("Board not found");
    }

    public void addUserToBoardByEmail(Long boardId, String userEmail){
        Board board = boardRepository. findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boardRepository.save(board);
    }

//    public List<UserDTO> getBoardMembersByUserRole(Long boardId, UserRole roleName){
//        Optional<Board> boardOptional = boardRepository.findById(boardId);
//        if (boardOptional.isPresent()){
//            Board board = boardOptional.get();
//            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
//            List<User> userListByRole = userRepository.findByRoles(role);
//            List<UserDTO> userDTOList = new ArrayList<>();
//
//        }
//    }

    public void deleteMemberFromBoard(Long boardId, User user){
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()){
            Board board = boardOptional.get();
            if (board.getBoardMembers().contains(user)){
                board.getBoardMembers().remove(user);
                boardRepository.save(board);
            }
        } else {
            throw  new RuntimeException("Board not found");
        }
    }
}
