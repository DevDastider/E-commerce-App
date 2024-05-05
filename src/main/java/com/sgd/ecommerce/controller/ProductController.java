/**
 * 
 */
package com.sgd.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.service.ProductService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping({"/addProduct"})
	public Product addNewProduct(@RequestBody Product product) {
		return productService.addNewProduct(product);
	}
}
