package com.main.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.AdminDto;
import com.main.models.Role;
import com.main.models.Admin;
import com.main.models.AdminPrincipal;
import com.main.repositories.RolesRepository;
import com.main.repositories.AdminsRepository;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
public class AdminsServiceImplementation implements AdminsService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired(required = true)
	private AdminsRepository adminsRepo;

	@Autowired(required = true)
	private RolesRepository rolesRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private Environment env;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
		CompletableFuture<AdminDto> dto = this.getByUsername(username);
		
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
	@Async("asyncExecutor")
	public CompletableFuture<AdminDto> createAdmin(AdminDto admin) {
		try {
			
			Role role = null;
			
			String adminRegSecret = this.env.getProperty("admin.registration.secret");
			
			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			
			Admin entity = mapper.map(admin, Admin.class);
			
			String encPassword = encoder.encode(admin.getPassword());
			
			entity.setEncryptedPassword(encPassword);
			
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String dateString = format.format(new Date());
			
			Date   date  = format.parse ( dateString);
			
			entity.setDate_created(date);
			
			if (admin.getAdminRegSecret().equals(adminRegSecret)) {
				
				role = rolesRepo.findById(1L).get();
				
				entity.setRole(role);
				
			} else {
				
				role = rolesRepo.findById(2L).get();
				
				entity.setRole(role);
			}
			
			adminsRepo.saveAndFlush(entity);
			
			AdminDto returnDto = mapper.map(entity, AdminDto.class);
			
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
	public CompletableFuture<AdminDto> update(AdminDto user) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Admin admin = null; 
		AdminDto returnObject = null;
		
		try {
			
			admin = this.adminsRepo.findById(user.getId()).get();
			
			Role role = admin.getRole();
			
		    this.mapper.map(user, admin);
		    
		    admin.role = role;
		    
		    returnObject = new AdminDto();
		    
		    this.mapper.map(admin, returnObject);
		    
		    this.adminsRepo.saveAndFlush(admin);
		    
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
		
		Optional<Admin> adminOptional = null;
		
		try {
			this.adminsRepo.deleteById(id);
			
			adminOptional = this.adminsRepo.findById(id);
			
			if(adminOptional.get() != null) {
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
	public CompletableFuture<AdminDto> getAdminDetails(String id) {
		
		AdminDto adminDetails = null;
		Admin admin = null;
		try {
			
			admin = this.adminsRepo.findById(id).get();
			
			if (admin != null) {
				
				adminDetails = this.mapper.map(admin, AdminDto.class);
				
				return CompletableFuture.completedFuture(adminDetails);
				
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
	public CompletableFuture<AdminDto> getByUsername(String userName) {
		
		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<Admin> userEntity = adminsRepo.findAll().stream().filter(x->x.getUsername().equals(userName)).findFirst();
		
		AdminDto dtoToReturn = mapper.map(userEntity.get(), AdminDto.class);
		
		return CompletableFuture.completedFuture(dtoToReturn);
	}
	
	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<List<AdminDto>> getAdmins() {
		
		List<AdminDto> adminDetails = new ArrayList<AdminDto>();
		Type listType = new TypeToken<List<AdminDto>>() {}.getType();
		
		try {
		
			adminDetails = this.mapper.map(this.adminsRepo.findAll(),listType);
			
			
			return CompletableFuture.completedFuture(adminDetails);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return null;
	}

}
