package br.com.torres.geradorDeVersaoMaven.JSON.dto;

import java.util.HashMap;
import java.util.Map;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MavenProfilesJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataToGenerateVersionDTO {

	private ClientJSONComponent client;
	private SystemJSONComponent system;
	private MavenProfilesJSONComponent profile;
	private GenerateVersionParametersDTO parameters;

	public DataToGenerateVersionDTO(GenerateVersionParametersDTO parameters,MavenProfilesJSONComponent profile,ClientJSONComponent client, SystemJSONComponent system) {
		this.client = client;
		this.system = system;
		this.profile = profile;
		this.parameters = parameters;
		
	}
	
	public Map<Integer,ProjectPathJSONComponent> getProfilesToMap() {
		Map<Integer,ProjectPathJSONComponent> map = new HashMap<Integer,ProjectPathJSONComponent>();
		for (ProjectPathJSONComponent project : system.getProjectPath()) {
			map.put(project.getSequencial(), project);
		}
		return map;
	}

}
