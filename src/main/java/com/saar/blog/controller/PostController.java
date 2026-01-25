package com.saar.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saar.blog.payloads.PostDto;
import com.saar.blog.service.PostService;

@RestController
@RequestMapping("api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	@PostMapping("/add/userId/{userId}/categoryId/{categoryId}")
	ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto postDto1= postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(postDto1,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto postDto1= postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(postDto1,HttpStatus.CREATED);
	}
	
	
	// Get All posts of this category
	@GetMapping("/get/categoryId/{categoryId}")
	ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postDtos= postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/get/userId/{userId}")
	ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> postDtos= postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	@DeleteMapping ("/delete/{postId}")
	ResponseEntity<String> deletePost(@PathVariable Integer postId)
	{
		postService.deletePost(postId);
		return new ResponseEntity<String>("You Deleted postId is :"+postId,HttpStatus.OK);
	}
	
	
	@GetMapping("/get/{postId}")
	ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto postDto=postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	
	@GetMapping("/get")
	ResponseEntity<List<PostDto>> getAllPost()
	{
		List<PostDto> postDtos= postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
}
