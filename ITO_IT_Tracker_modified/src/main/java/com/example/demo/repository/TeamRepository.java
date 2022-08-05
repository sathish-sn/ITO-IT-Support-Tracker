package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Ticket;

public interface TeamRepository extends JpaRepository<Ticket, Integer> , CrudRepository<Ticket, Integer>{
	
	
	@Query(value="select * from ticket where ticket.ticket_id =:ticketId",nativeQuery = true )
	public Ticket getAllTicket(@Param("ticketId") Integer ticketId);
	
	


}
