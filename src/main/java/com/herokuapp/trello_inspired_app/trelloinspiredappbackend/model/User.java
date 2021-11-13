package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    @NotBlank
    private String username;
    private String password;
    private String name;
    private String surname;
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<BoardUser> boards;
    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Task> ownedTasks;
    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Board> ownedBoards;
    @OneToMany (mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Task> assignedToTasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
