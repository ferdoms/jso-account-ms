package com.jobseekerorganizer.accountms.repositories;


import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.jobseekerorganizer.accountms.domain.UserAccount;

@EnableScan
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
	List<UserAccount> findByEmail(String email);
}
