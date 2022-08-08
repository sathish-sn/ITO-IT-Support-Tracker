package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminTeam;
import com.example.demo.entity.Category;
import com.example.demo.entity.Comments;
import com.example.demo.entity.Priority;
import com.example.demo.entity.Status;
import com.example.demo.entity.SubCategory;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CommentDto;
import com.example.demo.payload.UserDto;
import com.example.demo.payload.service.TeamServices;
import com.example.demo.repository.AdminRepo;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PriorityRepo;
import com.example.demo.repository.StatusRepo;
import com.example.demo.repository.SubCategoryRepo;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.TicketResponse;
import com.example.demo.response.TicketResponseForViewAllTicket;

@Service
public class TeamServiceImpl implements TeamServices {
	org.slf4j.Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	PriorityRepo priorityRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	Category catService;

	@Autowired
	StatusRepo statusRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	SubCategoryRepo subCategoryRepo;

	@Autowired
	Comments comment;

	@Override
	public List<TicketResponseForViewAllTicket> viewTicketList() {

		List<Ticket> viewTicket = (List<Ticket>) this.teamRepo.findAll();

		List<TicketResponseForViewAllTicket> allTicket = new ArrayList<TicketResponseForViewAllTicket>();

		
		
		for (Ticket ticket : viewTicket) {

			TicketResponseForViewAllTicket response = new TicketResponseForViewAllTicket();

			response.setTicketId(ticket.getTicketId());

			response.setCategoryDesc(categoryRepo.getDescription(ticket.getCategoryId()));

			response.setSubCategoryDesc(subCategoryRepo.getDescription(ticket.getSubCategoryId()));

			response.setStatus(statusRepo.getStatus(ticket.getStatusId()));

			response.setSubject(ticket.getSubject());

			response.setDescription(ticket.getDescription());

			if (ticket.getAssignee() == null) {
				
				response.setAssignee("not assigned");

			} else {

				response.setAssignee(ticket.getAssignee().getName());
			}

			if (ticket.getPriorityId() == null) {

				response.setPriority(null);
				
			} else {

				response.setPriority(priorityRepo.getPriority(ticket.getPriorityId()));
			}

			response.setLink("http://localhost:8080/api/team/ticketId/" + ticket.getTicketId());
			
			allTicket.add(response);
		}

		return allTicket;

	}

