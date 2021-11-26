package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardColumnDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardUserDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.BoardNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserAlreadyHasAdminException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserIsNotMemberOfBoardException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.BoardMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.*;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardUserRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;

    private final BoardMapper boardMapper;

    public List<BoardDto> getAllBoards() {
        log.info("Getting all boards");
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                     .map(boardMapper::toDto)
                     .collect(Collectors.toList());
    }

    @Transactional
    public void addNewBoard(BoardDto newBoard) {
        log.info("Adding new board");
        Board board = new Board(newBoard);

        //TODO: get user from jwt
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        board.setCreatedDate(LocalDateTime.now());
        board.setOwner(user);
        board.setColumns(createDefaultColumns(board));

        boardRepository.save(board);
    }

    private List<Column> createDefaultColumns(
            Board board) {
        List<Column> defaultColumns = new ArrayList<>();
        defaultColumns.add(new Column("Todo", board));
        defaultColumns.add(new Column("In progress", board));
        defaultColumns.add(new Column("Done", board));
        return defaultColumns;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        log.info("Deleting board with id {}", boardId);
        //TODO: check if user has admin privileges
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        boardRepository.delete(board);
    }

    public List<BoardUserDto> getAllBoardMembers(Long boardId) {
        log.info("Getting all members");
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return board.getMembers().stream()
                    .map(boardMapper::toUserDto)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void addAdminPrivileges(Long boardId, Long userId) {
        log.info("Giving user {} admin privileges", userId);
        //TODO: check if user who make request is admin
        BoardUser boardUser = boardUserRepository
                .findBoardUserByBoard_BoardIdAndAndUser_UserId(boardId, userId)
                .orElseThrow(UserIsNotMemberOfBoardException::new);

        if (boardUser.getRole() == Role.ADMIN) {
            throw new UserAlreadyHasAdminException();
        }

        boardUser.setRole(Role.ADMIN);
        boardUserRepository.save(boardUser);
    }

    public BoardColumnDto getBoardDetails(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return boardMapper.toColumnDto(board);
    }
}
