package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.CommentDto;
import com.example.demo.payload.TicketDto;
import com.example.demo.payload.service.UserService;
import com.example.demo.response.TicketResponseForViewAllTicket;

@RestController
@RequestMapping("/api/users")
public class UserControl {
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<Map<String, String>> createTicket(@Valid @RequestBody TicketDto teamdto,
			@RequestParam(value = "userId") Integer userId) {
		
		Map<String, String> createUserDto = this.userService.createTicket(teamdto, userId);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	@PutMapping("/comment/")
	public ResponseEntity<String> commentOnTicket(@RequestBody CommentDto commentDto,
			@RequestParam(value = "ticketId") Integer ticketId, @RequestParam(value = "userId") Integer userId) {
		
		String updatedUser = this.userService.commentOnTicket(commentDto, ticketId, userId);
		
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/user/ticket/")

	public ResponseEntity<TicketResponseForViewAllTicket> viewTicketByTicketId(
			@RequestParam(value = "ticketId") Integer ticketId) {

		return ResponseEntity.ok(this.userService.viewTicketByTicketId(ticketId));
	}

	@GetMapping("/user/")
	public ResponseEntity<List<TicketResponseForViewAllTicket>> viewTicketByUserId(
			@RequestParam(value = "userId") Integer userId) {

		return ResponseEntity.ok(this.userService.viewTicketByUserId(userId));
	}
}
