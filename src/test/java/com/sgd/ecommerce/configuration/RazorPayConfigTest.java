/**
 * 
 */
package com.sgd.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Configuration
@Profile("test")
public class RazorPayConfigTest {

    @Value("${razorpay.api-key}")
    private String apiKey;

    @Value("${razorpay.secret-key}")
    private String secretKey;

    @Bean(name = "paymentClient")
    public RazorpayClient razorpayClient() {
        try {
            return new RazorpayClient(apiKey, secretKey, true);
        } catch (RazorpayException e) {
            throw new RuntimeException("Exception occurred during payment client creation", e);
        }
    }
}
