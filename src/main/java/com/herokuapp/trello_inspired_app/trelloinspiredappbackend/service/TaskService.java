package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.UpdateTaskDto;
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

@Service
@RequiredArgsConstructor
@Slf4j
//TODO: Add assigning to board
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteTask(Long taskId) {
        log.info("Removing task with id {}", taskId);
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void editTask(Long taskId, UpdateTaskDto taskDto) {
        log.info("Editing task with id {}", taskId);
        var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        updateTask(task, taskDto);
        taskRepository.save(task);
    }

    private void updateTask(Task task, UpdateTaskDto taskDto) {
        User assignee = null;
        if(taskDto.getAssigneeId() != null) {
            assignee = userRepository.findById(taskDto.getAssigneeId()).orElseThrow(UserNotFoundException::new);
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setAssignee(assignee);
    }
}
