package com.example.demo.payload;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class CategoryDto  {
	
	private int categoryId;
	
	private String categoryDesc;
	
	

}
