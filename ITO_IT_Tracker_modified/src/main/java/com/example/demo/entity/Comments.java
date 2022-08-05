package com.example.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id",insertable = false , updatable = false)
	private int commentId;
	
	@Column(name = "ticket_id")
	private int ticketId;
	
	@Column(name = "user_id")
	private int userId;
	
	private String message;

	
}
