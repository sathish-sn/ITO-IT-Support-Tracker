package com.example.demo.payload;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
public class SubCategoryDto {
	private int subCatId;
	
	private int CategoryId;
	
	private int subCatDesc;
}
