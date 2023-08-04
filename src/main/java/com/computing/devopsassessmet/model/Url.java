package com.computing.devopsassessmet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Url {
	private String urlLink;
	private String imageUrl;

	public Url(String urlLink, String imageUrl) {
		this.urlLink = urlLink;
		this.imageUrl = imageUrl;
	}
}
