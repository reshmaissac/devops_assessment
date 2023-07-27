package com.computing.devopsassessmet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
	private String title;
	private String questionText;
	private String answerText;
	private int answerScore;
}
