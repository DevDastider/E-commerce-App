/**
 * 
 */
package com.sgd.ecommerce.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgd.ecommerce.model.Cart;
import com.sgd.ecommerce.model.User;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {

	public List<Cart> findByUser(User user);
}
