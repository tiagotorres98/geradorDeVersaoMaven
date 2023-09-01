package br.com.torres.geradorDeVersaoMaven.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.log.ILogInfomations;
import br.com.torres.geradorDeVersaoMaven.services.ICommandExecutor;
import br.com.torres.geradorDeVersaoMaven.view.ProcessView;
import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class CommandExecutor implements ICommandExecutor {

	private Process process;
	private BufferedReader reader;
	private String[] commands;
	private String configPathFolder;
	private ILogInfomations log;
	
	public CommandExecutor(@Qualifier("ConfigPathFolder")String configPathFolder, ILogInfomations log) {
		this.configPathFolder = configPathFolder;
		this.log = log;
	}

	@Override
	public void execute(ProcessView dialog, DataToGenerateVersionDTO data) {
		log.resetLog();
		List<String> errors = new ArrayList<>();
		commands = new String[4];
		commands[0] = "cmd";
		commands[1] = "/c";
		commands[2] = "mvn clean install -DskipTests";
		commands[3] = "";
		String lastLine = "";

		for(int i = 0; i < data.getProfilesToMap().size();i++) {
			
			ProjectPathJSONComponent project = data.getProfilesToMap().get(i+1);
			log.toLog("\n\n");
			log.toLog("Iniciando processamento para o projeto: ".concat(project.getName()));
			log.toLog("\n\n");
			
			dialog.getLblSistemaValue().setText(data.getSystem().getName());
			dialog.getLblProjetoValue().setText(project.getName());
			dialog.getLblProfileValue().setText(data.getProfile().getName());
			dialog.getLblClientValue().setText(data.getClient().getName());

			if (project.getExecuteMavenProfile().equals("sim") && !data.getParameters().isGenerateVersionWithNoProfiles()) {
				commands[3] = "-P";
				Iterator<String> profileString = data.getProfile().getMavenProfiles().iterator();
				while (profileString.hasNext()) {
					commands[3] += profileString.next();
					if (profileString.hasNext()) {
						commands[3] += ",";
					}
				}
			}

			File path = new File(project.getPath());
			String line = "";

			try {
				process = Runtime.getRuntime().exec(commands, null, path);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while (true) {
					line = reader.readLine();
					if (line == null) {
						break;
					}else { 
						lastLine = line; 
					}
					dialog.getTextArea().append(line + "\n");
					log.toLog(line);
				}

			} catch (Exception e) {
				errors.add("Não foi possível compilar o projeto " + project.getName() + ". Verifique o log.");
				log.toLog("\n ----------------------------------------- \n".concat(e.toString()));
			}

			if (lastLine.startsWith("[ERROR]")) {
				errors.add("Não foi possível compilar o projeto " + project.getName() + ". Verifique o log.");
			}
			
			if(dialog.getKillThread()) {
				process.destroy();
				break;
			}

		}
		if (errors.size() > 0) {
			JOptionPane.showMessageDialog(null, new JList<>(errors.toArray()));
		}
		log.write();
	}

}
