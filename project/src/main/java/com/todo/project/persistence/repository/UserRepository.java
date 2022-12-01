package com.todo.project.persistence.repository;

import com.todo.project.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository{
    List<User> get();

    User get(int id);

    User get(String username);

    void create(User user);

    void update(User user);

    void delete(User user);
}
