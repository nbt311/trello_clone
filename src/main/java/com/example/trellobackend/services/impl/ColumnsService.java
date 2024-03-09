package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.*;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.ColumnRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IColumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ColumnsService implements IColumsService {
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Iterable<Columns> findAll() {
        return null;
    }

    @Override
    public Optional<Columns> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Columns columns) {
        columnsRepository.save(columns);
    }

    @Override
    public void remove(Long columnId) {
        Optional<Columns> optionalColumn = columnsRepository.findById(columnId);
        if (optionalColumn.isPresent()) {
            // Nếu tồn tại, xóa cột
            columnsRepository.deleteById(columnId);
        } else {
            // Nếu không tồn tại, có thể xử lý theo ý của bạn, ví dụ: ném một ngoại lệ.
            throw new RuntimeException("Not found column id: " + columnId);
        }
    }

    @Override
    public List<ColumnsDTO> getAllColumns() {
        return columnsRepository.findAll().stream()
                .map(ColumnsDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getAllCardDTOByBoardId(Long columnId) {
        Columns columns = columnsRepository.findById(columnId).orElse(null);
        if (columns != null) {
            return columns.getCards().stream()
                    .map(CardDTO::new
                    )
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Không tìm thấy bảng với ID: " + columnId);
        }
    }

    @Override
    public ColumnsDTO updateColumnCardOrderIds(Long columnId, UpdateColumnDTO updateData) {
        Optional<Columns> columnsOptional = columnsRepository.findById(columnId);

        if (columnsOptional.isPresent()) {
            Columns columns = columnsOptional.get();
            columns.setCardOrderIds(updateData.getCardOrderIds());
            columnsRepository.save(columns);

            List<CardDTO> cards = columns.getCards()
                    .stream()
                    .map(card -> {
                        CardDTO cardDTO = new CardDTO();
                        cardDTO.setId(card.getId());
                        cardDTO.setTitle(card.getTitle());
                        // Map other properties as needed
                        return cardDTO;
                    })
                    .collect(Collectors.toList());

            return new ColumnsDTO(columns, updateData.getCardOrderIds(), cards);
        } else {
            // Xử lý trường hợp không tìm thấy Board
            throw new RuntimeException("Board not found with id: " + columnId);
        }
    }

    @Override
    public BoardResponseDTO createNewColumn(ColumnRequest columnRequest) { Optional<User> userOptional = userRepository.findByEmail(columnRequest.getEmail());
        if (userOptional.isPresent()){
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(columnRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()){
                Optional<Board> boardOptional = boardRepository.findById(columnRequest.getBoardId());
                if (boardOptional.isPresent()) {
                    Board board = boardOptional.get();
                    Columns newColumns = new Columns();
                    newColumns.setTitle(columnRequest.getTitle());
                    newColumns.setBoard(board);
                    columnsRepository.save(newColumns);
                    List<Long> columnOrderIds = board.getColumnOrderIds();
                    columnOrderIds.add(newColumns.getId());
                    board.setColumnOrderIds(columnOrderIds);
                    boardRepository.save(board);

                    // Update the board with the new list of columns
                    List<Columns> updatedColumns = columnOrderIds.stream()
                            .map(id -> columnsRepository.findById(id).orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    BoardResponseDTO responseDTO = new BoardResponseDTO();
                    responseDTO.setId(board.getId());
                    responseDTO.setTitle(board.getTitle());
                    responseDTO.setVisibility(board.getVisibilities());

                    List<ColumnsDTO> columnsDTOList = updatedColumns.stream()
                            .map(ColumnsDTO::fromEntity)
                            .collect(Collectors.toList());

                    responseDTO.setColumns(columnsDTOList);
                    responseDTO.setColumnOrderIds(columnOrderIds);

                    return responseDTO;
                } else {
                    throw new RuntimeException("Error: Board not found.");
                }
            }else {
                throw new RuntimeException("Error: Workspace not found.");
            }
        }else {
            throw new RuntimeException("Error: User not found.");
        }
    }

    @Override
    public void handleDragAndDrop(DragAndDropDTO dragAndDropDTO) {
        Long currentCardId = dragAndDropDTO.getCurrentCardId();
        Long prevColumnId = dragAndDropDTO.getPrevColumnId();
        List<Long> prevCardOrderIds = dragAndDropDTO.getPrevCardOrderIds();
        Long nextColumnId = dragAndDropDTO.getNextColumnId();
        List<Long> nextCardOrderIds = dragAndDropDTO.getNextCardOrderIds();

        // Xử lý logic thay đổi vị trí Card
        moveCardBetweenColumns(currentCardId, prevColumnId, nextColumnId);

        // Cập nhật danh sách CardOrderIds cho Column cũ và Column mới
        updateColumnsCardOrderIds(prevColumnId, prevCardOrderIds);
        updateColumnsCardOrderIds(nextColumnId, nextCardOrderIds);
    }

    private void updateColumnsCardOrderIds(Long columnId, List<Long> cardOrderIds) {
        Columns column = columnsRepository.findById(columnId).orElse(null);
        if (column != null) {
            column.setCardOrderIds(cardOrderIds);
            columnsRepository.save(column);
        }
    }

    private void moveCardBetweenColumns(Long currentCardId, Long prevColumnId, Long nextColumnId) {
        Card card = cardRepository.findById(currentCardId).orElse(null);
        if (card != null) {
            Columns prevColumn = columnsRepository.findById(prevColumnId).orElse(null);
            if (prevColumn != null) {
                prevColumn.getCards().remove(card);
                card.setColumn(null);  // Set lại cột cho Card
                columnsRepository.save(prevColumn);
            }

            Columns nextColumn = columnsRepository.findById(nextColumnId).orElse(null);
            if (nextColumn != null) {
                nextColumn.getCards().add(card);
                card.setColumn(nextColumn);  // Set lại cột cho Card
                columnsRepository.save(nextColumn);
            }
        }
    }

    @Override
    public void updateColumnsById(Long columnId,ColumnsDTO  newTitle) {
        Optional<Columns> columnsOptional = columnsRepository.findById(columnId);
        if(columnsOptional.isPresent()){
            Columns columns = columnsOptional.get();
            columns.setTitle(newTitle.getTitle());
            columnsRepository.save(columns);
        } else {
            throw new RuntimeException("Column not found");
        }
    }
}
