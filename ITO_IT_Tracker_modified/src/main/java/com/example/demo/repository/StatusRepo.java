package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Status;

public interface StatusRepo extends JpaRepository<Status, Integer>
{
	

	@Query(nativeQuery = true,
            value ="SELECT status.status"+" FROM status "+" WHERE status.status_id=:oldId")
public String getOldStatus(@Param("oldId")  int oldId);

	

	@Query(nativeQuery = true,
            value ="SELECT status.status"+" FROM status "+" WHERE status.status_id=:statusId")
	public String getStatus( @Param("statusId") int statusId);
	
}
