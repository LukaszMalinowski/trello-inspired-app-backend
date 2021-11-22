package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.ColumnNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.TaskMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Column;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public void addTask(Long columnId, NewTaskDto taskDto) {
        log.info("Adding new task to column with id {}", columnId);
        Column column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);

        Task task = taskMapper.toEntity(taskDto);
        task.setCreatedDate(LocalDateTime.now());
        task.setColumn(column);

        column.getTasks().add(task);
        columnRepository.save(column);
    }

}
