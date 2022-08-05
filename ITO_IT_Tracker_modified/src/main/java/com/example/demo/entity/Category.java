package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "category_desc")
	private String categoryDesc;

}
