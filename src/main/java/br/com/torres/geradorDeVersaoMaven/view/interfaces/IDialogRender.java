package br.com.torres.geradorDeVersaoMaven.view.interfaces;

import javax.swing.JDialog;

import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;

public interface IDialogRender {

	public JDialog render(DataToGenerateVersionDTO data);
	
}
