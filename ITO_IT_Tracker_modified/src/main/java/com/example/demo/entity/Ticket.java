package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor

public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ticket_id")
	private Integer ticketId;

	@Column(name = "reported_id")
	private Integer reportedId;

	private String subject;

	private String description;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "sub_category_id")
	private Integer subCategoryId;

	@Column(name = "priority_id")
	private Integer priorityId;

	@Column(name = "status_id")
	private Integer statusId;

	@Column(name = "assignee_id")
	private Integer assigneeId;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm a\"") 
	@Column(name = "create_date_time")
	private LocalDateTime createDateTime = LocalDateTime.now();

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm  a")
	@Column(name = "last_modified_date_time")
	private LocalDateTime lastModifiedDateTime = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
	private AdminTeam assignee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reported_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ticket_id ", insertable = false, updatable = false)
	
	private List<Comments> commentList;
}
