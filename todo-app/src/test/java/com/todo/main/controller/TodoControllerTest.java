package com.todo.main.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.todo.main.entity.TodoItem;
import com.todo.main.service.TodoService;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Test
    void testGetAllTodos() {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(
            new TodoItem(1L, "Task 1", "Desc 1"),
            new TodoItem(2L, "Task 2", "Desc 2")
        ));

        ResponseEntity<List<TodoItem>> response = todoController.getAllTodos();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testCreateTodo() {
        TodoItem newItem = new TodoItem(null, "New", "Item");
        TodoItem savedItem = new TodoItem(1L, "New", "Item");

        when(todoService.createTodo(newItem)).thenReturn(savedItem);

        ResponseEntity<TodoItem> response = todoController.createTodo(newItem);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testDeleteTodo() {
        doNothing().when(todoService).deleteTodo(1L);

        ResponseEntity<Void> response = todoController.deleteTodo(1L);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(todoService).deleteTodo(1L);
    }
}
