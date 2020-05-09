package com.jobseekerorganizer.accountms.web.model;

import java.time.OffsetDateTime;
import java.util.Date;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDto {
	
	@Null
	private String id;
	
	@NotBlank
	private String fname;
	
	@NotBlank
	private String lname;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private String profileImage;
	
	@Null
	private Date createdAt;
	@Null
	private Date lastModifiedDate;
		


}
