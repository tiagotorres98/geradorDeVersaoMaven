package br.com.torres.geradorDeVersaoMaven.validations;

import java.util.List;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

public interface IValidations {

	public List<String> validate(MainJSONComponent jsonm,ValidationType type);
	
}
