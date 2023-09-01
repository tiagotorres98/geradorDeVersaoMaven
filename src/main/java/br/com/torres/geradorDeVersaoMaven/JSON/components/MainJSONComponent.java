package br.com.torres.geradorDeVersaoMaven.JSON.components;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.Setter;

@Setter
public class MainJSONComponent {

	private List<SystemJSONComponent> system;
	private List<ClientJSONComponent> client;

	public String toJsonString() throws Exception {
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(this);
		} catch (Exception e) {
			throw new Exception("Não foi possivel retornar um JSON");
		}
		return json;
	}
	
	public List<ClientJSONComponent> getClient(){
		if(client == null) client = new ArrayList<ClientJSONComponent>();
		return client;
	}
	
	public List<SystemJSONComponent> getSystem(){
		if(system == null) system = new ArrayList<SystemJSONComponent>();
		return system ;
	}
}
