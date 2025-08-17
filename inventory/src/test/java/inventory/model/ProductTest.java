package inventory.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    
    @Test
    void testProductCreation() {
        Product product = new Product("Laptop", "High-end gaming laptop", 1299.99, 10);
        
        assertNull(product.getId()); // ID should be null before persistence
        assertEquals("Laptop", product.getName());
        assertEquals("High-end gaming laptop", product.getDescription());
        assertEquals(1299.99, product.getPrice(), 0.001);
        assertEquals(10, product.getQuantity());
    }
    
    @Test
    void testSetters() {
        Product product = new Product();
        product.setName("Mouse");
        product.setDescription("Wireless mouse");
        product.setPrice(29.99);
        product.setQuantity(50);
        
        assertEquals("Mouse", product.getName());
        assertEquals("Wireless mouse", product.getDescription());
        assertEquals(29.99, product.getPrice(), 0.001);
        assertEquals(50, product.getQuantity());
    }
    
    @Test
    void testEqualsAndHashCode() {
        Product product1 = new Product("Keyboard", "Mechanical keyboard", 99.99, 15);
        Product product2 = new Product("Keyboard", "Mechanical keyboard", 99.99, 15);
        
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }
}