package inventory.service;

import inventory.model.Product;
import inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductService productService;
    
    @Test
    void testGetAllProducts() {
        productService.getAllProducts();
        verify(productRepository).findAll();
    }
    
    @Test
    void testGetProductByIdFound() {
        Product product = new Product("Laptop", "Gaming laptop", 1499.99, 3);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        
        Optional<Product> result = productService.getProductById(1L);
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
    }
    
    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertFalse(productService.getProductById(99L).isPresent());
    }
    
    @Test
    void testSaveProduct() {
        Product product = new Product("Keyboard", "Mechanical", 99.99, 10);
        when(productRepository.save(product)).thenReturn(product);
        
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        verify(productRepository).save(product);
    }
    
    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(99L, new Product());
        });
    }
    
    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }
}
