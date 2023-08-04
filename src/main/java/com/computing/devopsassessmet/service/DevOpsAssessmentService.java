package com.computing.devopsassessmet.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.computing.devopsassessmet.model.GapArea;
import com.computing.devopsassessmet.model.MaturityLevel;
import com.computing.devopsassessmet.model.Question;
import com.computing.devopsassessmet.model.QuestionnaireResponse;
import com.computing.devopsassessmet.model.Section;
import com.computing.devopsassessmet.model.Tutorial;
import com.computing.devopsassessmet.repository.ResponseRepository;
import com.computing.devopsassessmet.repository.TutorialRepository;

@Service
public class DevOpsAssessmentService {

	private ResponseRepository responseRepository;
	private TutorialRepository tutorialRepository;

	@Autowired
	public DevOpsAssessmentService(ResponseRepository responseRepository, TutorialRepository tutorialRepository) {
		this.responseRepository = responseRepository;
		this.tutorialRepository = tutorialRepository;
	}

	public List<QuestionnaireResponse> findResponsesByEmail(String email) {
		return responseRepository.findByEmail(email);
	}

	public void saveQuestionnaireResponse(QuestionnaireResponse response) {
		// Calculate and set section marks in percentage
		setSectionScore(response);

		// Calculate and set total marks and total percentage
		double totalScore = response.getSections().stream().flatMap(section -> section.getQuestions().stream())
				.mapToDouble(Question::getAnswerScore).sum();
		double fullScore = response.getSections().stream().flatMap(section -> section.getQuestions().stream()).count()
				* 5.0;

		double maturityPercent = (totalScore / fullScore) * 100;

		// Find MaturityLevel
		Optional<MaturityLevel> maturityLevel = findMaturityLevel(maturityPercent);

		// Find Gap Areas
		List<String> gapAreas = findGapAreas(response);

		response.setGapAreas(gapAreas);
		response.setTotalScore(totalScore);
		response.setMaturityPercent(maturityPercent);
		response.setMaturityLevel(maturityLevel.orElse(MaturityLevel.UNKNOWN));

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
		response.setTimestamp(timeStamp);

		responseRepository.save(response);

	}

	public QuestionnaireResponse getQuestionnaireResponse(String responseId) {
		return responseRepository.findById(responseId).orElse(null);

	}

	public List<Tutorial> getTutorials(List<String> gapAreas) {
 
		List<Tutorial> tutorials = tutorialRepository.findByAreaIn(gapAreas).orElse(null);

		Tutorial cicdPipelineTutorial = tutorials.stream()
				.filter(tutorial -> tutorial.getArea().equals(GapArea.CICD_PIPELINE.getTutorialArea())).findFirst()
				.get();

		// if gap area is CICD , add tutorials for CICD pipeline
		String tutorialArea = GapArea.CICD_AUTOMATION.getTutorialArea(); 
		System.out.println(tutorialArea);
		Optional<Tutorial> optionalCICDTutorial = tutorials.stream()
				.filter(tutorial -> tutorial.getArea().equals(tutorialArea)).findFirst(); 
		if(optionalCICDTutorial.isPresent()) {
			
			optionalCICDTutorial.get().setTutorialItems(cicdPipelineTutorial.getTutorialItems()); 
		}

		// if gap area is MTTR or Deployment Frequency, add tutorials for Test
		// automation, CICD pipeline, Monitoring
		List<Tutorial> mttrOrDeployFreqTutorials = tutorials.stream()
				.filter(tutorial -> tutorial.getArea()
						.equals(GapArea.MTTR.getTutorialArea())
								|| tutorial.getArea().equals(GapArea.DEPLOYMENT_FREQUENCY.getTutorialArea()))
				.collect(Collectors.toList());
			
		mttrOrDeployFreqTutorials.forEach(tutorial -> tutorial.setTutorialItems(cicdPipelineTutorial.getTutorialItems()));

		return tutorials;

	}

	private void setSectionScore(QuestionnaireResponse response) {
		response.getSections().stream().forEach(section -> {
			double sectionTotal = section.getQuestions().stream().mapToDouble(question -> question.getAnswerScore())
					.sum();
			double sectionPercentage = (sectionTotal / (section.getQuestions().size() * 5)) * 100;
			section.setSectionScorePercent(sectionPercentage);
		});
	}

	private List<String> findGapAreas(QuestionnaireResponse response) {
		List<Section> gapSections = response.getSections().stream()
				.sorted(Comparator.comparingDouble(Section::getSectionScorePercent)).limit(2)
				.collect(Collectors.toList());

		List<String> gapAreas = gapSections.stream().flatMap(section -> section.getQuestions().stream())
				.filter(questionLower -> questionLower.getAnswerScore() < 4).map(question -> question.getTitle())
				.collect(Collectors.toList());
		return gapAreas;
	}

	private Optional<MaturityLevel> findMaturityLevel(Double maturityPercent) {
		if (maturityPercent == null) {
			return Optional.empty();
		}

		if (maturityPercent < 50) {
			return Optional.of(MaturityLevel.LOW);
		} else if (maturityPercent <= 74) {
			return Optional.of(MaturityLevel.MEDIUM);
		} else if (maturityPercent <= 89) {
			return Optional.of(MaturityLevel.HIGH);
		} else {
			return Optional.of(MaturityLevel.ELITE);
		}
	}

}
