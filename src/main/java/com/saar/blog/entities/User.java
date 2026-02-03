package com.saar.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails{ // implemented with UserDetails because in CustomUserDetailService we are returning user
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false, length = 100)
	private String name;
	private String email;
	private String password;
	private String about;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts= new ArrayList<>();
	//mappedBy = "category" : means foreign key Post entity mai hona chahiye because Post entity mai category name ka variable hai
	//cascade = CascadeType.ALL  : User pe jo action hoga, wahi Post pe bhi lagega, like - User save → posts bhi save
	//fetch = FetchType.LAZY : Posts tabhi database se aayenge jab unki zarurat hogi
	
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	fetch = FetchType.EAGER: When a User is loaded, all related Roles are loaded immediately.
	@JoinTable(name="user_role",         // table name = user_role, 
		joinColumns = @JoinColumn(name="user", referencedColumnName = "id"), // user_role table mai user name ka column hoga jaha userId store hogi, and user Table mai "id" primary key hai jo user_role table mai foreign key ka kaam krega
		inverseJoinColumns=@JoinColumn(name="role", referencedColumnName="id")	)// user_role table mai role name ka column hoga jaha roleId store hogi, and Role Table mai "id" primary key hai jo user_role table mai foreign key ka kaam krega
	private Set<Role> roles=new HashSet<>();
	
	
	// This method returns the roles (authorities) of the user. It converts the roles into SimpleGrantedAuthority objects and returns them as a list.
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() 
		{
			List<SimpleGrantedAuthority>authories= this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			
			return authories;
		}
		
		@Override
		public String getUsername() {//This method returns the user's email as the username.
			return this.email;
		}
}
