package com.saar.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Integer id;
	@NotBlank
	@Size(min=4, message="Username must be min of 4 characters !!")
	private String name;
	
	@Email(message="Email address is not valid !!")
	private String email;
	
	@NotNull
	@Size(min=3, max=10, message="Password must be min of 3 chars and max of 10 chars !!")
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> roles =new HashSet<>();
}
