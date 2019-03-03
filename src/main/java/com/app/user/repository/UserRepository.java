package com.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.user.models.User;

/**
 * @author Krishnan
 * 
 *         Repository class for User object
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
