package com.swiggy.authentication.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.authentication.model.User;
import com.swiggy.authentication.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes=com.swiggy.authentication.AuthenticationTestApplication.class)
public class UserControllerTest {

	@Autowired
	private transient MockMvc mockMvc;

	@MockBean
	private transient UserServiceImpl userService;



	private transient User user;
	
	@InjectMocks
	private UserController controller;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("ArshAftab", "Arshaftab@83", "8756895142", "arshaftab@gmail.com", new Date());
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}


	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status()
						.isCreated()).andDo(print());
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}

	
	@Test
	public void login()throws Exception
	{
		when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUserIdAndPassword("ArshAftab", "Arshaftab@83")).thenReturn(user);
		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isOk());
		 verify(userService,times(1)).findByUserIdAndPassword(user.getUserName(),user.getPassword());
		 verifyNoMoreInteractions(userService);
	}

	private String jsonToString(final Object object) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;

	}

}
