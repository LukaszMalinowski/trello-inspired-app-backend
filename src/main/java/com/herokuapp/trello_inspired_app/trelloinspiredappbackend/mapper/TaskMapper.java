package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.mapper;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.dto.NewTaskDto;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper (componentModel = "spring", uses = UserRepository.class)
public abstract class TaskMapper {

    @Autowired
    private UserRepository userRepository;

    public Task toEntity(NewTaskDto taskDto) {
        return Task.builder()
                   .title(taskDto.getTitle())
                   .description(taskDto.getDescription())
                   .owner(userRepository.findById(taskDto.getOwnerId())
                                        .orElseThrow(UserNotFoundException::new))
                   .build();
    }

}
