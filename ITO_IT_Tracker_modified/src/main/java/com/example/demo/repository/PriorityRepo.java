package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Priority;

public interface PriorityRepo extends JpaRepository<Priority, Integer> {

	
	@Query(nativeQuery = true,
            value ="SELECT priority.priority"+" FROM priority "+" WHERE priority.priority_id=:oldId")
public String getOldPriority(@Param("oldId")  int oldId);
	

	@Query(nativeQuery = true,
            value ="SELECT priority.priority"+" FROM priority "+" WHERE priority.priority_id=:priorityId")
public String getPriority(@Param("priorityId")  Integer priorityId);
}
