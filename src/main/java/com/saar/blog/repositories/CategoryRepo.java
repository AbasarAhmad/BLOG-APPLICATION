package com.saar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saar.blog.entities.Category;

public interface CategoryRepo  extends JpaRepository<Category, Integer>{

}
