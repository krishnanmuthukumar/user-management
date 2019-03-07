package com.app.user.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.models.User;
import com.app.user.repository.UserRepository;

/**
 * @author Krishnan
 *
 *         UserRestController is for defining user management APIs
 * 
 */

@RestController
@RequestMapping("/api/v1/")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/user")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User reqUser) {
		// TODO Error should be handled in case of no id found in the system for this
		// request
		return userRepository.findById(id).map(user -> {
			user.setName(reqUser.getName());
			User updated = userRepository.save(user);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		// TODO Error should be handled in case of no id found in the system for this
		// request
		return userRepository.findById(id).map(record -> {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/users")
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@RequestMapping("/login")
	public ResponseEntity<?> login(@RequestBody User reqUser) {
		User objUser = userRepository.findByEmail(reqUser.getEmail());
		// TODO Exception should be handled
		if (objUser.getPassword().equals(reqUser.getPassword())) {
			Date date = new Date();
			objUser.setLastLogin(date);
			User updated = userRepository.save(objUser);
			return ResponseEntity.ok().body(updated);
		} else {
			return ResponseEntity.badRequest().body("Login Failed");
		}
	}

}
