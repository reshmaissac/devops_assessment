package com.computing.devopsassessmet.model;

import lombok.Getter;

@Getter
public enum GapArea {
	
	CICD_PIPELINE("CICD Pipeline"),
	
	TEST_AUTOMATION("Test Automation"),
	
	CONFIGURATION_AUTOMATION("Configuration Automation"),
	
	CONTAINARIZATION("Containerization"),
	
	CONTINUOUS_MONITORING("Continuous Monitoring"),
	
	DEPLOYMENT_FREQUENCY("Deployment Frequency"),
	
	MTTR("MTTR"),
	
	CICD_AUTOMATION("CICD Automation");
	
	private String tutorialArea;

	GapArea(String tutorialArea) {
		this.tutorialArea = tutorialArea;
	}

}
