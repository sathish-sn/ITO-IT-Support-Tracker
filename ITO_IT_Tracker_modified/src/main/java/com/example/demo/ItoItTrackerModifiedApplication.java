package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.entity.AdminTeam;
import com.example.demo.entity.Category;
import com.example.demo.entity.Comments;
import com.example.demo.entity.Ticket;
import com.example.demo.response.TicketResponse;

@SpringBootApplication
public class ItoItTrackerModifiedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItoItTrackerModifiedApplication.class, args);
	}


	@Bean
	public ModelMapper modelmapper() {
		return new  ModelMapper();
	}
	
	@Bean
	public Category cat() {
		return new Category();
	}
	
	@Bean
	public Ticket ticket() {
		return new Ticket();
	}
	@Bean
	public AdminTeam adminTeam() {
		return new AdminTeam();
	}
	@Bean
	public Comments comment() {
		return new Comments();
	}
	@Bean
	public TicketResponse response() {
		return new TicketResponse();
	}
}
