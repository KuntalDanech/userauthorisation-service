package com.fujifilm.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT Token Generation Logic 
 * 
 * @author pce16
 *
 */
@Component
public class JWTTokenUtil {

	@Value("{fujifilm.secret}")
	private String secret;

	@Value("{fujifilm.issuer}")
	private String issuer;
	
	/**
	 * Generate Token Logic
	 * 
	 * @param subject
	 * @return
	 */
	public String generateToken(String subject) {
		
		// Java JWT Token
		return Jwts.builder()
				// # This is username
				.setSubject(subject)
				// # Who issue the token
				.setIssuer(issuer)
				// # When token has been issued ?
				.setIssuedAt(new Date(System.currentTimeMillis()))
				// # Which signature algorithm, We have used with secret key
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}
}
