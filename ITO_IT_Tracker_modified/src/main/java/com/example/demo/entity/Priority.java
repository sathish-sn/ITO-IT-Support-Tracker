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
@Table(name = "priority")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Priority  {

	@Id
	@Column(name = "priority_id")
	private int priorityId;

	private String priority;


	
}
