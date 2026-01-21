package com.saar.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	@Size(min=5,message = "Write category of post minimum 5 letter")
	private String categoryTitle;
	@NotEmpty
	@Size(min=12,message="write something about post description")
	private String categoryDescription;
}
