package com.example.demo.payload;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class CommentDto  {

	private int commentId;
	
	private int ticketId;
	
	private int userId;
	
	private String message;
}
