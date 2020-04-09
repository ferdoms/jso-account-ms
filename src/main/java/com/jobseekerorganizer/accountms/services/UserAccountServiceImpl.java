package com.jobseekerorganizer.accountms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.repositories.UserAccountRepository;
import com.jobseekerorganizer.accountms.web.mappper.UserAccountMapper;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
// TODO remove slf4j if not used
@Slf4j
@Service
public class UserAccountServiceImpl implements UserAccountService {
	// TODO use UserAccountMapper implementation
	@Autowired
	private UserAccountRepository repository;
	
	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	public UserAccountDto create(UserAccountDto newUserAcc) {
		UserAccount user = UserAccount.builder()
						.email(newUserAcc.getEmail())
						.fname(newUserAcc.getFname())
						.lname(newUserAcc.getLname())
						.password(newUserAcc.getPassword()).build();
		
		UserAccount saved = repository.save(user);
		// verify if user account was saved correctly
		Assert.hasLength(saved.getId(), "Could not create new user account: " + newUserAcc.toString());;
		newUserAcc.setId(saved.getId());
		return newUserAcc;
	}

	@Override
	public UserAccountDto getByEmail(String email) {	
		List<UserAccount> users = repository.findByEmail(email);
		Assert.notEmpty(users, "User account not found with email: " + email);
		UserAccount userFound = users.get(0);
		UserAccountDto userData = userAccountMapper.userAccountToUserAccountDto(userFound);
		return userData;
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<UserAccount> getById(String id) {
		return repository.findById(id);
	}

	@Override
	public void update(String userId, UserAccountDto userDTO) {
		Optional<UserAccount> found = repository.findById(userId);
		
		Assert.isTrue(found.isPresent(), "User account not found for the given ID");
		
		found.ifPresent(updatedUser -> {
			updatedUser.setFname(userDTO.getFname());
			updatedUser.setLname(userDTO.getLname());
			updatedUser.setPassword(userDTO.getPassword());
//			TODO add profileImage
			repository.save(updatedUser);
		});
	}
	@Override
	public Iterable<UserAccount> getAll() {
		return repository.findAll();
	}

}
