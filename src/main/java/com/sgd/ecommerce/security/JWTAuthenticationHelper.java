/**
 * 
 */
package com.sgd.ecommerce.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author Sayantan Ghosh Dastider
 *
 */
@Component
public class JWTAuthenticationHelper {

	private static final String SECRET_KEY = "sgd7";
	private static final int TOKEN_VALIDATY = 3600 * 5;

	public String getUserNameFromToken(final String token) {
		return getClaimsFromToken(token, Claims::getSubject);
	}

	private static <T> T getClaimsFromToken(final String token, final Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private static Claims getAllClaimsFromToken(final String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private static boolean isTokenExpired(String token) {
		Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	private static Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
}
