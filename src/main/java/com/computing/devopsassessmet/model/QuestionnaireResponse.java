package com.computing.devopsassessmet.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("questionnaireresponses")
public class QuestionnaireResponse {
	@Id
	private String id;
	private String email;
    private List<Section> sections;
    private String timestamp;
    private double totalScore;
    private double maturityPercent;
    private MaturityLevel maturityLevel;
    private List<String> gapAreas;
}
