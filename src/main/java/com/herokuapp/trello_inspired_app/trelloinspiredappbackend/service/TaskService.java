package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.task.UpdateTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.TaskNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TaskRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role.MEMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final BoardService boardService;

    @Transactional
    public void deleteTask(Long taskId, User user) {
        log.info("Removing task with id {}", taskId);
        var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Long boardId = task.getColumn().getBoard().getBoardId();
        if (boardService.isNotBoardMember(boardId, user.getUserId())) {
            boardService.addMember(user.getUserId(), boardId, MEMBER);
        }
        taskRepository.delete(task);
    }

    @Transactional
    public void editTask(Long taskId, UpdateTaskDto taskDto, User user) {
        log.info("Editing task with id {}", taskId);
        var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Long boardId = task.getColumn().getBoard().getBoardId();
        if (boardService.isNotBoardMember(boardId, user.getUserId())) {
            boardService.addMember(user.getUserId(), boardId, MEMBER);
        }
        updateTask(task, taskDto);
        taskRepository.save(task);
    }

    private void updateTask(Task task, UpdateTaskDto taskDto) {
        User assignee = null;
        if (taskDto.getAssigneeId() != null) {
            assignee = userRepository.findById(taskDto.getAssigneeId()).orElseThrow(UserNotFoundException::new);
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setAssignee(assignee);
    }
}
