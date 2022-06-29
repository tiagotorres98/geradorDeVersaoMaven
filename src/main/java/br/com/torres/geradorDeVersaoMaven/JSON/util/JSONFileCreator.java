package br.com.torres.geradorDeVersaoMaven.JSON.util;

import java.io.File;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class JSONFileCreator {

	private String fileNameAndPath;
	
	private String configPathFolder;
	
	public JSONFileCreator(@Qualifier("FileNameAndPath")String fileNameAndPath,@Qualifier("ConfigPathFolder")String configPathFolder) {
		this.fileNameAndPath = fileNameAndPath;
		this.configPathFolder = configPathFolder;
	}
	
	@Bean(name="FolderAndFileJSONCreator")
	public void create() throws Exception {
		createFolder();
		createFileJson();
	}
	
	public void createFolder() {
		File directory = new File(configPathFolder);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}
	
	public void createFileJson() throws Exception {
		
		File directory = new File(fileNameAndPath);
		if (!directory.exists()) {
	        FileWriter file = new FileWriter(fileNameAndPath);
	        file.write(JSONCreatorDefaultContent.defaultJSON().toJsonString());		
	        file.close(); 
		}
	}

}
