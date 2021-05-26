package com.fujifilm.model;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Long id;
	private String userName;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean status;
	private Set<String> roles;
}
