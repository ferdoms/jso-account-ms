package com.jobseekerorganizer.accountms;


import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.jobseekerorganizer.accountms.web.model.UserAccount;

@EnableScan
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
	List<UserAccount> findByEmail(String email);
}
