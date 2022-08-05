package com.example.demo.payload;





import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class StatusDto {
	//status (status_id, status)
	
	private int statusId;
	
	private String status;
	

}
