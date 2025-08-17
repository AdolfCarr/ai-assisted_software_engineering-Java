package inventory.controller;

import inventory.model.Product;
import inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Test
    void testGetAllProducts() throws Exception {
        Product product = new Product("Laptop", "Gaming", 1499.99, 5);
        product.setId(1L);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
        
        mockMvc.perform(get("/api/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Laptop"))
               .andExpect(jsonPath("$[0].price").value(1499.99));
    }
    
    @Test
    void testGetProductByIdFound() throws Exception {
        Product product = new Product("Mouse", "Wireless", 29.99, 10);
        product.setId(1L);
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        
        mockMvc.perform(get("/api/products/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Mouse"));
    }
    
    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(99L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/products/99"))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product("Keyboard", "Mechanical", 99.99, 5);
        product.setId(1L);
        when(productService.saveProduct(any(Product.class))).thenReturn(product);
        
        mockMvc.perform(post("/api/products")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"name\":\"Keyboard\",\"description\":\"Mechanical\",\"price\":99.99,\"quantity\":5}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));
    }
    
    @Test
    void testUpdateProductSuccess() throws Exception {
        Product updated = new Product("Updated", "New desc", 199.99, 2);
        updated.setId(1L);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updated);
        
        mockMvc.perform(put("/api/products/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"name\":\"Updated\",\"description\":\"New desc\",\"price\":199.99,\"quantity\":2}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Updated"));
    }
    
    @Test
    void testUpdateProductNotFound() throws Exception {
        when(productService.updateProduct(eq(99L), any(Product.class)))
            .thenThrow(new RuntimeException("Product not found"));
        
        mockMvc.perform(put("/api/products/99")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"name\":\"Nonexistent\"}"))
               .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        
        mockMvc.perform(delete("/api/products/1"))
               .andExpect(status().isNoContent());
        
        verify(productService).deleteProduct(1L);
    }
}
