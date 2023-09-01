package br.com.torres.geradorDeVersaoMaven.validations.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.validations.IValidations;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;

@Component
public class ValidationsExecutor implements IValidationsExecutor {

	private List<? extends IValidations> validations;
	
	public ValidationsExecutor(List<? extends IValidations> validations) {
		this.validations = validations;
	}

	@Override
	public boolean execute(MainJSONComponent json, ValidationType type) {
		List<String> erros = new ArrayList<>();
	
		for(IValidations validation:validations) {
			erros.addAll(validation.validate(json,type));
		}

		if(!erros.isEmpty()) {
			JOptionPane.showMessageDialog(null, new JList<Object>(erros.toArray()), "Atenção! Corrija os erros e tente novamente.", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

}
