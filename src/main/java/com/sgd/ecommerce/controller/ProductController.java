/**
 * 
 */
package com.sgd.ecommerce.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgd.ecommerce.exception.GeneralServiceException;
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
	
	@PreAuthorize("hasRole('admin')")
	@PostMapping(value = {"/addProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
								@RequestPart("imageFiles") MultipartFile[] files) throws GeneralServiceException {
		try {
			Set<ImageModel> images = uploadImage(files);
			product.setProductImages(images);
		} catch(IOException ex) {
			throw new GeneralServiceException(500, "Error while uploading image", ex);
		}
		return productService.addNewProduct(product);
	}
	
	@GetMapping({"/getAllProducts"})
	public List<Product> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNumber,
										@RequestParam(defaultValue = "") String searchKey){
		return productService.getProductList(pageNumber, searchKey);
	}
	
	@PreAuthorize("hasRole('admin')")
	@DeleteMapping({"/deleteProductDetails/{productId}"})
	public void deleteProductDetails(@PathVariable("productId") Integer productId) {
		productService.deleteProductDetails(productId);
	}
	
	@GetMapping({"/getProductDetailsById/{productId}"})
	public Product getProductDetailsById(@PathVariable("productId")Integer productNumber) {
		return productService.getProductDetailsByNumber(productNumber);
	}
	
	@PreAuthorize("hasRole('user')")
	@GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
	public List<Product> getProductDetails(
			@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
			@PathVariable(name = "productId") Integer productId) {
		return productService.getProductDetails(isSingleProductCheckout, productId);
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
