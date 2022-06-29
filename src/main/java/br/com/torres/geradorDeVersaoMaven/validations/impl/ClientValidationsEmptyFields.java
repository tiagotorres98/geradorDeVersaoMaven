package br.com.torres.geradorDeVersaoMaven.validations.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.IValidations;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

@Component
public class ClientValidationsEmptyFields implements IValidations {

	@Override
	public List<String> validate(MainJSONComponent json,ValidationType type) {
		List<String> errors = new ArrayList<>();
		
		if(!type.equals(ValidationType.CLIENT_VALIDATION)) return errors;
		
		for(ClientJSONComponent client: json.getClient()) {
			if(client.getName().isEmpty()) {
				errors.add("O Nome do Cliente é Obrigatório.");
			}
			if(client.getOutputWarPath().isEmpty()) {
				errors.add("O Endereço de Pasta para Salvar o WAR é Obrigatório");
			}
		}
		return errors;
	}

}