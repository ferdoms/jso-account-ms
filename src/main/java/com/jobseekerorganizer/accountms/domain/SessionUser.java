package com.jobseekerorganizer.accountms.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import lombok.Data;

@Data
public class SessionUser {
	
	private String username;
	private String password;
	private Date createdAt;
	
	public boolean hasExpired() {
		if(createdAt == null) {
			return true;
		}
		LocalDateTime localDateTime = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusHours(1);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).before(new Date());
	}
}
