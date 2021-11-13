package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.BoardDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Board;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> getAllBoards() {
        log.info("Getting all boards");
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                     .map(BoardDto::new)
                     .collect(Collectors.toList());
    }

}
