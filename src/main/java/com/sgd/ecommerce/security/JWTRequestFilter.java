/**
 * 
 */
package com.sgd.ecommerce.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
		String jwtToken = null;
		String username = null;

		if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER)) {
			jwtToken = authorizationHeader.substring(7);

			try {
				System.out.println("jwtToken= "+jwtToken);
				username = jwtAuthenticationHelper.getUserNameFromToken(jwtToken);
			} catch (IllegalArgumentException ex) {
				System.out.println("Unable to get JWT token");
				System.out.println(ex);
				ex.printStackTrace();
			} catch (ExpiredJwtException ex) {
				System.out.println("JWT token expired");
			}
		} else {
			System.out.println("Required header missing");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtService.loadUserByUsername(username);
			if(jwtAuthenticationHelper.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);

	}

}
