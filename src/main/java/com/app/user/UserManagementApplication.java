package com.app.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User Management SpringBoot Application
 * 
 * @author Krishnan
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class UserManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}
}
