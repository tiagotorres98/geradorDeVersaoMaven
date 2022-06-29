package br.com.torres.geradorDeVersaoMaven.JSON.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPathJSONComponent {

	private Integer sequencial;
	private String path;
	private String executeMavenProfile;
	private String name;
}
