package br.com.torres.geradorDeVersaoMaven.validations;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

public interface IValidationsExecutor {

	public boolean execute(MainJSONComponent json, ValidationType type);
	
}
