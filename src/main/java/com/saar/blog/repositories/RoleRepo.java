package com.saar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saar.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
