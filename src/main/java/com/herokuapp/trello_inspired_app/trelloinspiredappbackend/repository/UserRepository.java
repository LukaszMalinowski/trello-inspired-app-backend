package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
