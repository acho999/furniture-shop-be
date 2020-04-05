package com.main.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.UserDto;
import com.main.models.Role;
import com.main.models.User;
import com.main.models.UserPrincipal;
import com.main.repositories.RolesRepository;
import com.main.repositories.UsersRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDto dto = this.getByUsername(username);
		
		User entity = mapper.map(dto, User.class);
		
		UserPrincipal userPrincipal = new UserPrincipal(entity);
		
		return userPrincipal;
	}

	@Override
	@Transactional(readOnly = false)
	public UserDto createUser(UserDto user) {
		try {
			
			Role role = null;
			
			String adminRegSecret = this.env.getProperty("admin.registration.secret");
			
			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			User entity = mapper.map(user, User.class);
			
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
			
			return returnDto;
			
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
	public void delete(long id) {
		
	}

	@Override
	@Transactional(readOnly = false)
	public UserDto getUserDetails(long id) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto getByUsername(String userName) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<User> userEntity = usersRepo.findAll().stream().filter(x->x.getUsername().equals(userName)).findFirst();
		
		UserDto dtoToReturn = mapper.map(userEntity.get(), UserDto.class);
		
		return dtoToReturn;
	}

}
