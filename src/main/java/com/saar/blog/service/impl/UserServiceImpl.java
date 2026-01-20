package com.saar.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.blog.entities.User;
import com.saar.blog.exceptions.ResourceNotFoundException;
import com.saar.blog.payloads.UserDto;
import com.saar.blog.repositories.UserRepo;
import com.saar.blog.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private	UserRepo userRepo;
	
	@Override
	public UserDto addUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User createUser=userRepo.save(user);
		return this.userToDto(createUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		 User user = this.userRepo.findById(userId)
			        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		 user.setName(userDto.getName());
		 user.setPassword(userDto.getPassword());
		 user.setEmail(userDto.getEmail());
		 user.setAbout(userDto.getAbout());
		 User updatedUser=userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		 User user = this.userRepo.findById(userId)
			        .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUser=userRepo.findAll();
		List<UserDto> userDtos=allUser.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}
	/**
	 * allUser.stream(): यह allUser (जो कि एक List<User> है) को Stream में बदल देता है।
	Stream डेटा को प्रोसेस करने के लिए एक पाइपलाइन प्रदान करता है, जिसमें आप डेटा को फ़िल्टर, मैप, और कलेक्ट कर सकते हैं।
	
    map(user -> this.userToDto(user)): यह Stream के हर user को प्रोसेस करता है। userToDto(user) एक मेथड है, जो User ऑब्जेक्ट को UserDto में बदलता है।
	यहां user -> this.userToDto(user) एक lambda expression है, जो हर user पर यह मेथड लागू करता है।
	
	collect(Collectors.toList()): प्रोसेस किए गए Stream को वापस List में बदल देता है। इसका मतलब है कि प्रोसेसिंग के बाद जो भी UserDto ऑब्जेक्ट्स तैयार हुए हैं, उन्हें एक नई List (userDtos) में इकट्ठा कर लिया गया।*/

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);

	}
	
	// Converting Dto to User
	User dtoToUser(UserDto userDto)
	{
		User user= new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		return user;
	}
	
	// Converting User to Dto
	UserDto userToDto(User user)
	{
		UserDto ud=new UserDto();
		ud.setId(user.getId());
		ud.setName(user.getName());
		ud.setEmail(user.getEmail());
		ud.setPassword(user.getPassword());
		ud.setAbout(user.getAbout());
		return ud;
	}

	

}
