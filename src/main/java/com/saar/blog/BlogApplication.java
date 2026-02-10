package com.saar.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saar.blog.config.AppConstants;
import com.saar.blog.entities.Role;
import com.saar.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{
	//CommandLineRunner : “After Spring Boot finishes starting up, run my code once.”

	@Autowired
	private RoleRepo roleRepo;
	
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
		
		
		try {
            Role role =new Role();
            role.setId(AppConstants.ADMIN_USER);
            role.setName("ADMIN");
            
            Role role2 =new Role();
            role2.setId(AppConstants.NORMAL_USER);
            role2.setName("NORMAL");
            List<Role>roles=List.of(role, role2);
            List<Role>result=this.roleRepo.saveAll(roles);
            result.forEach(r->{
            	System.out.println(r.getName());
            });
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
	}
}
