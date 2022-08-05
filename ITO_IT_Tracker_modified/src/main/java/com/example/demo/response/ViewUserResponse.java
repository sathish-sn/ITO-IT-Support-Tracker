package com.example.demo.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewUserResponse {
	
	private Integer userId;
	private String name;
	private String emailId;
	@JsonFormat(pattern = "yyyy-MMM-dd hh:mm a ",shape = Shape.STRING)
	private LocalDateTime createdDateTime = LocalDateTime.now();
	
	@JsonFormat(pattern = "yyyy-MMM-dd hh:mm a ",shape = Shape.STRING)
	private LocalDateTime lastModifiedDateTime = LocalDateTime.now();
	

}
