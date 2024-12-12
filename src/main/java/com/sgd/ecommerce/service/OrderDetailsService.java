/**
 * 
 */
package com.sgd.ecommerce.service;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.sgd.ecommerce.dao.CartDao;
import com.sgd.ecommerce.dao.OrderDetailsDao;
import com.sgd.ecommerce.dao.ProductDao;
import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.exception.GeneralServiceException;
import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.model.OrderDetails;
import com.sgd.ecommerce.model.OrderInput;
import com.sgd.ecommerce.model.OrderProductQuantity;
import com.sgd.ecommerce.model.Product;
import com.sgd.ecommerce.model.TransactionDetail;
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
	
	@Autowired
	@Qualifier(value = "paymentClient")
	private RazorpayClient paymentClient;
	
	private static final String API_KEY = System.getenv("API_KEY");
	private static final String CURRENCY = "INR";
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailsService.class);
	
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
					user,
					orderInput.getTransactionId()
					);
			
			if (isCartCheckout) {
				LOGGER.debug("Deleting cart details");
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

	public List<OrderDetails> getAllOrderDetails(String status) {
		if ("ALL".equalsIgnoreCase(status)) {
			return (List<OrderDetails>) orderDetailsDao.findAll();
		} else {
			return orderDetailsDao.findByOrderStatus(status);
		}
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
	
	/**
	 * create payment order which is first part of payment transaction
	 * @param amount
	 * @return
	 * @throws GeneralServiceException 
	 */
	public TransactionDetail createPaymentTransaction(final double amount) throws GeneralServiceException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("amount", (amount*100));
		jsonObject.put("currency", CURRENCY);
		TransactionDetail transactionDetail = null;
		
		try {
			Order createdOrder = paymentClient.orders.create(jsonObject);
			transactionDetail = prepareTransactionDetails(createdOrder);
		} catch (RazorpayException e) {
			LOGGER.error("Exception occurred during payment order creation");
			e.printStackTrace();
			throw new GeneralServiceException(500, "Exception occurred during payment order creation", e);
		}
		return transactionDetail;
	}

	/**
	 * @param createdOrder
	 * @return 
	 */
	private static TransactionDetail prepareTransactionDetails(final Order order) {
		LOGGER.debug("Transaction detail object created to return to front end");
		return new TransactionDetail(order.get("id"), order.get("amount"), order.get("currency"), API_KEY);
	}
}
