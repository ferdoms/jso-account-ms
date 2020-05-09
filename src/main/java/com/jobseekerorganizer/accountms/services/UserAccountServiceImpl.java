package com.jobseekerorganizer.accountms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.repositories.UserAccountRepository;
import com.jobseekerorganizer.accountms.web.mappper.UserAccountMapper;
import com.jobseekerorganizer.accountms.web.model.PasswordDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountProfileDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	@Autowired
	private UserAccountRepository repository;

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Override
	public UserAccountDto create(UserAccountDto newUserAcc) {
		UserAccount user = userAccountMapper.userAccountDtoToUserAccount(newUserAcc);

		UserAccount saved = repository.save(user);
		// verify if user account was saved correctly
		Assert.hasLength(saved.getId(), "Could not create new user account: " + newUserAcc.toString());

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
	public UserAccountDto getById(String id) {
		Optional<UserAccount> found = repository.findById(id);
		Assert.isTrue(found.isPresent(), "User account not found for the given ID");
		UserAccountDto userDTO = userAccountMapper.userAccountToUserAccountDto(found.get());
		return userDTO;
	}
	@Override
	public UserAccountProfileDto getProfileById(String id) {
		Optional<UserAccount> found = repository.findById(id);
		Assert.isTrue(found.isPresent(), "User account not found for the given ID");
		UserAccountProfileDto userDTO = userAccountMapper.userAccountToUserAccountProfileDto(found.get());
		return userDTO;
	}

	@Override
	public void updateProfile(String userId, UserAccountProfileDto userDTO) {
		Optional<UserAccount> found = repository.findById(userId);

		Assert.isTrue(found.isPresent(), "User account not found for the given ID");

		found.ifPresent(updatedUser -> {
			updatedUser.setFname(userDTO.getFname());
			updatedUser.setLname(userDTO.getLname());
			updatedUser.setEmail(userDTO.getEmail());
			updatedUser.setProfileImage(userDTO.getProfileImage());
			repository.save(updatedUser);
		});
	}

	@Override
	public Iterable<UserAccount> getAll() {
		return repository.findAll();
	}

	@Override
	public void updatePassword(String userId, PasswordDto password) {
		Optional<UserAccount> userFound = repository.findById(userId);

		Assert.isTrue(userFound.isPresent(), "User account not found for the given ID");

		UserAccount user = userFound.get();
		user.setPassword(password.getPassword());
		repository.save(user);
	}

}
