/**
 * 
 */
package com.sgd.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgd.ecommerce.model.User;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Repository
public interface UserDao extends CrudRepository<User, String>{

}
