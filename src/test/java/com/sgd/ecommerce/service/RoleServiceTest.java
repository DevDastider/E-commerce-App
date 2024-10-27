/**
 * 
 */
package com.sgd.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.sgd.ecommerce.dao.RoleDao;
import com.sgd.ecommerce.model.Role;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@SpringBootTest
public class RoleServiceTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewRole_ShouldReturnSavedRole() {
        // Arrange
        Role role = new Role();
        role.setRoleName("USER");
        role.setRoleDescription("User role");

        when(roleDao.save(role)).thenReturn(role);

        // Act
        Role savedRole = roleService.createNewRole(role);

        // Assert
        assertEquals(role, savedRole);
        verify(roleDao).save(role);  // Verify that roleDao.save() was called with the correct argument
    }
}
