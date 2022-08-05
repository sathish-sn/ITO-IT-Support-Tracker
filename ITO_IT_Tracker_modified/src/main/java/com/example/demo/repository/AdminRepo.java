package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AdminTeam;

public interface AdminRepo extends JpaRepository<AdminTeam, Integer> {

}
