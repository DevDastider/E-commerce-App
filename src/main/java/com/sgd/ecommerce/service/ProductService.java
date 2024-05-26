/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.CartDao;
import com.sgd.ecommerce.dao.ProductDao;
import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.security.JWTRequestFilter;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CartDao cartDao;

	public Product addNewProduct(Product product) {
		return productDao.save(product);
	}

	public List<Product> getProductList(int pageNumber, String searchKey) {
		Pageable pageable = PageRequest.of(pageNumber, 10);
		if ("".equals(searchKey)) {
			return (List<Product>) productDao.findAll(pageable);
		} else {
			return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey,
					searchKey, pageable);
		}
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
		if (isSingleProductCheckout && productId!=0 ) {
			// buying a single product
			productList.add(productDao.findById(productId).get());
		} else {
			String username = JWTRequestFilter.CURRENT_USER;
			User user = userDao.findById(username).get();
			List<Cart> cartList = cartDao.findByUser(user);

			productList.addAll(cartList.stream().map(
					cart -> cart.getProduct()).collect(Collectors.toList()));
		}
		return productList;
	}
}
