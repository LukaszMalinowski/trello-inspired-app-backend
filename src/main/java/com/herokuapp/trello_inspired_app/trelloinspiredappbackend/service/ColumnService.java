package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.ColumnNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.TaskNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.TaskMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.ColumnRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TaskRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role.MEMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final BoardService boardService;

    private final TaskMapper taskMapper;


    @Transactional
    public TaskDto addTask(Long columnId, NewTaskDto taskDto, String username) {
        log.info("Adding new task to column with id {}", columnId);

        var column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);
        Long boardId = column.getBoard().getBoardId();

        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        if (!boardService.isMember(boardId, user.getUserId())) {
            boardService.addMember(user.getUserId(), boardId, MEMBER);
        }

        var task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .owner(user)
                .build();

        task.setCreatedDate(LocalDateTime.now());
        task.setColumn(column);

        return taskMapper.toDto(taskRepository.save(task));
    }

    //TODO there's a bug that when number of tasks is lower than previously tasks won't be deleted. Also order doesnt change.
    @Transactional
    public void updateTasks(Long columnId, List<Long> taskIds, User user) {
        log.info("Updating tasks in column with id {}", columnId);
        var column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);
        Long boardId = column.getBoard().getBoardId();

        if (!boardService.isMember(boardId, user.getUserId())) {
            boardService.addMember(user.getUserId(), boardId, MEMBER);
        }

        var tasks = taskIds.stream()
                .map(taskId -> taskRepository.findById(taskId)
                        .orElseThrow(TaskNotFoundException::new))
                .peek(task -> task.setColumn(column))
                .collect(Collectors.toList());

        column.setTasks(tasks);
        columnRepository.save(column);
    }
}
