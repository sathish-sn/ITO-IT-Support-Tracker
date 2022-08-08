package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Category;

public interface CategoryRepo  extends JpaRepository<Category, Integer>{
	
	@Query(nativeQuery = true,
            value ="SELECT category.category_desc"+" FROM category "+" WHERE category.category_id=:CategoryId")
public String getDescription(@Param("CategoryId")  Integer CategoryId);

}
