package br.com.torres.geradorDeVersaoMaven.JSON.components;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MavenProfilesJSONComponent {

	private String name;
	private List<String> mavenProfiles; 
}
