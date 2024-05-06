/**
 * 
 */
package com.sgd.ecommerce.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgd.ecommerce.model.ImageModel;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.service.ProductService;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@RestController
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping(value = {"/addProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
								@RequestPart("imageFiles") MultipartFile[] files) {
		try {
			Set<ImageModel> images = uploadImage(files);
			product.setProductImages(images);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return productService.addNewProduct(product);
	}
	
	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException{
		Set<ImageModel> images = new HashSet<>();
		
		for (MultipartFile file : multipartFiles) {
			ImageModel imageModel = new ImageModel(file.getName(), file.getContentType(), file.getBytes());
			images.add(imageModel);
		}
		
		return images;
	}
}
