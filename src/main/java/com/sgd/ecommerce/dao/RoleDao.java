/**
 * 
 */
package com.sgd.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sgd.ecommerce.model.Role;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
