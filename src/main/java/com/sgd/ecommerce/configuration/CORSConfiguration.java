/**
 * 
 */
package com.sgd.ecommerce.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.sgd.ecommerce.util.Constants.GET;
import static com.sgd.ecommerce.util.Constants.POST;
import static com.sgd.ecommerce.util.Constants.PUT;
import static com.sgd.ecommerce.util.Constants.DELETE;;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Configuration
public class CORSConfiguration {

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods(GET, POST, PUT, DELETE)
						.allowedHeaders("*")
						.allowedOriginPatterns("*")
						.allowCredentials(true);
			}
		};
	}
}
