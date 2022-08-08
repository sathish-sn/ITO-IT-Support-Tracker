package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.AdminTeam;

public interface AdminRepo extends JpaRepository<AdminTeam, Integer> {

	@Query(nativeQuery = true,
            value ="SELECT admin_team.name"+" FROM admin_team "+" WHERE admin_team.admin_id=:adminId")
public String getAdmin(@Param("adminId")  Integer adminId);
	
}
