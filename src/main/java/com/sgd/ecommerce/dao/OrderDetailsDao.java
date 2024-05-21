/**
 * 
 */
package com.sgd.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgd.ecommerce.model.OrderDetails;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
public interface OrderDetailsDao extends CrudRepository<OrderDetails, Integer> {

}
