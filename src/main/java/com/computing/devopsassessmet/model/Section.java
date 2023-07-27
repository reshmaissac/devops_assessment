package com.computing.devopsassessmet.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Section {

	private String sectionName;
    private List<Question> questions;
    private double sectionScorePercent;
    
}
