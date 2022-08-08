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
@Table(name = "sub_category")
@Data
@Getter
@Setter
@NoArgsConstructor
public class SubCategory {
	@Id
	@Column(name = "sub_category_id")
	private int subCategoryId;
	
	@Column(name = "category_id")
	private int category_id;
	
	@Column(name = "sub_category_desc")
	private String subCategoryDesc;
	

}
