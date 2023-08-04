package com.computing.devopsassessmet.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.computing.devopsassessmet.model.Tutorial;
import com.computing.devopsassessmet.repository.TutorialRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

@Service
public class AdminService {

	private TutorialRepository tutorialRepository;
	private final ObjectMapper objectMapper;
	MongoCollection<Document> collection;  

	@Autowired
	public AdminService(TutorialRepository tutorialRepository, ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.tutorialRepository = tutorialRepository;
	}

	public String uploadTutorialData(MultipartFile file) {
		try {

			// Read the JSON data from the uploaded file
			 String jsonDataString = new String(file.getBytes(), StandardCharsets.UTF_8);

			// Deserialize the JSON data into the entity class
			List<Tutorial> tutorials = objectMapper.readValue(jsonDataString, new TypeReference<List<Tutorial>>() {
			});

			// Save the tutorials to the MongoDB collection using Spring Data MongoDB
			tutorialRepository.deleteAll();
			tutorialRepository.saveAll(tutorials);
			
			return "Successfully uploaded";
		} catch (IOException e) {
			System.out.println(e.getMessage()); 
			// Handle IO error	
			return "Upload failed";
		}

	}
	

}
