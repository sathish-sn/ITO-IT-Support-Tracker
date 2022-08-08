package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(nativeQuery = true, value = "SELECT user.user_id" + " FROM user " + " WHERE user.email_id=:getUserId")
	public int findByEmail(@Param("getUserId") String getUserId);

	@Query(value = "select * from User", nativeQuery = true)
	public List<User> getAllUsers();

	@Query(value = "select ticket_id from  ticket where reported_id = :UserId", nativeQuery = true)
	public Integer getTicketId(@Param("UserId") Integer UserId);

	@Query(value = "select  reported_id from  ticket where ticket_id = :ticketId", nativeQuery = true)
	public List<Integer> getUserId(@Param("ticketId") int ticketId);

}
