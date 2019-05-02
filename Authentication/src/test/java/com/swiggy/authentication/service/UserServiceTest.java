package com.swiggy.authentication.service;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.swiggy.authentication.exception.UserAlreadyExistsException;
import com.swiggy.authentication.exception.UserNotFoundException;
import com.swiggy.authentication.model.User;
import com.swiggy.authentication.repository.UserRepository;

public class UserServiceTest {
	@Mock
	UserRepository repository;
	
	private User user;
	
	@InjectMocks
	UserServiceImpl service;
	
   @Mock
	BCryptPasswordEncoder encryptor;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		user = new User("ArshAftab", "Arshaftab@83", "8756895142", "arshaftab@gmail.com", new Date());
		
	}
	
	@Test
	public void testSaveUser() throws UserAlreadyExistsException
	{
		
		when(repository.findbyUsername(user.getUserName())).thenReturn(null);
		when(repository.save(user)).thenReturn(user);
		when(encryptor.encode(user.getPassword())).thenCallRealMethod();
		boolean flag=service.saveUser(user);
		assertEquals("can Register user",true,flag);
		verify(repository, times(1)).save(user);
		verify(repository, times(1)).findbyUsername(user.getUserName());
		verify(encryptor,times(1)).encode(Mockito.anyString());
	}
	
	@Test(expected=UserAlreadyExistsException.class)
	public void testSaveUserFailure() throws UserAlreadyExistsException 
	{
		when(repository.findbyUsername(user.getUserName())).thenReturn(user);
		when(repository.save(user)).thenReturn(user);
		
		boolean flag = service.saveUser(user);
		assertTrue("saving user failed", flag);
		verify(repository, times(1)).findbyUsername(user.getUserName());
		
	}
	
	@Test
	public void testValidateSuccess() throws UserNotFoundException
	{
		when(repository.findbyUsername(user.getUserName())).thenReturn(user);
		when(encryptor.matches("Arshaftab@83",user.getPassword())).thenReturn(true);
		User userResult = service.findByUserIdAndPassword(user.getUserName(), user.getPassword());
		assertNotNull(userResult);
		assertEquals(user.getId(), userResult.getId());
		verify(repository, times(1)).findbyUsername(user.getUserName());
		verify(encryptor,times(1)).matches(Mockito.anyString(),Mockito.anyString());
	}

}
