package com.saar.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{
	//CommandLineRunner : “After Spring Boot finishes starting up, run my code once.”

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	// this method we wrote only to convert current password "Riyaz123" to encode and encoded value will write into db
	// that's why we implemented CommandLineRunner, means after runnig application first will execute below  function
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Riyaz123"));
		
	}
}
