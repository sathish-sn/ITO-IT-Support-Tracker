package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Ticket;

public interface TeamRepository extends JpaRepository<Ticket, Integer>, CrudRepository<Ticket, Integer> {

	
@Query(value = "select sub_category.sub_category_desc  from sub_category  where sub_category_id = "+
"(select sub_category_id from ticket where ticket_id= :ticketId) "+
" union "+
"select  category_desc from category where category_id = (select category_id from ticket where ticket_id=:ticketId) "
+" union "+
"select  status from status where status_id = (select status_id from ticket where ticket_id= :ticketId)"+ 
" union "+
"select  priority from priority where priority_id = (select priority_id from ticket where ticket_id=:ticketId)"+
" union "+
"select  name  from admin_team where admin_id = (select assignee_id from ticket where ticket_id= :ticketId)", nativeQuery = true)
List<String> getTicketDetails(@Param("ticketId") Integer ticketId);
	
}
