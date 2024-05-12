/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.ProductDao;
import com.sgd.ecommerce.model.Product;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public Product addNewProduct(Product product) {
		return productDao.save(product);
	}
	
	public List<Product> getProductList() {
		return (List<Product>) productDao.findAll();
	}
	
	public void deleteProductDetails(Integer id) {
		productDao.deleteById(id);
	}
}
