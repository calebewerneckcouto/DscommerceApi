package com.devsuperior.dscommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.tests.UserFactory;
import com.devsuperior.dscommerce.tests.userDetailsFactory;
import com.devsuperior.dscommerce.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;
	@Mock
	private UserRepository repository;
	
	@Mock
	private CustomUserUtil userUtil;
	
	private String existingUsername,nonExistingUserName;
	private User user;
	
	List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setup() throws Exception {
		existingUsername = "maria@gmail.com";
		nonExistingUserName="user@gmail.com";
		
		user = UserFactory.createCustomClientUser(1L, existingUsername);
		userDetails = userDetailsFactory.createCustomAdminUser(existingUsername);
		
		Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUserName)).thenReturn(new ArrayList<>());
		
		Mockito.when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
		Mockito.when(repository.findByEmail(nonExistingUserName)).thenReturn(Optional.empty());
	}
	
	@Test // carregamento UserByName deve retornar UserDteail quando usuario existir....
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existingUsername);
		
		Assertions.assertNotNull(result);//verifica se nao e nulo
		Assertions.assertEquals(result.getUsername(), existingUsername);//Verifica se existe usuario
	}
	
	@Test // carregamento UserByName deve retornar excecao quando usuario nao existir....
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		
		Assertions.assertThrows(UsernameNotFoundException.class, ()->{
			
			service.loadUserByUsername(nonExistingUserName); //lanca excecao caso nao tenha o usuario
		});
		
		
	}
	
	@Test
	public void authenticatedShouldReturnWhenUserExists() {
		Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingUsername);
		
		User result = service.authenticated();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUsername);
	}
	
	
	@Test
	public void authenticatedShouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
		Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();
		
		Assertions.assertThrows(UsernameNotFoundException.class, ()->{
			service.authenticated();
		});
	}
}
