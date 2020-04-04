package com.jobseekerorganizer.repositories;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.jobseekerorganizer.datamodels.UserAccount;

@EnableScan
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {

}
