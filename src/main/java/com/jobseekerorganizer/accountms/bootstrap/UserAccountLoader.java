package com.jobseekerorganizer.accountms.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.repositories.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Component
//@RequiredArgsConstructor
public class UserAccountLoader implements CommandLineRunner {
	
	@Autowired
	private UserAccountRepository repository;
	
	@Override
	public void run(String... args) throws Exception{
		loadUserAccObjects();
	}
	
	private void loadUserAccObjects() {
		if(repository.count() == 0) {
			repository.save(UserAccount.builder()
					.fname("Jack")
					.lname("Walsh")
					.email("j.walsh@gmail.com")
					.password("pass1234")
					.build());
			repository.save(UserAccount.builder()
					.fname("Sofie")
					.lname("Campanha")
					.email("sofie.c@gmail.com")
					.password("pass1234")
					.build());
			repository.save(UserAccount.builder()
					.fname("Fernando")
					.lname("Marinho")
					.email("marinhosilva.fernando@gmail.com")
					.password("pass1234")
					.build());
		}
	}
}
 