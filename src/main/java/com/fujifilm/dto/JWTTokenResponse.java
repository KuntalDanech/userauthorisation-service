package com.fujifilm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTTokenResponse {
	private Boolean status;
	private Integer code;
	private String token;
}