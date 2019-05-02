package com.swiggy.authentication.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import com.swiggy.authentication.model.User;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private transient UserRepository userRepository;

	private User user;


	@Test
	public void testRegisterUserSuccess() {
		user = new User("Arsh", "Arshaftab@83", "8756895142", "arshaftab@gmail.com", new Date());
		userRepository.save(user);
		User userToFind = userRepository.findbyUsername(user.getUserName());
		assertThat(userToFind.equals(user));
	}

}
