/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgd.ecommerce.dao.CartDao;
import com.sgd.ecommerce.dao.OrderDetailsDao;
import com.sgd.ecommerce.dao.ProductDao;
import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.model.OrderDetails;
import com.sgd.ecommerce.model.OrderInput;
import com.sgd.ecommerce.model.OrderProductQuantity;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.model.User;
import com.sgd.ecommerce.security.JWTRequestFilter;
import com.sgd.ecommerce.util.Constants;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Service
public class OrderDetailsService {

	@Autowired
	private OrderDetailsDao orderDetailsDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	/**
	 * @param orderInput
	 */
	public void placeOrder(OrderInput orderInput, boolean isCartCheckout) {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
		for(OrderProductQuantity orderedProduct: productQuantityList) {
			Product product = productDao.findById(orderedProduct.getProductNumber()).get();
			String currentUser = JWTRequestFilter.CURRENT_USER;
			User user = userDao.findById(currentUser).get();
			
			OrderDetails orderDetails = new OrderDetails(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					Constants.ORDER_PLACED,
					product.getProductDiscountedPrice()*orderedProduct.getProductQuantity(),
					product,
					user
					);
			
			if (isCartCheckout) {
				List<Cart> cartList = cartDao.findByUser(user);
				cartList.stream().forEach(cart-> cartDao.deleteById(cart.getId()));
			}
			
			orderDetailsDao.save(orderDetails);
		}
	}

	public List<OrderDetails> getOrderDetails() {
		String currentUser = JWTRequestFilter.CURRENT_USER;
		User user = userDao.findById(currentUser).get();
		return orderDetailsDao.findByUser(user);
	}

	public List<OrderDetails> getAllOrderDetails() {
		return (List<OrderDetails>) orderDetailsDao.findAll();
	}

	/**
	 * @param orderId
	 */
	public void markOrderDelivered(Integer orderId) {
		OrderDetails order = orderDetailsDao.findById(orderId).get();
		if (order != null) {
			order.setOrderStatus(Constants.ORDER_DELIVERED);
			orderDetailsDao.save(order);
		}
	}
}
