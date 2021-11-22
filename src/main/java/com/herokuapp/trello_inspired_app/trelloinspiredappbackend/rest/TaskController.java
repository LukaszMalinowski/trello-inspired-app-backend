package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @DeleteMapping ("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

}
