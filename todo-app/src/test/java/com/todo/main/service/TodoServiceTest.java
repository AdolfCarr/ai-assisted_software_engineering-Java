package com.todo.main.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.todo.main.entity.TodoItem;
import com.todo.main.repository.TodoRepository;

import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList(
            new TodoItem(1L, "Task 1", "Desc 1"),
            new TodoItem(2L, "Task 2", "Desc 2")
        ));

        assertEquals(2, todoService.getAllTodos().size());
    }

    @Test
    void testCreateTodo() {
        TodoItem newItem = new TodoItem(null, "New", "Item");
        when(todoRepository.save(newItem)).thenReturn(new TodoItem(1L, "New", "Item"));

        TodoItem created = todoService.createTodo(newItem);
        
        assertEquals(1L, created.getId());
        verify(todoRepository).save(newItem);
    }

    @Test
    void testUpdateTodo() {
        TodoItem existing = new TodoItem(1L, "Old", "Desc");
        TodoItem updates = new TodoItem(null, "Updated", "New Desc");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(todoRepository.save(existing)).thenReturn(existing);

        TodoItem updated = todoService.updateTodo(1L, updates);
        
        assertEquals("Updated", updated.getTitle());
        assertEquals("New Desc", updated.getDescription());
    }
}
