package com.jobseekerorganizer.accountms.web.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.jobseekerorganizer.accountms.domain.ProfileImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountProfileDto {

	@NotBlank
	private String fname;

	@NotBlank
	private String lname;

	@Email
	private String email;
}
