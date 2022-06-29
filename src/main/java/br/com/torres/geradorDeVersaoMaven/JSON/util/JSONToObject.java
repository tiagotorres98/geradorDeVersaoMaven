package br.com.torres.geradorDeVersaoMaven.JSON.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;

public class JSONToObject {

	public static MainJSONComponent convert(String json) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, MainJSONComponent.class);
	}
	
	public static MainJSONComponent convert(List<String> jsonList) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "";
		
		for(String s:jsonList) {
			json += s;
		}
		return objectMapper.readValue(json, MainJSONComponent.class);
	}
	
}
