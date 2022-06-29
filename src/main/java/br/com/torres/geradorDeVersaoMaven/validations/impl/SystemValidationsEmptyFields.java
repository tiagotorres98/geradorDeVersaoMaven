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
public class SystemValidationsEmptyFields implements IValidations {

	@Override
	public List<String> validate(MainJSONComponent json,ValidationType type) {
		List<String> errors = new ArrayList<>();
		
		if(!type.equals(ValidationType.SYSTEM_VALIDATION)) return errors;
		
		for(SystemJSONComponent system: json.getSystem()) {
			if(system.getName().isEmpty()) {
				errors.add("O Nome do Sistema é Obrigatório");
			}
			for (ProjectPathJSONComponent project : system.getProjectPath()) {
				if(project.getName().isEmpty()) {
					errors.add("Não é permitido ter projetos com nome vazio na tabela.");
				}
				if(project.getPath().isEmpty()) {
					errors.add("Não é permitido ter projetos com caminho do diretorio vazio na tabela.");
				}
			}
		}

		
		return errors;
	}

}