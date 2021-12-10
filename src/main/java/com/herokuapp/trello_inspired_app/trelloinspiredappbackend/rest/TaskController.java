package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.task.UpdateTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, Authentication authentication) {
        taskService.deleteTask(taskId, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> editTask(@PathVariable Long taskId, @RequestBody @Validated UpdateTaskDto taskDto, Authentication authentication) {
        taskService.editTask(taskId, taskDto, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

}
