package com.todo.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todo.main.entity.TodoItem;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {

}
