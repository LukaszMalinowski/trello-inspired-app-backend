package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
