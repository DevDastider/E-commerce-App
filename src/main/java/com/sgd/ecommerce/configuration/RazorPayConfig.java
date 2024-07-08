/**
 * 
 */
package com.sgd.ecommerce.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Profile("!test")
public class RazorPayConfig {
	
	private static final String API_KEY = System.getenv("API_KEY");
	private static final String SECRET_KEY = System.getenv("SECRET_KEY");
	private static final Logger LOGGER = LoggerFactory.getLogger(RazorPayConfig.class);

	@Bean(name = "paymentClient")
	public  RazorpayClient razorpayClient() {
        try {
            return new RazorpayClient(API_KEY, SECRET_KEY, true);
        } catch (RazorpayException e) {
			LOGGER.error("Exception occurred during payment client creation", e);
            throw new RuntimeException("Exception occurred during payment client creation", e);
        }
	}
}
