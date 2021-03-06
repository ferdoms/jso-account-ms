package com.jobseekerorganizer.accountms.jwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobseekerorganizer.accountms.domain.AuthenticationTokenImpl;
import com.jobseekerorganizer.accountms.domain.SessionUser;
import com.jobseekerorganizer.accountms.jwt.TokenAuthenticationService;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	
	private TokenAuthenticationService service;


	public JWTLoginFilter(String url, AuthenticationManager authenticationManager,TokenAuthenticationService service) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authenticationManager);
		this.service = service;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		SessionUser credentials = new ObjectMapper().readValue(request.getInputStream(), SessionUser.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
				credentials.getPassword());
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		AuthenticationTokenImpl auth = (AuthenticationTokenImpl) authentication;
		service.addAuthentication(response, auth);
		
	}
}
