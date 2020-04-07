package com.jobseekerorganizer.accountms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jobseekerorganizer.accountms.UserAccountRepository;
import com.jobseekerorganizer.accountms.web.model.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDTO;

import java.lang.module.FindException;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository repository;

	@Override
	public UserAccountDTO create(UserAccountDTO newUserAcc) {
		UserAccount user = newUserAcc.toUserAccount();
		
		UserAccount saved = repository.save(user);
		// verify if user account was saved correctly
		Assert.hasLength(saved.getId(), "Could not create new user account: " + newUserAcc.toString());;
		newUserAcc.setId(saved.getId());
		return newUserAcc;
	}

	@Override
	public UserAccountDTO getByEmail(String email) {
		List<UserAccount> users = repository.findByEmail(email);
		Assert.notEmpty(users, "User account not found with email: " + email);
		UserAccount userFound = users.get(0);
		UserAccountDTO userData = UserAccountDTO.builder().id(userFound.getId()).email(userFound.getEmail())
				.fName(userFound.getFName())
				.lName(userFound.getLName())
				.password(userFound.getPassword())
				.build();
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
	public void update(UserAccountDTO userDTO) {
		Optional<UserAccount> found = repository.findById(userDTO.getId());
		
		Assert.isTrue(found.isPresent(), "User account not found for the given ID");
		
		found.ifPresent(updatedUser -> {
			updatedUser.setFName(userDTO.getFName());
			updatedUser.setLName(userDTO.getLName());
			updatedUser.setPassword(userDTO.getPassword());
//			TODO add profileImage
//			userFound.setFName(userDTO.getFName());
			repository.save(updatedUser);
		});
	}
	@Override
	public Iterable<UserAccount> getAll() {
		return repository.findAll();
	}

}
