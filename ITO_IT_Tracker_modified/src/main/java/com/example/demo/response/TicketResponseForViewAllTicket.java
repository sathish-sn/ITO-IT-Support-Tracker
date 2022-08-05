package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseForViewAllTicket {
	
	private int ticketId;
	 private String categoryDesc;
	 private String subCategoryDesc;
	 private String priority;
	 private String status;
	 private String subject;
	 private String description;
	 private String link;
	 private String assignee;

}
