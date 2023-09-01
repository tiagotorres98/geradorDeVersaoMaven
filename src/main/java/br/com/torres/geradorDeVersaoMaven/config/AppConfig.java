package br.com.torres.geradorDeVersaoMaven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	private final String ROOT_DIRECTORY = System.getProperty("user.dir").substring(0, 3);
	private final String FILE_DIRECTORY = ROOT_DIRECTORY.concat("geradorDeVersao\\");
	private final String FILE_NAME = "config.json";

	@Bean("FileNameAndPath")
	public String getStringFileNameAndPath() {
		return FILE_DIRECTORY.concat(FILE_NAME);
	}

	@Bean("ConfigPathFolder")
	public String getConfigPathFolder() {
		return FILE_DIRECTORY;
	}

	
}
