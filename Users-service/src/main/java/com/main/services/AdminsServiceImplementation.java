package com.main.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.UserDto;
import com.main.models.Role;
import com.main.models.Admin;
import com.main.models.AdminPrincipal;
import com.main.repositories.RolesRepository;
import com.main.repositories.AdminsRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
public class AdminsServiceImplementation implements AdminsService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired(required = true)
	private AdminsRepository usersRepo;

	@Autowired(required = true)
	private RolesRepository rolesRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private Environment env;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
		
		CompletableFuture<UserDto> dto = this.getByUsername(username);
		
		Admin entity = mapper.map(dto.get(), Admin.class);
		
		AdminPrincipal userPrincipal = new AdminPrincipal(entity);
		
		return userPrincipal;
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async
	public CompletableFuture<UserDto> createUser(UserDto user) {
		try {
			
			Role role = null;
			
			String adminRegSecret = this.env.getProperty("admin.registration.secret");
			
			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			Admin entity = mapper.map(user, Admin.class);
			
			String encPassword = encoder.encode(user.getPassword());
			
			entity.setId(UUID.randomUUID().toString());
			
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
			
			return CompletableFuture.completedFuture(returnDto);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	@Transactional(readOnly = false)
	public void update(UserDto user) {
		
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		
	}

	@Override
	@Transactional(readOnly = false)
	public CompletableFuture<UserDto> getUserDetails(String id) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public CompletableFuture<UserDto> getByUsername(String userName) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<Admin> userEntity = usersRepo.findAll().stream().filter(x->x.getUsername().equals(userName)).findFirst();
		
		UserDto dtoToReturn = mapper.map(userEntity.get(), UserDto.class);
		
		return CompletableFuture.completedFuture(dtoToReturn);
	}

}
