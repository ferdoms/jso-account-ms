package com.jobseekerorganizer.accountms.services;

import java.util.Optional;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;

/**
 * @author ferdoms
 *
 */
public interface UserAccountService {
	UserAccountDto create(UserAccountDto newUserAcc);
	UserAccountDto getByEmail(String email);
	void delete(String id);
	Optional<UserAccount> getById(String id);
	void update(String userId, UserAccountDto userDTO);
	Iterable<UserAccount>getAll();
}
