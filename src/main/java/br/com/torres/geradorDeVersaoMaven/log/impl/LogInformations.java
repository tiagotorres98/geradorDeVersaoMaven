package br.com.torres.geradorDeVersaoMaven.log.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.log.ILogInfomations;

@Component
public class LogInformations implements ILogInfomations {

	private String configPathFolder;

	private FileWriter logFile;

	private String logValue = "";

	public LogInformations(@Qualifier("ConfigPathFolder") String configPathFolder) {
		this.configPathFolder = configPathFolder;
	}

	public FileWriter getLogFile() {
		FileWriter file = null;
		try {
			String path = configPathFolder.concat(LocalDate.now().toString()).concat("_log.txt");
			File log = new File(path);
			if (log.exists()) {
				return new FileWriter(log);
			}
			file = new FileWriter(log);
			file.write("");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível criar pasta para armazenar WAR. " + e);
			return null;
		}
		return file;
	}

	@Override
	public void toLog(String log) {
		logValue += log.concat("\n");
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub
		try {
			logFile = getLogFile();
			logFile.append(logValue);
			logFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void resetLog() {
		// TODO Auto-generated method stub
		logValue = "";
	}

}
