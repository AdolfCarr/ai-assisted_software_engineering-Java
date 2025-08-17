package com.todo.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.main.entity.TodoItem;
import com.todo.main.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<TodoItem> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public TodoItem createTodo(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    public TodoItem updateTodo(Long id, TodoItem todoDetails) {
        TodoItem todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        TodoItem todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        
        todoRepository.delete(todo);
    }
}