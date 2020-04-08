package com.jobseekerorganizer.accountms.web.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.*;

import com.jobseekerorganizer.accountms.domain.ProfileImage;

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
	
	private ProfileImage profileImage;
	
	@Null
	private OffsetDateTime createdAt;
	@Null
	private OffsetDateTime lastModifiedDate;
		


}
