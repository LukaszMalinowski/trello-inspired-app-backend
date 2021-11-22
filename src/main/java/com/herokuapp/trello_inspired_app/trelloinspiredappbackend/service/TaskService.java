package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public void deleteTask(Long taskId) {
        log.info("Removing task with id {}", taskId);
        taskRepository.deleteById(taskId);
    }

}
