/**
 * 
 */
package com.sgd.ecommerce.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

	/**
	 * @param pageable
	 * @return
	 */
	public List<Product> findAll(Pageable pageable);
	
	public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(String key1,
			String key2, Pageable pageable);

}
