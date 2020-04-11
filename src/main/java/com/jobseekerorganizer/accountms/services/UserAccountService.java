package com.jobseekerorganizer.accountms.services;

import java.util.Optional;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountProfileDto;

/**
 * @author ferdoms
 *
 */
public interface UserAccountService {
	UserAccountDto create(UserAccountDto newUserAcc);
	UserAccountDto getByEmail(String email);
	void delete(String id);
	Optional<UserAccount> getById(String id);
	void updateProfile(String userId, UserAccountProfileDto userProfileDTO);
	void updatePassword(String userId, String password);
	Iterable<UserAccount>getAll();
}
