package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    private long boardId;
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDateTime createdDate;

}
