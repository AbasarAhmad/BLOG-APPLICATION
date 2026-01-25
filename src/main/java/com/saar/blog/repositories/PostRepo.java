package com.saar.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saar.blog.entities.Category;
import com.saar.blog.entities.Post;
import com.saar.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findAllByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}
