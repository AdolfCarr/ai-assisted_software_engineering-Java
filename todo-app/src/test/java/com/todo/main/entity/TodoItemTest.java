package com.todo.main.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void testEntity() {
        TodoItem item = new TodoItem();
        item.setId(1L);
        item.setTitle("Test Title");
        item.setDescription("Test Description");

        assertEquals(1L, item.getId());
        assertEquals("Test Title", item.getTitle());
        assertEquals("Test Description", item.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        TodoItem item = new TodoItem(1L, "Test", "Description");
        assertEquals(1L, item.getId());
    }

    @Test
    void testNoArgsConstructor() {
        TodoItem item = new TodoItem();
        assertNotNull(item);
    }
    
    @Test
    void testEqualsAndHashCode() {
        TodoItem item1 = new TodoItem(1L, "Task 1", "Description 1");
        TodoItem item2 = new TodoItem(1L, "Task 1", "Description 1");
        TodoItem item3 = new TodoItem(2L, "Task 2", "Description 2");
        TodoItem item4 = new TodoItem(1L, "Different Title", "Different Desc");

        // Test equality
        assertEquals(item1, item2);  // Same ID should be equal
        assertNotEquals(item1, item3);  // Different ID
        assertNotEquals(item1, item4);  // Same ID but different fields
        
        // Test null and other class
        assertNotEquals(item1, null);
        assertNotEquals(item1, new Object());

        // HashCode consistency
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testToString() {
        TodoItem item = new TodoItem(1L, "Test Task", "Test Description");
        String toStringOutput = item.toString();

        assertTrue(toStringOutput.contains("Test Task"));
        assertTrue(toStringOutput.contains("Test Description"));
        assertTrue(toStringOutput.contains("1")); // ID
    }

    @Test
    void testEqualsWithNullFields() {
        TodoItem item1 = new TodoItem(1L, null, null);
        TodoItem item2 = new TodoItem(1L, null, null);
        TodoItem item3 = new TodoItem(1L, "NotNull", null);

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
    }
}
