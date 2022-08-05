package com.example.demo.payload.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.payload.CommentDto;
import com.example.demo.payload.UserDto;
import com.example.demo.response.TicketResponse;
import com.example.demo.response.TicketResponseForViewAllTicket;

public interface ItService {

	void deletticket(Integer ticketId);

	String createUser(UserDto userDto);

	List<UserDto> viewAllUser();

	void deleteUser(Integer userId) ;

	String changePriority(Integer ticketId, Integer priorityId, Integer userId);

	UserDto updateuser(UserDto userDto, Integer userId);

	String changeStatus(Integer ticketId, Integer statusId, Integer userId);

	String setAssignee(Integer ticketId, Integer adminId, Integer userId);

	
	String commentOnTicket(CommentDto commentDto, Integer ticketId, Integer adminId);

	UserDto viewUser(Optional<Integer> userId, Optional<String> emailId);

	List<TicketResponseForViewAllTicket> viewTicketList();

	TicketResponse viewTicketByID(Integer ticketId);
}
