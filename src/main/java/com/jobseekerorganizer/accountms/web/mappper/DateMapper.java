package com.jobseekerorganizer.accountms.web.mappper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


// TODO fix data converting
@Component
public class DateMapper {

	public OffsetDateTime asOffsetDateTime(Date dt) {
		if (dt != null) {
			
			return dt.toInstant().atOffset(ZoneOffset.ofHours(Calendar.ZONE_OFFSET));
		}
		return null;
	}
	public Date asDate(OffsetDateTime offsetDateTime) {
		if (offsetDateTime != null) {
			return Date.from(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toInstant());
		}
		return null;
	}

}
