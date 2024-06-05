/**
 * 
 */
package com.sgd.ecommerce.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgd.ecommerce.model.OrderDetails;
import com.sgd.ecommerce.model.User;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Repository
public interface OrderDetailsDao extends CrudRepository<OrderDetails, Integer> {

	public List<OrderDetails> findByUser(User user);
	
	public List<OrderDetails> findByOrderStatus(String status);
}
