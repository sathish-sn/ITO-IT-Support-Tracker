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
import com.example.demo.payload.service.ItService;
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
public class TeamServiceImpl implements ItService {
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

		for (Ticket getAll : viewTicket) {
			TicketResponseForViewAllTicket response = new TicketResponseForViewAllTicket();

			response.setTicketId(getAll.getTicketId());
			System.out.println();

			Category category = this.categoryRepo.getById(getAll.getCategoryId());
			response.setCategoryDesc(category.getCategoryDesc());

			SubCategory subCategory = this.subCategoryRepo.getById(getAll.getSubCategoryId());
			response.setSubCategoryDesc(subCategory.getSubCategoryDesc());

			Status getStatus = this.statusRepo.getById(getAll.getStatusId());
			response.setStatus(getStatus.getStatus());

			response.setSubject(getAll.getSubject());
			response.setDescription(getAll.getDescription());

			if (getAll.getPriorityId() == null) {
				response.setPriority(null);
			} else {
				Priority getPriority = this.priorityRepo.getById(getAll.getPriorityId());
				response.setPriority(getPriority.getPriority());
			}

			response.setLink("http://localhost:8080/api/team/ticketId/" + getAll.getTicketId());
			allTicket.add(response);
		}

		return allTicket;

	}

	@Override
	public TicketResponse viewTicketByID(Integer ticketId) {
		TicketResponse response = new TicketResponse();
		
		Ticket getTicket = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));
		response.setTicketId(ticketId);

		Category category = this.categoryRepo.findById(getTicket.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", getTicket.getCategoryId()));
		response.setCategoryDesc(category.getCategoryDesc());
		
		SubCategory subCategory = this.subCategoryRepo.findById(getTicket.getSubCategoryId()).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", getTicket.getSubCategoryId()));
		response.setSubCategoryDesc(subCategory.getSubCategoryDesc());
		
		Status getStatus = this.statusRepo.findById(getTicket.getStatusId())
				.orElseThrow(() -> new ResourceNotFoundException("userId", "Id", getTicket.getStatusId()));
		response.setStatus(getStatus.getStatus());
		
		response.setSubject(getTicket.getSubject());
		response.setDescription(getTicket.getDescription());
		
		if(getTicket.getPriorityId() == null) {
			response.setPriority(null);
		}else {
			Priority getPriority = this.priorityRepo.findById(getTicket.getPriorityId()).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", getTicket.getSubCategoryId()));
			response.setPriority(getPriority.getPriority());
		}
		
		
		
		if (getTicket.getAssigneeId() == null) {
			response.setAssignee(null);
		} else {
			AdminTeam assignee = this.adminRepo.findById(getTicket.getAssigneeId()).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", getTicket.getSubCategoryId()));
			response.setAssignee(assignee.getName());
		}
		
		response.setLink("http://localhost:8080/api/team/ticketId/" + ticketId);
		
		List<Comments> comments = this.commentRepo.findAll();
		
		response.setComment(this.returnCommentResponse(comments, ticketId));
		
		return response;

	}

	@Override
	public String setAssignee(Integer ticketId, Integer adminId, Integer userId) {
		Ticket setAssignee = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		User checkUserId = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("userId", "Id", userId));

		this.adminRepo.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", adminId));

		setAssignee.setUser(checkUserId);

		setAssignee.setAssigneeId(adminId);

		setAssignee.setLastModifiedDateTime(LocalDateTime.now());

		this.teamRepo.save(setAssignee);

		String msg = "Assignee assinged";

		return msg;
	}

	@Override
	public String changeStatus(Integer ticketId, Integer userId, Integer statusId) {

		Ticket setStatus = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		Status getStatus = this.statusRepo.getById(setStatus.getStatusId());

		String getOldStatus = getStatus.getStatus();

		if (getOldStatus.equalsIgnoreCase("completed")) {
			return "Ticket status has been completed can't be changed";
		}

		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user ", "Id", userId));

		Status checkStatusId = this.statusRepo.findById(statusId)
				.orElseThrow(() -> new ResourceNotFoundException("status", "Id", statusId));

		setStatus.setLastModifiedDateTime(LocalDateTime.now());

		setStatus.setStatusId(statusId);

		this.teamRepo.save(setStatus);

		String msg = "status changed from " + getOldStatus + " to " + checkStatusId.getStatus();

		return msg;
	}

	@Override
	public String changePriority(Integer ticketId, Integer priorityId, Integer userId) {

		Ticket setNewPriority = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId", "Id", userId));

		Status getStatus = this.statusRepo.getById(setNewPriority.getStatusId());

		String getStatusOfTicket = getStatus.getStatus();

		System.out.println(getStatusOfTicket);

		System.out.println();

		if (!(getStatusOfTicket.equalsIgnoreCase("open"))) {

			return "Cant change the priority";
		}

		Priority checkPriorityId = this.priorityRepo.findById(priorityId)
				.orElseThrow(() -> new ResourceNotFoundException("priorityId", "Id", priorityId));

		Priority check = this.priorityRepo.getById(setNewPriority.getPriorityId());

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

		this.teamRepo.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		AdminTeam checkAdminId = this.adminRepo.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("AdminId", "Id", adminId));

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

		List<User> findEmail = this.userRepo.findAll();

		String regexp = "^(.+)@(\\\\S+)$";

		Pattern pattern = Pattern.compile(regexp);

		Matcher matcher = pattern.matcher(userDto.getEmailId());

		if (!matcher.matches()) {
			return "Please enter valid email id";
		} else {

			for (User getEmail : findEmail) {
				if (getEmail.getEmailId() == null) {

					continue;
				}

				if (getEmail.getEmailId().equalsIgnoreCase(userDto.getEmailId())) {

					return "Email Id is already Exits";
				}
			}

			user.setCreateDateTime(user.getCreateDateTime());

			user.setLastModifiedDateTime(user.getLastModifiedDateTime());

			user.setEmailId(userDto.getEmailId());
		}
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

			System.out.println("finding by email id");

			int temp = getUserId;
			User userById = this.userRepo.findById(getUserId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "Id", temp));
			return this.modelMapper.map(userById, UserDto.class);
		}
		Integer UserId = this.userRepo.findByEmail(getEmail);

		if (emailId != null && getUserId == null) {
			System.out.println("finding by user Id");

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

		return null;// map;
	}

	@Override
	public List<UserDto> viewAllUser() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;

	}

	@Override
	public UserDto updateuser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());

		user.setLastModifiedDateTime(LocalDateTime.now());

		User updatedUser = this.userRepo.save(user);

		UserDto userDto1 = this.modelMapper.map(updatedUser, UserDto.class);

		return userDto1;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
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
