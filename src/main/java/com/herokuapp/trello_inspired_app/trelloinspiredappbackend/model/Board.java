package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private LocalDateTime createdDate;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private User owner;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardUser> members;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Column> columns;

}
