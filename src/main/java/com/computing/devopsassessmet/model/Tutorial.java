package com.computing.devopsassessmet.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("tutorials")
public class Tutorial {
	// 
	public Tutorial() {
	}

	@Id
	private String id;
	@Indexed(unique = true)
	private String area;
	private String description;
	private List<TutorialItems> tutorialItems;

}
