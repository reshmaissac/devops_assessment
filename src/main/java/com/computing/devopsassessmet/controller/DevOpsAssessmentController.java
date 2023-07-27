package com.computing.devopsassessmet.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.computing.devopsassessmet.model.QuestionnaireResponse;
import com.computing.devopsassessmet.service.DevOpsAssessmentService;

@Controller
public class DevOpsAssessmentController {

	private DevOpsAssessmentService assessmentService;

	@Autowired
	public DevOpsAssessmentController(DevOpsAssessmentService devOpsAssessmentService) {
		this.assessmentService = devOpsAssessmentService;

	}

	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	@PostMapping("/questionnaire")
	public String showQuestionnairePage(@RequestParam String email, Model model) {
		model.addAttribute("email", email);
		System.out.println(email);
		return "questionnaire";
	}

	@PostMapping("/submit")
	public ResponseEntity<Map<String, String>> submitAssessment(@RequestBody QuestionnaireResponse response, Model model) {

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

		// Add the QuestionnaireResponse to the model
		model.addAttribute("response", response);

		return "results"; // Return the view name "results" along with the model attributes.
	}

}
