package com.sai.rest.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sai.rest.api.entity.Student;
import com.sai.rest.api.repository.searchRespository;

@Service
public class searchService {
	
	@Autowired
	private searchRespository searchRepo;
	
	public  List<Student> searchName(String keyword){
		return searchRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
	}



}
