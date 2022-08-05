package com.example.demo.payload;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Data
@Getter
@Setter
@NoArgsConstructor

public class TicketDto {
	
	
	private int ticketId;
	private Integer reportedId;
	
	private String subject;
	
	private String description;
	
	private Integer categoryId;
	
	private Integer subCategoryId;
	
	private Integer priorityId;
	
	
	private Integer statusId;
	private Integer assigneeId;
	private LocalDateTime createDateTime = LocalDateTime.now();
	private LocalDateTime lastModifiedDateTime = LocalDateTime.now();
	
}
