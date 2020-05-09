package com.jobseekerorganizer.accountms.providers;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jobseekerorganizer.accountms.domain.AuthenticationTokenImpl;
import com.jobseekerorganizer.accountms.domain.SessionUser;
import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.services.RedisService;
import com.jobseekerorganizer.accountms.services.UserAccountService;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserAccountService userAccountService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";

		
		UserAccountDto userAccount = userAccountService.getByEmail(username);
		if (username == null || username.length() < 5 || (userAccount == null)) {
			System.out.println("bad user");
			throw new BadCredentialsException("User with the given email not found.");
		}
		if (password.length() < 8 || !(password.equals(userAccount.getPassword()))) {
			throw new BadCredentialsException("User with the given email not found.");
		}
		SessionUser userSession = new SessionUser();
		userSession.setUserId(userAccount.getId());
		userSession.setCreatedAt(new Date());
		AuthenticationTokenImpl auth = new AuthenticationTokenImpl(userSession.getUserId(), Collections.emptyList());
		auth.setAuthenticated(true);
		auth.setDetails(userSession);  
		redisService.setValue(String.format("%s:%s", userSession.getUserId().toLowerCase(), auth.getHash()),
				userSession, TimeUnit.SECONDS, 3600L, true);
		return auth;
	}

	@Override
	public boolean supports(Class<?> type) {
		return type.equals(UsernamePasswordAuthenticationToken.class);
	}

}
