package com.saar.blog.service;

import java.util.List;

import com.saar.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer cId);
	void deleteCategory(Integer cId);
	CategoryDto getCategory(Integer cId);
	List<CategoryDto>getAllCategory();
}
