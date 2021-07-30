package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.*;
import com.example.exception.UserNotFound;
import com.example.demo.Properties;
import com.example.demo.User;

// Developer Control


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	// To get All Users
	
	@GetMapping("/get")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	// Get a Single user
	
	  @GetMapping("/get/{id}")
	  @PreAuthorize("hasRole('ADMIN')")
	  User oneUser(@PathVariable Long id) {

	    return userRepository.findById(id)
	      .orElseThrow(() -> new UserNotFound(id));
	  }
	
	  // Using Post Method to add a user
	  
	@PostMapping("/add")
	public void createUser(@RequestBody User user) {
		userRepository.save(user);
	}
	
	
	// Using Put Method to update a User
	 @PutMapping("/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public void updateUser(@RequestBody User renew) {
	 userRepository.save(renew);
	     
	  }
	
	 
	 // Delete Method to delete a User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = { "/{id}" })
	public User deleteUser(@PathVariable("id") long id) {
		User user = userRepository.getOne(id);
		userRepository.deleteById(id);
		return user;
	}
}
