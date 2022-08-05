package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.payload.service.StatusService;
import com.example.demo.repository.StatusRepo;

public class StatuServiceImpl implements StatusService {

	@Autowired
	StatusRepo statusRepo;
	@Override
	public String getType(int statusId) {
		
		
	
		String message = statusRepo.findById(statusId).get().getStatus();
		return message;
	}

}
