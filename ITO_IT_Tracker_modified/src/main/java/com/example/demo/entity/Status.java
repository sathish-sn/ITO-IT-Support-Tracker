package com.example.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "status")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Status {
	@Id
	@Column(name = "status_id")
	private int statusId;
	
	private String status;


	
	}
