package com.example.demo.payload;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Component
public class AdminTeamDto implements Serializable {
	
	
	private int adminId;
	
	private String name;
	
	private String emailId;
	

}
