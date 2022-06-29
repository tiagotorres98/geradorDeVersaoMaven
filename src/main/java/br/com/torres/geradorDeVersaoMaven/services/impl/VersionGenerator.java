package br.com.torres.geradorDeVersaoMaven.services.impl;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.services.ICommandExecutor;
import br.com.torres.geradorDeVersaoMaven.services.IFileTransporter;
import br.com.torres.geradorDeVersaoMaven.services.IVersionGenerator;
import br.com.torres.geradorDeVersaoMaven.view.ProcessView;

@Component
public class VersionGenerator implements IVersionGenerator {
	
	private ICommandExecutor commandExecutor;
	private IFileTransporter fileTranporter;

	@Autowired
	public VersionGenerator(ICommandExecutor commandExecutor, IFileTransporter fileTranporter) {
		this.commandExecutor = commandExecutor;
		this.fileTranporter = fileTranporter;
	}


	@Override
	public void generate(ProcessView dialog, DataToGenerateVersionDTO data) {
		// TODO Auto-generated method stub
		commandExecutor.execute(dialog, data);
		if(!data.getParameters().isDoNotMoveWarToFolder()) fileTranporter.transport(dialog,data);
		JOptionPane.showMessageDialog(null, "A versão foi gerada com sucesso!");
		dialog.dispose();
	}

}
