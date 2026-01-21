package com.saar.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.blog.entities.Category;
import com.saar.blog.exceptions.ResourceNotFoundException;
import com.saar.blog.payloads.CategoryDto;
import com.saar.blog.repositories.CategoryRepo;
import com.saar.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto,Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	public List<CategoryDto> getAllCategory() {
	    List<Category> allCategory = categoryRepo.findAll();
	    List<CategoryDto> allCategoryDto = allCategory.stream()
	            .map(cat -> modelMapper.map(cat, CategoryDto.class))
	            .collect(Collectors.toList());
	    return allCategoryDto;
	}
	
	CategoryDto categoryToDto(Category category)
	{
		return modelMapper.map(category, CategoryDto.class);
	}
	
	Category dtoToCategory(CategoryDto categoryDto)
	{
		return modelMapper.map(categoryDto, Category.class);
	}

}
