package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.rest;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/columns")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping ("/{columnId}/tasks")
    public ResponseEntity<Void> addTask(@PathVariable Long columnId, @RequestBody NewTaskDto taskDto) {
        columnService.addTask(columnId, taskDto);
        return ResponseEntity.noContent().build();
    }

}
