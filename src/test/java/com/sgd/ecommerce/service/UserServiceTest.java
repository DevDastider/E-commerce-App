/**
 * 
 */
package com.sgd.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sgd.ecommerce.dao.UserDao;
import com.sgd.ecommerce.model.Role;
import com.sgd.ecommerce.model.User;

/**
*
* @author Sayantan Ghosh Dastider
*
*/
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterNewUser_ShouldReturnUserWithEncryptedPasswordAndUserRole() {
        // Arrange
        User user = new User();
        user.setUserPassword("plainPassword");

        Role userRole = new Role();
        userRole.setRoleName("USER");
        userRole.setRoleDescription("Default user role");
        
        Set<Role> expectedRoles = new HashSet<>();
        expectedRoles.add(userRole);

        when(passwordEncoder.encode("plainPassword")).thenReturn("encryptedPassword");
        when(userDao.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User savedUser = userService.registerNewUser(user);

        // Assert
        assertEquals("encryptedPassword", savedUser.getUserPassword());
        assertEquals(expectedRoles.size(), savedUser.getRole().size());
        assertTrue(savedUser.getRole().contains(userRole));

        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterNewAdmin_ShouldReturnUserWithEncryptedPasswordAndAdminRole() {
        // Arrange
        User admin = new User();
        admin.setUserPassword("plainPassword");

        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        adminRole.setRoleDescription("Default admin role");

        Set<Role> expectedRoles = new HashSet<>();
        expectedRoles.add(adminRole);

        when(passwordEncoder.encode("plainPassword")).thenReturn("encryptedPassword");
        when(userDao.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        User savedAdmin = userService.registerNewAdmin(admin);

        // Assert
        assertEquals("encryptedPassword", savedAdmin.getUserPassword());
        assertEquals(expectedRoles.size(), savedAdmin.getRole().size());
        assertTrue(savedAdmin.getRole().contains(adminRole));

        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    public void testGetEncryptedPassword_ShouldReturnEncryptedPassword() {
        // Arrange
        String rawPassword = "plainPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn("encryptedPassword");

        // Act
        String encryptedPassword = userService.registerNewUser(new User()).getUserPassword();

        // Assert
        assertEquals("encryptedPassword", encryptedPassword);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
