package br.com.torres.geradorDeVersaoMaven.services;

import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.view.ProcessView;

public interface IFileTransporter {

	public void transport(ProcessView dialog,DataToGenerateVersionDTO data);
	
}
