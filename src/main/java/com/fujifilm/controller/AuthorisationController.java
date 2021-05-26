package com.fujifilm.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fujifilm.dto.JWTTokenResponse;
import com.fujifilm.dto.LoginRequest;
import com.fujifilm.util.JWTTokenUtil;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthorisationController {

	private static final Logger LOGGER = Logger.getLogger(AuthorisationController.class.getName());
	
	@Autowired
	public AuthenticationManager authManager;
	
	@Autowired
	private JWTTokenUtil jwtUtil;
	
	@PostMapping("token")
	public ResponseEntity<JWTTokenResponse> token(@RequestBody LoginRequest request){
		LOGGER.info("token API has been called");
		// If it is valid then token will be generated otherwise InvalidUserAuthEntrypoint
		// will be triggered.
		Authentication auth = authManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								request.getUserName(),
								request.getPassword()));
		
		LOGGER.info(auth.getName()+" :: "+auth.getAuthorities());
		String token = jwtUtil.generateToken(request.getUserName());
		return ResponseEntity.ok(new JWTTokenResponse(true, 200, token));
	}
}