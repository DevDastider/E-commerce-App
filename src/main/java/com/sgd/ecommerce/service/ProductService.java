/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.ArrayList;
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

	public Product getProductDetailsByNumber(Integer productNumber) {
		return productDao.findById(productNumber).get();
	}

	/**
	 * @param productId 
	 * @param isSingleProductCheckout 
	 * @return
	 */
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
		List<Product> productList = new ArrayList<>();
		if(isSingleProductCheckout) {
			productList.add(productDao.findById(productId).get());
		}
		return productList;
	}
}
