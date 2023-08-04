package com.computing.devopsassessmet.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = TutorialItemsDeserializer.class)
public class TutorialItems {
	private String title;
	private List<Url> urls;

	public TutorialItems(String title, List<Url> urls) {
		this.title = title;
		this.urls = urls;
	}

}
