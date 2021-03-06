package com.onlineclothing.springboot.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.onlineclothing.springboot.entities.Users;


@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void addUserTest() {
		Users user1 = new Users();
		user1.setEmail("john@abc.com");
		user1.setFirstName("John");
		user1.setLastName("Cena");
		user1.setRole("user");
		userRepository.save(user1);
	}
	

}
