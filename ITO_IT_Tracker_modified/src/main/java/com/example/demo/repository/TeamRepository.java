package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Ticket;

public interface TeamRepository extends JpaRepository<Ticket, Integer>, CrudRepository<Ticket, Integer> {

}
