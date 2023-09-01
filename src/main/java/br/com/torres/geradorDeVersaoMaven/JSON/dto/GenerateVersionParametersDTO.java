package br.com.torres.geradorDeVersaoMaven.JSON.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateVersionParametersDTO {

	private boolean generateVersionWithNoProfiles;
	private boolean doNotMoveWarToFolder;
	private boolean justMoveWarToFolder;
}
