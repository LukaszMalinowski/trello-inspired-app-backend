package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository;

import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.BoardUser;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

    Optional<BoardUser> findBoardUserByBoard_BoardIdAndAndUser_UserId(Long boardId, Long userId);

    boolean existsByBoard_BoardIdAndUser_UserIdAndRole(Long boardId, Long userId, Role role);

    boolean existsByBoard_BoardIdAndAndUser_UserId(Long boardId, Long userId);

    List<BoardUser> findBoardUsersByUser_UserId(Long userId);

    List<BoardUser> findBoardUsersByUser_UserId_AndRole(Long userId, Role role);
}
