package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.TaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/{columnId}/tasks")
    public ResponseEntity<TaskDto> addTask(@PathVariable Long columnId, @RequestBody NewTaskDto taskDto) {
        var task = columnService.addTask(columnId, taskDto);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{columnId}/tasks")
    public ResponseEntity<Void> updateTasks(@PathVariable Long columnId, @RequestBody List<Long> tasks) {
        columnService.updateTasks(columnId, tasks);
        return ResponseEntity.noContent().build();
    }

}
