/**
 * 
 */
package com.sgd.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgd.ecommerce.model.Product;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Repository
public interface ProductDao extends CrudRepository<Product, Integer>{

}
