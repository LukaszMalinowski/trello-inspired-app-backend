package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long taskId;
    @NotNull
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private LocalDateTime createdDate;
    @ManyToOne (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private User owner;
    @ManyToOne (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private User assignee;
    @ManyToOne (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Column column;

}
