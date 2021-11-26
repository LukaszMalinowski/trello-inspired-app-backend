package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.service;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.ColumnNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.TaskNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper.TaskMapper;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.ColumnRepository;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public void addTask(Long columnId, NewTaskDto taskDto) {
        log.info("Adding new task to column with id {}", columnId);
        var column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);

        var task = taskMapper.toEntity(taskDto);
        task.setCreatedDate(LocalDateTime.now());
        task.setColumn(column);

        column.getTasks().add(task);
        columnRepository.save(column);
    }

    //TODO there's a bug that when number of tasks is lower than previously tasks won't be deleted. Also order doesnt change.
    @Transactional
    public void updateTasks(Long columnId, List<Long> taskIds) {
        log.info("Updating tasks in column with id {}", columnId);
        var column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);

        var tasks = taskIds.stream()
                           .map(taskId -> taskRepository.findById(taskId)
                                                        .orElseThrow(TaskNotFoundException::new))
                           .peek(task -> task.setColumn(column))
                           .collect(Collectors.toList());

        column.setTasks(tasks);
        columnRepository.save(column);
    }
}
