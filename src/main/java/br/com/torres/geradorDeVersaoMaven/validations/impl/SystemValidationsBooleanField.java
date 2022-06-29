package br.com.torres.geradorDeVersaoMaven.validations.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.IValidations;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

@Component
public class SystemValidationsBooleanField implements IValidations {

	@Override
	public List<String> validate(MainJSONComponent json, ValidationType type) {
		List<String> errors = new ArrayList<>();

		if (!type.equals(ValidationType.SYSTEM_VALIDATION))
			return errors;

		for (SystemJSONComponent system : json.getSystem()) {
			for (ProjectPathJSONComponent project : system.getProjectPath()) {
				if(!project.getExecuteMavenProfile().equals("sim") && !project.getExecuteMavenProfile().equals("nao")) {
					errors.add("O campo 'Executar Maven Profiles deve ser preenchido apenas com 'sim' ou 'nao'");
				}
			}
		}

		return errors;
	}

}