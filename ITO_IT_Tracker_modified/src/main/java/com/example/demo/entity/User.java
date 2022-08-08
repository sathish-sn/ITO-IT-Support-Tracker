package com.example.demo.entity;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "user")
@Data
@Getter
@Setter
@NoArgsConstructor
public class User  {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	@Column(name = "user_id")
	private int userId;
	
	private String name;
	
	@Column(name = "email_id", unique = true , columnDefinition = "email is already exits")
	private String emailId;    
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm a",shape = Shape.STRING)
	@Column(name = "create_date_time")
	private LocalDateTime createDateTime =LocalDateTime.now();
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm a",shape = Shape.STRING)
	@Column(name = "last_modified_date_time")
	private LocalDateTime lastModifiedDateTime =LocalDateTime.now();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = {CascadeType.ALL})
	private List<Ticket> ticket;
	
	
	}
