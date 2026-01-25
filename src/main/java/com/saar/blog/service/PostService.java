package com.saar.blog.service;

import java.util.List;

import com.saar.blog.payloads.PostDto;
import com.saar.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	void deletePost(Integer postId);
//	List<PostDto> getAllPost();
	List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
	// get Single Post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto>getPostByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto>getPostsByUser(Integer userId);
	// search posts
	List<PostDto> searchPosts(String keywords);
}
