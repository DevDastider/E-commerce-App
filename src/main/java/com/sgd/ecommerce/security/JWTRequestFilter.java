/**
 * 
 */
package com.sgd.ecommerce.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sgd.ecommerce.exception.GeneralServiceException;
import com.sgd.ecommerce.exception.ServiceRuntimeException;
import com.sgd.ecommerce.service.JWTService;
import com.sgd.ecommerce.util.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTAuthenticationHelper jwtAuthenticationHelper;

	@Autowired
	private JWTService jwtService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(JWTRequestFilter.class);
	public static String CURRENT_USER = "";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

		try {
			final String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
			String jwtToken = null;
			String username = null;
			if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER)) {
				jwtToken = authorizationHeader.substring(7);

				try {
					username = jwtAuthenticationHelper.getUserNameFromToken(jwtToken);
					CURRENT_USER = username;
				} catch (IllegalArgumentException ex) {
					LOGGER.error("Unable to get JWT token" + ex);
					throw new ServiceRuntimeException("Error during fetching username from token", ex);
				} catch (ExpiredJwtException ex) {
					LOGGER.info("JWT token expired");
					throw new ServiceRuntimeException("Credentials token expired", ex);
				}
			} else {
				LOGGER.warn("Authorization header missing");
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = jwtService.loadUserByUsername(username);
				if (jwtAuthenticationHelper.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}

			filterChain.doFilter(request, response);
		} catch (IOException | ServletException ex) {
			throw new ServiceRuntimeException("Error occurred during user authentication", ex);
		}
	}

}
