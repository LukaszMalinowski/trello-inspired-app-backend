package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardUserId;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    private Board board;
    private LocalDateTime joinDate;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
