/**
 * 
 */
package com.sgd.ecommerce.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgd.ecommerce.model.OrderDetails;
import com.sgd.ecommerce.model.OrderInput;
import com.sgd.ecommerce.model.TransactionDetail;
import com.sgd.ecommerce.service.OrderDetailsService;

/**
*
* @author Sayantan Ghosh Dastider
*
*/
public class OrderDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderDetailsService orderDetailsService;

    @InjectMocks
    private OrderDetailsController orderDetailsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderDetailsController).build();
    }

    @Test
    @WithMockUser(roles = "user")
    public void testPlaceOrder() throws Exception {
        OrderInput orderInput = new OrderInput();  // Populate with test data

        doNothing().when(orderDetailsService).placeOrder(any(OrderInput.class), eq(true));

        mockMvc.perform(post("/placeOrder/{isCartCheckout}", true)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderInput)))
                .andExpect(status().isOk());

        verify(orderDetailsService, times(1)).placeOrder(any(OrderInput.class), eq(true));
    }

    @Test
    @WithMockUser(roles = "user")
    public void testGetOrderDetails() throws Exception {
        List<OrderDetails> orderDetailsList = Arrays.asList(new OrderDetails(), new OrderDetails());

        when(orderDetailsService.getOrderDetails()).thenReturn(orderDetailsList);

        mockMvc.perform(get("/getOrderDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orderDetailsList.size()));

        verify(orderDetailsService, times(1)).getOrderDetails();
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testGetAllOrderDetails() throws Exception {
        String orderStatus = "pending";
        List<OrderDetails> orderDetailsList = Arrays.asList(new OrderDetails(), new OrderDetails());

        when(orderDetailsService.getAllOrderDetails(orderStatus)).thenReturn(orderDetailsList);

        mockMvc.perform(get("/getAllOrderDetails/{status}", orderStatus))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orderDetailsList.size()));

        verify(orderDetailsService, times(1)).getAllOrderDetails(orderStatus);
    }

    @Test
    @WithMockUser(roles = "admin")
    public void testMarkOrderDelivered() throws Exception {
        Integer orderId = 1;

        doNothing().when(orderDetailsService).markOrderDelivered(orderId);

        mockMvc.perform(get("/orderDelivered/{orderId}", orderId))
                .andExpect(status().isOk());

        verify(orderDetailsService, times(1)).markOrderDelivered(orderId);
    }

    @Test
    @WithMockUser(roles = "user")
    public void testCreateTransaction() throws Exception {
        Double amount = 100.0;
        TransactionDetail transactionDetail = new TransactionDetail("orderId", 100, "INR", "api-key");

        when(orderDetailsService.createPaymentTransaction(amount)).thenReturn(transactionDetail);

        mockMvc.perform(get("/createTransaction/{amount}", amount))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(transactionDetail.getOrderId()));

        verify(orderDetailsService, times(1)).createPaymentTransaction(amount);
    }

    // Utility method to convert objects to JSON strings
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
