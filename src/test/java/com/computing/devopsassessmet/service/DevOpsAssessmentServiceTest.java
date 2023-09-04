package com.computing.devopsassessmet.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.computing.devopsassessmet.model.MaturityLevel;
import com.computing.devopsassessmet.model.Question;
import com.computing.devopsassessmet.model.QuestionnaireResponse;
import com.computing.devopsassessmet.model.Section;
import com.computing.devopsassessmet.repository.ResponseRepository;
import com.computing.devopsassessmet.repository.TutorialRepository;

@WebMvcTest({ DevOpsAssessmentService.class })
class DevOpsAssessmentServiceTest {

	@MockBean
    private ResponseRepository responseRepository;
	
	@MockBean
    private TutorialRepository tutorialRepository;
  

    @Test
    void testSaveQuestionnaireResponse() {
    	
    	DevOpsAssessmentService service = new DevOpsAssessmentService(responseRepository, tutorialRepository);
    	
        QuestionnaireResponse response = new QuestionnaireResponse();
        Section section = new Section();
        Question question = new Question();
        section.setQuestions(Collections.singletonList(question));
        response.setSections(Collections.singletonList(section));
        
        List<String> gapAreas = new ArrayList<String>();
        gapAreas.add("CICD Pipeline");
        response.setGapAreas(gapAreas);
		response.setTotalScore(75d);
		response.setMaturityPercent(75d);
		response.setMaturityLevel(MaturityLevel.MEDIUM);
		service.saveQuestionnaireResponse(response);

        when(responseRepository.save(any(QuestionnaireResponse.class))).thenReturn(response);

       

           }
}
