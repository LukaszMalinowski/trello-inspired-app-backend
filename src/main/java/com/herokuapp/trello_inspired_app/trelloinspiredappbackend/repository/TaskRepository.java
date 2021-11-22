package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
