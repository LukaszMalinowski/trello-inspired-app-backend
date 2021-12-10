package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
}
