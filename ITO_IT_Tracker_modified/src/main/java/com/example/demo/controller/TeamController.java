package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CommentDto;
import com.example.demo.payload.TicketDto;
import com.example.demo.payload.UserDto;
import com.example.demo.payload.service.TeamServices;
import com.example.demo.response.TicketResponse;
import com.example.demo.response.TicketResponseForViewAllTicket;

@RestController
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	private TeamServices service;

//------------------------------------------------------------------------------------------------------------------------------------------------------------	
	@PostMapping("/")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
		String createUserDto = this.service.createUser(userDto);

		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@GetMapping("/users")
	public ResponseEntity<UserDto> viewUser(@RequestParam(value = "userId") Optional<Integer> userId,
			@RequestParam(value = "emailId") Optional<String> emailId) {

		return ResponseEntity.ok(this.service.viewUser(userId, emailId));
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------

	@PutMapping("/team/")
	public ResponseEntity<ApiResponse> changePriority(@RequestBody TicketDto teamdto,
			@RequestParam(value = "ticketId") Integer ticketId, @RequestParam(value = "priorityId") Integer priorityId,
			@RequestParam(value = "userId") Integer userId) {

		String message = service.changePriority(ticketId, priorityId, userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(message), HttpStatus.OK);

	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@GetMapping("/getAllTicket")
	public List<TicketResponseForViewAllTicket> viewTicketList() {
		
		return (this.service.viewTicketList());
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@GetMapping("/ticketId/{ticketId}")
	public ResponseEntity<TicketResponse> viewTicketById(@PathVariable Integer ticketId) {

		return ResponseEntity.ok(this.service.viewTicketByID(ticketId));
	
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@GetMapping("/viewAll")
	public ResponseEntity<?> viewAllUsers() {

		return ResponseEntity.ok(service.viewAllUser());

	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updatedUser = this.service.updateuser(userDto, userId);

		return ResponseEntity.ok(updatedUser);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	
	@DeleteMapping("/delete/user/")
	public ResponseEntity<ApiResponse> deleteUser(@RequestParam(value = "userId") Integer userId) {
		
		this.service.deleteUser(userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("user id deleted"), HttpStatus.OK);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@DeleteMapping("/delete/")
	public ResponseEntity<ApiResponse> deletticket(@RequestParam(value = "ticketId") Integer ticketId) {
		this.service.deletticket(ticketId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Ticket deleted"), HttpStatus.OK);
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@PutMapping("/changeStatus/")
	public ResponseEntity<?> changeStatus(@RequestParam(value = "ticketId") Integer ticketId,
			@RequestParam(value = "statusId") Integer statusId, @RequestParam(value = "userId") Integer userId) {

		String message = service.changeStatus(ticketId, userId, statusId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(message), HttpStatus.OK);

	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------	

	@PutMapping("/setAssignee/")
	public ResponseEntity<?> setAssignee(@RequestParam(value = "ticketId") Integer ticketId,
			@RequestParam(value = "adminId") Integer adminId, @RequestParam(value = "userId") Integer userId) {
		
		String message = service.setAssignee(ticketId, adminId, userId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message), HttpStatus.OK);
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------	


	@PutMapping("/comment/")
	public ResponseEntity<String> commentOnTicket(@RequestBody CommentDto commentDto,
			@RequestParam(value = "ticketId") Integer ticketId, @RequestParam(value = "adminId") Integer adminId) {

		String updatedUser = this.service.commentOnTicket(commentDto, ticketId, adminId);

		return ResponseEntity.ok(updatedUser);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------	

}
