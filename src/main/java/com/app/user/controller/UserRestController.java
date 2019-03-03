package com.app.user.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/user/add")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@RequestMapping("/user/update/{userid}")
	public ResponseEntity<User> updateUser(@PathVariable Long userid, @RequestBody User reqUser) {
		// TODO Error should be handled in case of no id found in the system for this
		// request
		return userRepository.findById(userid).map(user -> {
			user.setName(reqUser.getName());
			User updated = userRepository.save(user);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		// TODO Error should be handled in case of no id found in the system for this
		// request
		return userRepository.findById(id).map(record -> {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping("/user/list")
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@RequestMapping("/user/login")
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
