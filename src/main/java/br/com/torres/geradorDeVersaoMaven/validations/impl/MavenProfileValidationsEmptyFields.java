package br.com.torres.geradorDeVersaoMaven.validations.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MavenProfilesJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.IValidations;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

@Component
public class MavenProfileValidationsEmptyFields implements IValidations {

	@Override
	public List<String> validate(MainJSONComponent json, ValidationType type) {
		List<String> errors = new ArrayList<>();

		if (!type.equals(ValidationType.PROFILES_VALIDATION)) {return errors;}

		for(SystemJSONComponent system:json.getSystem()) {
			for(MavenProfilesJSONComponent profiles:system.getMavenProfiles()) {
				if(profiles.getName().isEmpty()) {
					errors.add("É obrigatório preencher o campo nome.");
				}
				if(profiles.getMavenProfiles().size()==0) {
					errors.add("É obrigatório preencher pelo menos um nome de profile na tabela.");
				}
				for(String s:profiles.getMavenProfiles()) {
					if(s.isEmpty()) {
						errors.add("Não é permitido deixar campos vazios na tabela.");
					}
				}
			}
		}
			
		return errors;
	}

}