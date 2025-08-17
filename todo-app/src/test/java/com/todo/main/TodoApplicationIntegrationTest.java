package com.todo.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import com.todo.main.entity.TodoItem;
import com.todo.main.repository.TodoRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testCreateAndGetTodo() {
        // Create
        TodoItem newItem = new TodoItem(null, "Integration", "Test");
        ResponseEntity<TodoItem> postResponse = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/todos",
            newItem,
            TodoItem.class
        );
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        
        // Get
        Long id = postResponse.getBody().getId();
        ResponseEntity<TodoItem> getResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/todos/" + id,
            TodoItem.class
        );
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Integration", getResponse.getBody().getTitle());
        
        // Cleanup
        todoRepository.deleteById(id);
    }
}