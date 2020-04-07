package com.jobseekerorganizer.accountms.services;

import java.util.List;
import java.util.Optional;

import com.jobseekerorganizer.accountms.web.model.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDTO;

public interface UserAccountService {
	UserAccountDTO create(UserAccountDTO newUserAcc);
	UserAccountDTO getByEmail(String email);
	void delete(String id);
	Optional<UserAccount> getById(String id);
	void update(UserAccountDTO userDTO);
	Iterable<UserAccount>getAll();
}
