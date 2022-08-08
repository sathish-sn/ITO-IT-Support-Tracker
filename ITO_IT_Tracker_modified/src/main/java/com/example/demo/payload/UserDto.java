package com.example.demo.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private int userId;
	
	private String name;
	
	private String emailId;                  
	
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a ",shape =JsonFormat.Shape.STRING)
	private LocalDateTime create_date_time =LocalDateTime.now();
	
	@JsonFormat(pattern = "dd-MMM-yyyy hh:mm a ",shape =JsonFormat.Shape.STRING)
	private LocalDateTime last_modified_date_time =LocalDateTime.now();
}
