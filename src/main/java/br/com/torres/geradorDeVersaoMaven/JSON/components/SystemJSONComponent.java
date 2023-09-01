package br.com.torres.geradorDeVersaoMaven.JSON.components;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SystemJSONComponent {
	
	private List<ProjectPathJSONComponent> projectPath;
	private List<MavenProfilesJSONComponent> mavenProfiles;
	private String name;

	public List<ProjectPathJSONComponent> getProjectPath() {
		if(projectPath == null) projectPath = new ArrayList<ProjectPathJSONComponent>();
		return projectPath;
	}
	public List<MavenProfilesJSONComponent> getMavenProfiles() {
		if(mavenProfiles == null) mavenProfiles = new ArrayList<MavenProfilesJSONComponent>();
		return mavenProfiles;
	}
	
	public String getName() {
		return name;
	}
	
}
