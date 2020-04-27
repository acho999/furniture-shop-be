package com.main.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.UserDto;
import com.main.models.Role;
import com.main.models.User;
import com.main.models.UserPrincipal;
import com.main.repositories.CustomersServiceClient;
import com.main.repositories.RolesRepository;
import com.main.repositories.UsersRepository;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
public class UsersServiceImplementation implements UsersService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired(required = true)
	private UsersRepository usersRepo;

	@Autowired(required = true)
	private RolesRepository rolesRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CustomersServiceClient client;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
		CompletableFuture<UserDto> dto = this.getByUsername(username);
		
		User entity = mapper.map(dto.get(), User.class);
		
		UserPrincipal userPrincipal = new UserPrincipal(entity);
		
		return userPrincipal;
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}
	
	
	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<UserDto> createUser(UserDto user) {
		try {
			
			Role role = null;
			
			String adminRegSecret = this.env.getProperty("admin.registration.secret");
			
			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			User entity = mapper.map(user, User.class);
			
			String encPassword = encoder.encode(user.getPassword());
			
			entity.setEncryptedPassword(encPassword);
			
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String dateString = format.format(new Date());
			
			Date   date  = format.parse ( dateString);
			
			entity.setDate_created(date);
			
			if (user.getAdminRegSecret().equals(adminRegSecret)) {
				
				role = rolesRepo.findById(1L).get();
				
				entity.setRole(role);
				
			} else {
				
				role = rolesRepo.findById(2L).get();
				
				entity.setRole(role);
			}
			
			usersRepo.saveAndFlush(entity);
			
			UserDto returnDto = mapper.map(entity, UserDto.class);
			
			this.client.createCustomer();
			
			return CompletableFuture.completedFuture(returnDto);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<UserDto> update(UserDto user) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		User userEntity = null; 
		UserDto returnObject = null;
		
		try {
			
			userEntity = this.usersRepo.findById(user.getId()).get();
			
			Role role = user.getRole();
			
		    this.mapper.map(user, userEntity);
		    
		    userEntity.role = role;
		    
		    returnObject = new UserDto();
		    
		    this.mapper.map(userEntity, returnObject);
		    
		    this.usersRepo.saveAndFlush(userEntity);
		    
		    return CompletableFuture.completedFuture(returnObject);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return CompletableFuture.completedFuture(returnObject);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(String id) {
		
		Optional<User> userOptional = null;
		
		try {
			this.usersRepo.deleteById(id);
			
			userOptional = this.usersRepo.findById(id);
			
			if(userOptional.get() != null) {
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return true;
		
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<UserDto> getUserDetails(String id) {
		
		UserDto userDetails = null;
		User user = null;
		try {
			
			user = this.usersRepo.findById(id).get();
			
			if (user != null) {
				
				userDetails = this.mapper.map(user, UserDto.class);
				
				return CompletableFuture.completedFuture(userDetails);
				
			}
		
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	@Async("asyncExecutor")
	public CompletableFuture<UserDto> getByUsername(String userName) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<User> userEntity = usersRepo.findAll().stream().filter(x->x.getUsername().equals(userName)).findFirst();
		
		UserDto dtoToReturn = mapper.map(userEntity.get(), UserDto.class);
		
		return CompletableFuture.completedFuture(dtoToReturn);
	}
	
	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<List<UserDto>> getUsers() {
		
		List<UserDto> userDetails = new ArrayList<UserDto>();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		
		try {
		
			userDetails = this.mapper.map(this.usersRepo.findAll(),listType);
			
			
			return CompletableFuture.completedFuture(userDetails);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return null;
	}

}
