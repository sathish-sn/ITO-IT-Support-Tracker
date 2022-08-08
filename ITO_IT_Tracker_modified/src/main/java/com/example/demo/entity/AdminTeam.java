package com.example.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin_team")
@Data
@Getter
@Setter
@NoArgsConstructor
public class AdminTeam  {
	
	@Id
	@Column(name = "admin_id")
	private int adminId;
	
	private String name;
	
	@Column(name = "email_id")
	private String emailId;
	


}
