package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.task.TaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = UserRepository.class)
public abstract class TaskMapper {

    @Autowired
    private UserRepository userRepository;

    public abstract TaskDto toDto(Task save);
}
