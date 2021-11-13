package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Column {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long columnId;
    @NotNull
    @NotBlank
    private String name;
    @ManyToOne (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Board board;
    @OneToMany (mappedBy = "column", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Column(String name, Board board) {
        this.name = name;
        this.board = board;
        this.tasks = Collections.emptyList();
    }
}