	@Override
	public TicketResponse viewTicketByID(Integer ticketId) {
		TicketResponse response = new TicketResponse();

		Ticket getTicket = teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));
		
		response.setTicketId(ticketId);

		response.setCategoryDesc(categoryRepo.getDescription(getTicket.getCategoryId()));

		response.setSubCategoryDesc(subCategoryRepo.getDescription(getTicket.getSubCategoryId()));
		
		response.setStatus(statusRepo.getStatus(getTicket.getStatusId()));
		
		response.setSubject(getTicket.getSubject());
		
		response.setDescription(getTicket.getDescription());

		if (getTicket.getPriorityId() == null) {
			
			response.setPriority(null);
			
		} else {
			
			response.setPriority(priorityRepo.getPriority(getTicket.getPriorityId()));
		}

		if (getTicket.getAssigneeId() == null) {
			
			response.setAssignee("not assigned");
			
		} else {
			
			response.setAssignee(adminRepo.getAdmin(getTicket.getAssigneeId()));
		
		}

		response.setLink("http://localhost:8080/api/team/ticketId/" + ticketId);

		List<Comments> comments = commentRepo.findAll();

		response.setComment(returnCommentResponse(comments, ticketId));

		return response;

	}

	@Override
	public String setAssignee(Integer ticketId, Integer adminId, Integer userId) {
		
		Ticket setAssignee = teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		User checkUserId = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("userId", "Id", userId));

		adminRepo.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Assignee", "Id", adminId));

		setAssignee.setUser(checkUserId);

		setAssignee.setAssigneeId(adminId);

		setAssignee.setLastModifiedDateTime(LocalDateTime.now());

		teamRepo.save(setAssignee);

		String msg = "Assignee assinged";

		return msg;
	}

	@Override
	public String changeStatus(Integer ticketId, Integer userId, Integer statusId) {

		Ticket setStatus = teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", ticketId));

		Status getStatus = statusRepo.findById(setStatus.getStatusId())
				.orElseThrow(() -> new ResourceNotFoundException("status", "Id", setStatus.getStatusId()));

		String getOldStatus = getStatus.getStatus();

		if (getOldStatus.equalsIgnoreCase("completed")) {
			
			return "Ticket status has been completed can't be changed";
		}

		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user ", "Id", userId));

		Status checkStatusId = statusRepo.findById(statusId)
				.orElseThrow(() -> new ResourceNotFoundException("status", "Id", statusId));

		setStatus.setLastModifiedDateTime(LocalDateTime.now());

		setStatus.setStatusId(statusId);

		teamRepo.save(setStatus);

		String msg = "status changed from " + getOldStatus + " to " + checkStatusId.getStatus();

		return msg;
	}

	@Override
	public String changePriority(Integer ticketId, Integer priorityId, Integer userId) {

		Ticket setNewPriority = teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", ticketId));

		Priority checkPriorityId = priorityRepo.findById(priorityId)
				.orElseThrow(() -> new ResourceNotFoundException("priority", "Id", priorityId));

		Integer value = setNewPriority.getPriorityId();
		
		if (value == null) {
			
			setNewPriority.setPriorityId(priorityId);

			setNewPriority.setLastModifiedDateTime(LocalDateTime.now());

			this.teamRepo.save(setNewPriority);

			String msg = "priority changed from " + value + " to " + checkPriorityId.getPriority();

			return msg;

		}

		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		Status getStatus = statusRepo.findById(setNewPriority.getStatusId())
				.orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", setNewPriority.getStatusId()));

		String getStatusOfTicket = getStatus.getStatus();

		System.out.println(getStatusOfTicket);

		if (!(getStatusOfTicket.equalsIgnoreCase("open"))) {

			return "Cant change the priority as status is not open";
		}

		Priority check = this.priorityRepo.findById(setNewPriority.getPriorityId())
				.orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", setNewPriority.getPriorityId()));

		String getOldPriority = check.getPriority();

		setNewPriority.setPriorityId(priorityId);

		setNewPriority.setLastModifiedDateTime(LocalDateTime.now());

		this.teamRepo.save(setNewPriority);

		if (getOldPriority.equalsIgnoreCase(checkPriorityId.getPriority())) {
			
			return "Priority is already in " + getOldPriority + " please try to change to another priority";
		}

		String msg = "priority changed from " + getOldPriority + " to " + checkPriorityId.getPriority();

		return msg;
	}

	@Override
	public String commentOnTicket(CommentDto commentDto, Integer ticketId, Integer adminId) {

		this.teamRepo.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", ticketId));

		AdminTeam checkAdminId = this.adminRepo.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("Admin", "Id", adminId));

		Comments comment = this.modelMapper.map(commentDto, Comments.class);

		comment.setMessage(commentDto.getMessage());

		comment.setUserId(checkAdminId.getAdminId());

		comment.setTicketId(ticketId);

		this.commentRepo.save(comment);

		return "Successfully added comment on " + ticketId;
	}

	@Override
	public void deletticket(Integer ticketId) {
		Ticket deleteTicket = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket", "Id", ticketId));

		this.teamRepo.delete(deleteTicket);
	}

	@Override
	public String createUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		String regexp = "(?:[a-z0-9A-Z!#$%&'?^_`{|}~-]+(?:\\.[a-z0-9A-Z!#$%&'*?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9A-Z](?:[a-z0-9A-Z-]*[a-z0-9A-Z])?\\.)+[a-z0-9A-Z](?:[a-z0-9A-Z]*[a-z0-9A-Z])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9A-Z]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

		Pattern pattern = Pattern.compile(regexp);

		Matcher matcher = pattern.matcher(userDto.getEmailId());

		if (!matcher.matches()) {
			
			return "Please enter valid email id";
		}

		int count = 2;
		
		String checkIsUpper = userDto.getEmailId();
		
		char[] charArray = checkIsUpper.toCharArray();
		
		for (int i = 0; i < charArray.length; i++) {

			if (charArray[i] == '@' || charArray[i] == '.') {

				continue;

			} else if (Character.isUpperCase(charArray[i])) {
				
				count++;
			}
		}
		if (count == charArray.length)
			
			if (userDto.getEmailId().equalsIgnoreCase(userDto.getEmailId())) {

				return "Email Id is already Exits";
			}

		user.setCreateDateTime(user.getCreateDateTime());

		user.setLastModifiedDateTime(user.getLastModifiedDateTime());

		user.setEmailId(userDto.getEmailId());

		this.userRepo.save(user);

		return "User with email " + userDto.getEmailId() + " created successfully \n "
				+ "http://localhost:8080/api/team/users/?userId=" + user.getUserId();

	}

	@Override
	public UserDto viewUser(Optional<Integer> userId, Optional<String> emailId) {
		
		Integer getUserId = null;
		
		try {
			
			getUserId = Integer.valueOf(userId.get());
			
		} catch (Exception e) {
			
			getUserId = null;
		}

		String getEmail = null;

		try {
			getEmail = String.valueOf(emailId.get());
			
		} catch (Exception e) {
			
			getEmail = null;
		}

		if (getUserId != null && getEmail == null) {

			int temp = getUserId;
			
			User userById = this.userRepo.findById(getUserId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "Id", temp));
			
			return this.modelMapper.map(userById, UserDto.class);
		}

		Integer UserId = null;

		try {
			UserId = this.userRepo.findByEmail(getEmail);
			
		} catch (Exception e) {
			
			throw new ResourceNotFoundException("Email Id not found  please check the email id");
		}

		if (emailId != null && getUserId == null) {

			UserId = this.userRepo.findByEmail(getEmail);

			int temp = UserId;

			User users = this.userRepo.findById(UserId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "Id", temp));

			return this.modelMapper.map(users, UserDto.class);
		}

		if (getUserId != null && emailId != null && getUserId != UserId) {

			throw new ResourceNotFoundException("User Id and Email Id not matched");
		}

		if (getUserId == null && emailId == null) {

			throw new ResourceNotFoundException("Please enter User Id or Email");
		}

		if (getUserId != null && emailId != null) {
			
			int temp = getUserId;

			User userById = this.userRepo.findById(getUserId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "Id", temp));

			return this.modelMapper.map(userById, UserDto.class);
		}

		return null;
	}

	@Override
	public List<UserDto> viewAllUser() {
		List<User> users = userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());

		return userDtos;

	}

	@Override
	public UserDto updateuser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userDto.getName());

		user.setLastModifiedDateTime(LocalDateTime.now());

		User updatedUser = userRepo.save(user);

		UserDto userDto1 = modelMapper.map(updatedUser, UserDto.class);

		return userDto1;
	}

	@Override
	public void deleteUser(Integer userId) {
		List<Ticket> ticket = teamRepo.findAll();
		for (Ticket findTicket : ticket) {
			
			if (findTicket.getReportedId() == userId) {
				
				throw new ResourceNotFoundException("Cant Delete the user as Ticket is pending");
			
			}
		}
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		this.userRepo.delete(user);
		
	}

	public List<CommentResponse> returnCommentResponse(List<Comments> comments, int ticketId) {
		List<CommentResponse> addComment = new ArrayList<>();

		for (Comments comment : comments) {

			if (comment.getTicketId() == ticketId) {

				CommentResponse commentResponse = new CommentResponse();

				commentResponse.setMessage(comment.getMessage());

				commentResponse.setUserId(comment.getUserId());

				System.out.println(commentResponse);

				addComment.add(commentResponse);

				System.out.println(addComment);

			}
		}
		return addComment;
	}

}
