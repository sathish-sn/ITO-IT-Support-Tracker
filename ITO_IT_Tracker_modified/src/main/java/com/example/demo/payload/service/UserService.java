package com.example.demo.payload.service;

import java.util.List;
import java.util.Map;

import com.example.demo.payload.CommentDto;
import com.example.demo.payload.TicketDto;
import com.example.demo.response.TicketResponseForViewAllTicket;

public interface UserService {
	

	String commentOnTicket(CommentDto commentDto, Integer ticketId, Integer adminId);

	Map<String, String> createTicket(TicketDto teamdto, Integer userId);
	TicketResponseForViewAllTicket viewTicketByTicketId(Integer ticketId);
	List<TicketResponseForViewAllTicket> viewTicketByUserId(Integer userId);

}
