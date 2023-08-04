package com.computing.devopsassessmet.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TutorialItemsDeserializer extends JsonDeserializer<TutorialItems> {

	@Override
	public TutorialItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		String title = node.get("title").asText();
		List<Url> urls = new ArrayList<>();

		JsonNode urlNode = node.get("url");
		if (urlNode != null && urlNode.isArray()) {
			for (JsonNode urlEntry : urlNode) {
				String urlLink = urlEntry.get("urlLink").asText();
				String imageUrl = urlEntry.get("imageUrl").asText();
				urls.add(new Url(urlLink, imageUrl));
			}
		}
		return new TutorialItems(title, urls);
	}
}
