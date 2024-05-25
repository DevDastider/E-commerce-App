/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CartService {

	@Autowired
	private CartDao cartDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	/**
	 * @param productId
	 */
	public Cart addProductToCart(Integer productId) {
		Product product = productDao.findById(productId).get();
		String username = JWTRequestFilter.CURRENT_USER;
		User user = null;
		if (username != null) {
			user = userDao.findById(username).get();
		}

		if (product != null && user != null) {
			Cart cart = new Cart(product, user);
			return cartDao.save(cart);
		}
		return null;
	}
	
	public List<Cart> getCartDetails(){
		String username = JWTRequestFilter.CURRENT_USER;
		User user = userDao.findById(username).get();
		return cartDao.findByUser(user);
	}

}
