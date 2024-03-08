package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.enums.EBoardVisibility;
import com.example.trellobackend.enums.UserRole;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.*;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private ColumnsRepository columnsRepository;

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
                                        card.getTitle()
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

//    @Override
//    public BoardResponseDTO updateCardOrderIds(Long boardId, DragAndDropDTO updateData) {
//        Long currentCardId = updateData.getCurrentCardId();
//        Long prevColumnId = updateData.getPrevColumnId();
//        List<Long> prevCardOrderIds = updateData.getPrevCardOrderIds();
//        Long nextColumnId = updateData.getNextColumnId();
//        List<Long> nextCardOrderIds = updateData.getNextCardOrderIds();
//        // Lấy bảng từ cơ sở dữ liệu
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
//
//        Columns prevColumn = board.getColumns().stream()
//                .filter(column -> column.getId().equals(prevColumnId))
//                .findFirst().orElseThrow(() -> new RuntimeException("Previous column not found with id: " + prevColumnId));
//        // Thực hiện chuyển card
////        moveCard(board, currentCardId, prevColumnId, prevCardOrderIds, nextColumnId, nextCardOrderIds);
//
//        // Lưu các thay đổi vào cơ sở dữ liệu
//        boardRepository.save(board);
//
//        // Trả về DTO chứa thông tin đã được cập nhật
////        return createBoardResponseDTO(board, currentCardId);
// return  ;
//    }

//    private BoardResponseDTO createBoardResponseDTO(Board board, Long currentCardId) {
//        List<ColumnsDTO> columnsDTOList = board.getColumns()
//                .stream()
//                .map(ColumnsDTO::fromEntity)
//                .collect(Collectors.toList());
//
//        // Tìm card đã được chuyển
//        Card movedCard = board.getColumns().stream()
//                .flatMap(column -> column.getCards().stream())
//                .filter(card -> card.getId().equals(currentCardId))
//                .findFirst().orElseThrow(() -> new RuntimeException("Moved card not found with id: " + currentCardId));
//
//        return new BoardResponseDTO(board, board.getVisibilities(), board.getColumnOrderIds(), columnsDTOList);
//    }
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
//    private void moveCard(Board board, Long currentCardId, Long prevColumnId, List<Long> prevCardOrderIds, Long nextColumnId, List<Long> nextCardOrderIds) {
//        // Tìm cột cũ và mới
//        logger.debug("prevColumnId: {}, nextColumnId: {}", prevColumnId, nextColumnId);
//        Columns prevColumn = board.getColumns().stream()
//                .filter(column -> column.getId().equals(prevColumnId))
//                .findFirst().orElseThrow(() -> new RuntimeException("Previous column not found with id: " + prevColumnId));
//
//        Columns nextColumn = board.getColumns().stream()
//                .filter(column -> column.getId().equals(nextColumnId))
//                .findFirst().orElseThrow(() -> new RuntimeException("Next column not found with id: " + nextColumnId));
//
//        // Lọc card từ cột cũ
//        List<Card> cardsInPrevColumn = prevColumn.getCards().stream()
//                .filter(card -> !card.getId().equals(currentCardId))
//                .collect(Collectors.toList());
//
//        // Thêm card vào cột mới
//        Card movedCard = prevColumn.getCards().stream()
//                .filter(card -> card.getId().equals(currentCardId))
//                .findFirst().orElseThrow(() -> new RuntimeException("Card not found with id: " + currentCardId));
//
//        nextColumn.getCards().add(movedCard);
//
//        // Cập nhật danh sách card trong cột cũ
//        prevColumn.setCards(cardsInPrevColumn);
//        prevColumn.setCardOrderIds(prevCardOrderIds);
//
//        // Cập nhật thứ tự card trong cột mới
//        nextColumn.setCardOrderIds(nextCardOrderIds);
//        System.out.println(prevColumn);
//        System.out.println(nextColumn);
//    }

    public void addMemberToBoard(Board board, User user, UserRole userRole) {
        BoardMembers boardMembers = new BoardMembers();
        boardMembers.setBoard(board);
        boardMembers.setUser(user);
        boardMembers.setRole(userRole);
        boardMembersRepository.save(boardMembers);
    }

}
