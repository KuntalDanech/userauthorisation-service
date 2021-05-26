package com.fujifilm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author pce16
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	private String userName;
	private String password;
}