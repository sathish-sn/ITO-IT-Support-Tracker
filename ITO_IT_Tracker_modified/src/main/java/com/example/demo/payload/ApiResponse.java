package com.example.demo.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApiResponse {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResponse() {

	}

	public ApiResponse(String message) {
		super();
		this.message = message;

	}

}
