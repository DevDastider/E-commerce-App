/**
 * 
 */
package com.sgd.ecommerce.controller;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sgd.ecommerce.exception.ExceptionHandlerMapping;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.service.ProductService;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
        			.setControllerAdvice(new ExceptionHandlerMapping())
        			.build();
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testAddNewProduct_WithAdminRole_ShouldReturnProduct() throws Exception {
        // Arrange
        Product mockProduct = new Product();  // Assuming Product is a model class
        mockProduct.setProductName("Test Product");
        mockProduct.setProductNumber(1);

        MockMultipartFile productJson = new MockMultipartFile("product", "", "application/json", "{\"id\":1, \"name\":\"Test Product\"}".getBytes());
        MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "image.jpg", "image/jpeg", "image content".getBytes());

        when(productService.addNewProduct(any(Product.class))).thenReturn(mockProduct);

        // Act & Assert
        mockMvc.perform(multipart("/addProduct")
                .file(productJson)
                .file(imageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productNumber").value(1))
                .andExpect(jsonPath("$.productName").value("Test Product"));

        verify(productService, times(1)).addNewProduct(any(Product.class));
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testAddNewProduct_WithIOException_ShouldReturn500() throws Exception {
        // Arrange
        MockMultipartFile productJson = new MockMultipartFile("product", "", "application/json", "{\"id\":1, \"name\":\"Test Product\"}".getBytes());
        MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "image.jpg", "image/jpeg", new byte[] {});

        doThrow(new RuntimeException("File upload error")).when(productService).addNewProduct(any(Product.class));

        // Act & Assert
        mockMvc.perform(multipart("/addProduct")
                .file(productJson)
                .file(imageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError());

        verify(productService, times(1)).addNewProduct(any(Product.class));
    }

    @Test
    public void testGetAllProducts_ShouldReturnProductList() throws Exception {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productService.getProductList(0, "")).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(get("/getAllProducts")
                .param("pageNumber", "0")
                .param("searchKey", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("Product 1"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(productService, times(1)).getProductList(0, "");
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testDeleteProductDetails_WithAdminRole_ShouldReturnOk() throws Exception {
        // Arrange
        Integer productId = 1;

        // Act & Assert
        mockMvc.perform(delete("/deleteProductDetails/{productId}", productId))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProductDetails(productId);
    }

    @Test
    public void testGetProductDetailsById_ShouldReturnProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductNumber(1);
        when(productService.getProductDetailsByNumber(1)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/getProductDetailsById/{productId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productNumber").value(1))
                .andExpect(jsonPath("$.productName").value("Test Product"));

        verify(productService, times(1)).getProductDetailsByNumber(1);
    }

    @Test
    @WithMockUser(roles = "user")
    public void testGetProductDetails_WithUserRole_ShouldReturnProductList() throws Exception {
        // Arrange
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Product 1");
        product.setProductNumber(1);
		productList.add(product);

        when(productService.getProductDetails(true, 1)).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(get("/getProductDetails/{isSingleProductCheckout}/{productId}", true, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productNumber").value(1))
                .andExpect(jsonPath("$[0].productName").value("Product 1"));

        verify(productService, times(1)).getProductDetails(true, 1);
    }
}

