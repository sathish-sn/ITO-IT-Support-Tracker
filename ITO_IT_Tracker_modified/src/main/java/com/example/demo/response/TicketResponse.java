package com.example.demo.response;

import java.util.List;



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
public class TicketResponse {

	 private int ticketId;
	 private String categoryDesc;
	 private String subCategoryDesc;
	 private String priority;
	 private String status;
	 private String subject;
	 private String description;
	 private String link;
	 private String assignee;
	 private List<CommentResponse> comment;
	

}
