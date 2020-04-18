package com.main.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.models.User;
import com.main.models.UserPrincipal;
import com.main.repositories.UsersRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
public class UsersServiceImplementation implements UsersService {

	@Autowired
	private ModelMapper mapper;

	@Autowired(required = true)
	private UsersRepository usersRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			CompletableFuture<User> user = this.getByUsername(username);

			User entity = mapper.map(user.get(), User.class);

			UserPrincipal userPrincipal = new UserPrincipal(entity);

			return userPrincipal;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	@Async("asyncExecutor")
	public CompletableFuture<User> getByUsername(String userName) {

		Optional<User> userEntity = usersRepo.findAll().stream().filter(x -> x.getUsername().equals(userName))
				.findFirst();
		
		User user = userEntity.get();

		return CompletableFuture.completedFuture(user);
	}

}