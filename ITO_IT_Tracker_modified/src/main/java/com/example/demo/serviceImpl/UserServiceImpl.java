package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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
import com.example.demo.payload.TicketDto;
import com.example.demo.payload.service.UserService;
import com.example.demo.repository.AdminRepo;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PriorityRepo;
import com.example.demo.repository.StatusRepo;
import com.example.demo.repository.SubCategoryRepo;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.TicketResponseForViewAllTicket;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TeamRepository teamRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private PriorityRepo priorityRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	Ticket ticket;

	@Override
	public String commentOnTicket(CommentDto commentDto, Integer ticketId, Integer userId) {

		Ticket ticket = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticketId", "Id", ticketId));

		 this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("userId", "Id", userId));

		Comments comment = this.modelMapper.map(commentDto, Comments.class);

		comment.setMessage(commentDto.getMessage());

		comment.setTicketId(ticket.getTicketId());

		comment.setUserId(userId);

		this.commentRepo.save(comment);

		return "Successfully added comment on " + ticketId;
	}

	@Override
	public Map<String, String> createTicket(TicketDto teamdto, Integer userId) {

		Map<String, String> map = new HashMap<String, String>();

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Ticket newTicket = this.modelMapper.map(teamdto, Ticket.class);

		System.out.println(teamdto.getCategoryId());

		Integer checkNull = teamdto.getCategoryId();

		if (checkNull == null) {
			map.put("Message", "Enter valid Category");
			return map;
		}

		this.categoryRepo.findById(teamdto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", teamdto.getCategoryId()));

		newTicket.setCategoryId(teamdto.getCategoryId());

		SubCategory matchCategory = this.subCategoryRepo.findById(teamdto.getSubCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Subcategory", "Id", teamdto.getSubCategoryId()));

		subCategoryRepo.findById(teamdto.getSubCategoryId());

		newTicket.setSubCategoryId(teamdto.getSubCategoryId());

		if (teamdto.getPriorityId() == null) {
			newTicket.setPriorityId(teamdto.getPriorityId());
		} else {

			this.priorityRepo.findById(teamdto.getPriorityId())
					.orElseThrow(() -> new ResourceNotFoundException("Priority", "Id", teamdto.getPriorityId()));

			newTicket.setPriorityId(teamdto.getPriorityId());
		}
		if (matchCategory.getCategory_id() != teamdto.getCategoryId()) {
			map.put("Message", "subcategory id not matched with category! enter valid subcategory id");
			return map;
		}

		newTicket.setSubject(newTicket.getSubject());

		newTicket.setDescription(newTicket.getDescription());

		newTicket.setCreateDateTime(LocalDateTime.now());

		newTicket.setLastModifiedDateTime(LocalDateTime.now());

		newTicket.setReportedId(userId);

		newTicket.setStatusId(1);

		Ticket saveTicket = this.teamRepo.save(newTicket);

		int createdTicket = saveTicket.getTicketId();

		System.out.println(createdTicket);

		map.put("Message:", "Ticket " + createdTicket + " Created Succussfully");

		map.put("Link:", "http://localhost:8080/api/users/user/ticket/?ticketId=" + createdTicket);

		return map;
	}

	@Override
	public List<TicketResponseForViewAllTicket> viewTicketByUserId(Integer userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId", "Id", userId));
		TicketResponseForViewAllTicket response = new TicketResponseForViewAllTicket();
		List<Ticket> tickets = this.teamRepo.findAll();
		List<TicketResponseForViewAllTicket> setTicket = new ArrayList<TicketResponseForViewAllTicket>();

		for (Ticket getTicket : tickets) {

			if (getTicket.getReportedId() == userId) {

				response.setTicketId(getTicket.getTicketId());

				Category category = this.categoryRepo.getById(getTicket.getCategoryId());
				response.setCategoryDesc(category.getCategoryDesc());

				SubCategory subCategory = this.subCategoryRepo.getById(getTicket.getSubCategoryId());
				response.setSubCategoryDesc(subCategory.getSubCategoryDesc());

				Status getStatus = this.statusRepo.getById(getTicket.getStatusId());
				response.setStatus(getStatus.getStatus());

				response.setSubject(getTicket.getSubject());
				response.setDescription(getTicket.getDescription());
				
				if(getTicket.getPriorityId() == null) {
					response.setPriority(null);
				}else {
					Priority getPriority = this.priorityRepo.getById(getTicket.getPriorityId());
					response.setPriority(getPriority.getPriority());
				}
				if (getTicket.getAssigneeId() == null) {
					response.setAssignee(null);
				} else {
					AdminTeam assignee = this.adminRepo.getById(getTicket.getAssigneeId());
					response.setAssignee(assignee.getName());
				}
				response.setLink("http://localhost:8080/api/team/ticketId/" + getTicket.getTicketId());

				setTicket.add(response);

			}
		}

		return setTicket;

	}

	@Override
	public TicketResponseForViewAllTicket viewTicketByTicketId(Integer ticketId) {
		TicketResponseForViewAllTicket response = new TicketResponseForViewAllTicket();

		Ticket getTicket = this.teamRepo.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("ticket", "Id", ticketId));
		response.setTicketId(ticketId);

		Category category = this.categoryRepo.getById(getTicket.getCategoryId());

		response.setCategoryDesc(category.getCategoryDesc());

		SubCategory subCategory = this.subCategoryRepo.getById(getTicket.getSubCategoryId());

		response.setSubCategoryDesc(subCategory.getSubCategoryDesc());

		Status getStatus = this.statusRepo.getById(getTicket.getStatusId());
		response.setStatus(getStatus.getStatus());

		response.setSubject(getTicket.getSubject());
		response.setDescription(getTicket.getDescription());

		Priority getPriority = this.priorityRepo.getById(getTicket.getPriorityId());
		response.setPriority(getPriority.getPriority());

		if (getTicket.getAssigneeId() == null) {
			response.setAssignee(null);
		} else {
			AdminTeam assignee = this.adminRepo.getById(getTicket.getAssigneeId());
			response.setAssignee(assignee.getName());
		}

		response.setLink("http://localhost:8080/api/team/ticketId/" + getTicket.getTicketId());

		return response;

	}

}
