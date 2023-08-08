package com.computing.devopsassessmet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.computing.devopsassessmet.model.QuestionnaireResponse;
import com.computing.devopsassessmet.model.Tutorial;
import com.computing.devopsassessmet.service.AdminService;
import com.computing.devopsassessmet.service.DevOpsAssessmentService;

@Controller
public class DevOpsAssessmentController {

	private DevOpsAssessmentService assessmentService;
	private AdminService adminService;
	private final Environment environment;

	@Autowired
	public DevOpsAssessmentController(DevOpsAssessmentService devOpsAssessmentService, AdminService adminService,
			Environment environment) {
		this.environment = environment;
		this.assessmentService = devOpsAssessmentService;
		this.adminService = adminService;

	}

	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	@PostMapping("/questionnaire")
	public String showQuestionnairePage(@RequestParam String email, Model model) {
		model.addAttribute("email", email);
		return "questionnaire";
	}

	@PostMapping("/submit")
	public ResponseEntity<Map<String, String>> submitAssessment(@RequestBody QuestionnaireResponse response,
			Model model) {

		assessmentService.saveQuestionnaireResponse(response);
		model.addAttribute("response", response);

		Map<String, String> responseData = new HashMap<>();
		responseData.put("message", "Form submitted successfully");
		responseData.put("responseId", response.getId());

		return ResponseEntity.ok(responseData);

	}

	@GetMapping("/results")
	public String showResultsPage(@RequestParam("responseId") String responseId, Model model) {
		// Fetch the QuestionnaireResponse data from the backend (if needed)
		QuestionnaireResponse response = assessmentService.getQuestionnaireResponse(responseId);
		model.addAttribute("response", response);
		if(response != null) {
			
			List<Tutorial> tutorials = assessmentService.getTutorials(response.getGapAreas());
			
			// Add the QuestionnaireResponse to the model
			model.addAttribute("email", response.getEmail());
			model.addAttribute("tutorials", tutorials);
		} else {
			model.addAttribute("error", "no results found");
		}

		return "results"; // Return the view name "results" along with the model attributes.
	}

	@GetMapping("/trackProgress")
	public String track(@RequestParam("email") String emailId, Model model) {

		List<QuestionnaireResponse> responseByEmail = assessmentService.findResponsesByEmail(emailId);
		model.addAttribute("assessmentHistory", responseByEmail);
		return "track";

	}

	@PostMapping("/loadAdmin")
	public String loadAdmin(@RequestParam("adminEmail") String adminEmail,
			@RequestParam("adminPassword") String adminPassword, Model model) {

		String myAdminEmailId = environment.getProperty("admin.email");
		String myAdminPassword = environment.getProperty("admin.password");
		
		if (adminEmail.equals(myAdminEmailId) && adminPassword.equals(myAdminPassword)) {
			model.addAttribute("isAdmin", true);
		} else {
			model.addAttribute("isAdmin", false);
		}
		return "uploadTutorial";
	}

	@PostMapping("uploadTutorial")
	public ResponseEntity<Map<String, String>> uploadTutorial(@RequestParam("file") MultipartFile file) {
		Map<String, String> responseData = new HashMap<>();
		if (file.isEmpty()) {

			responseData.put("message", "File is empty");

		} else {
			
			String uploadTutorialDataResponse = adminService.uploadTutorialData(file);
			
			responseData.put("message", uploadTutorialDataResponse);
		}
		
		

		return ResponseEntity.ok(responseData);

	}

}
