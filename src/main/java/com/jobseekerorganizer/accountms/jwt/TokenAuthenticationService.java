package com.jobseekerorganizer.accountms.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.util.Assert;

import com.jobseekerorganizer.accountms.domain.AuthenticationTokenImpl;
import com.jobseekerorganizer.accountms.domain.SessionUser;
import com.jobseekerorganizer.accountms.services.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	@Autowired
	private RedisService service;
	
	private long EXPIRATIONTIME = 1000 * 60 * 60; // 1hr
	private String secret;
	private String tokenPrefix = "Bearer";
	private String headerString = "Authorization";

	public TokenAuthenticationService(String key) {
		this.secret = Sha512DigestUtils.shaHex(key);
	}

	public void addAuthentication(HttpServletResponse response, AuthenticationTokenImpl auth) {
		// Generate JWT token
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", auth.getPrincipal());
		claims.put("hash", auth.getHash());
		String JWT = Jwts.builder()
				.setSubject(auth.getPrincipal().toString())
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(headerString);
		if (token == null) {
			return null;
		}

		// remove "Bearer" text
		token = token.replace(tokenPrefix, "");

		// validating token
		if (token != null && !token.isEmpty()) {
			Claims claims = null;
			try {
				claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			} catch (Exception e) {
				return null;
			}

			// valid token and check if token is actually expired or alive by quering redis
			if (claims != null && claims.containsKey("username")) {
				String username = claims.get("username").toString();
				String hash = claims.get("hash").toString();
				SessionUser user = (SessionUser) service.getValue(String.format("%s:%s", username, hash),
						SessionUser.class);
				if (user != null) {
					AuthenticationTokenImpl auth = new AuthenticationTokenImpl(user.getUsername(),
							Collections.emptyList());
					auth.setDetails(user);
					auth.authenticate();
					return auth;
				} else {
					return new UsernamePasswordAuthenticationToken(null, null);
				}
			}
		}

		return null;

	}

}
