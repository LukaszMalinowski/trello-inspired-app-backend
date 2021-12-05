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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime createdDate;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private User owner;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamUser> members;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Board> boards;

}
