package inventory;

import inventory.model.Product;
import inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void testFullCycle() {
        // Create
        Product newProduct = new Product("Integration", "Test", 100.0, 5);
        ResponseEntity<Product> createResponse = restTemplate.postForEntity(
            "/api/products", newProduct, Product.class);
        
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        Product createdProduct = createResponse.getBody();
        assertNotNull(createdProduct.getId());
        
        // Read
        ResponseEntity<Product> getResponse = restTemplate.getForEntity(
            "/api/products/" + createdProduct.getId(), Product.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Integration", getResponse.getBody().getName());
        
        // Update
        createdProduct.setPrice(120.0);
        ResponseEntity<Product> updateResponse = restTemplate.exchange(
            "/api/products/" + createdProduct.getId(),
            HttpMethod.PUT,
            new HttpEntity<>(createdProduct),
            Product.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals(120.0, updateResponse.getBody().getPrice());
        
        // Delete
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            "/api/products/" + createdProduct.getId(),
            HttpMethod.DELETE,
            null,
            Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        
        // Verify deletion
        ResponseEntity<Product> notFoundResponse = restTemplate.getForEntity(
            "/api/products/" + createdProduct.getId(), Product.class);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    }
}