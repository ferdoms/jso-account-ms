package com.jobseekerorganizer.accountms.web.mappper;

import org.mapstruct.Mapper;

import com.jobseekerorganizer.accountms.domain.UserAccount;
import com.jobseekerorganizer.accountms.web.model.UserAccountDto;
import com.jobseekerorganizer.accountms.web.model.UserAccountProfileDto;

@Mapper
public interface UserAccountMapper {
	
	public UserAccountDto userAccountToUserAccountDto(UserAccount userAccount);
	
	public UserAccountProfileDto userAccountToUserAccountProfileDto(UserAccount userAccount);
	
	public UserAccount userAccountDtoToUserAccount(UserAccountDto dto);
	
	
	
	
	
	
}
