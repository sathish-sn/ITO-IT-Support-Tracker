package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.SubCategory;


public interface SubCategoryRepo extends JpaRepository<SubCategory, Integer>{

	@Query(nativeQuery = true,
            value ="SELECT sub_category.sub_category_desc"+" FROM sub_category "+" WHERE sub_category.sub_category_id=:sub_cat_id")
public String getDescription(@Param("sub_cat_id")  Integer sub_cat_id);
	
}
