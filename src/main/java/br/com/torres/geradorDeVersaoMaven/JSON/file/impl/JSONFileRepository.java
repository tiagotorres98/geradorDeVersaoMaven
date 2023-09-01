package br.com.torres.geradorDeVersaoMaven.JSON.file.impl;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.file.IJSONFileRepository;
import br.com.torres.geradorDeVersaoMaven.JSON.util.JSONCreatorDefaultContent;
import br.com.torres.geradorDeVersaoMaven.JSON.util.JSONToObject;

@Component
public class JSONFileRepository implements IJSONFileRepository {
	
	private MainJSONComponent json;
	
	private String fileNameAndPath;
	
	public JSONFileRepository(@Qualifier("FileNameAndPath")String fileNameAndPath) {
		this.fileNameAndPath = fileNameAndPath;
	}

	public MainJSONComponent getJsonFile(){
		File json = new File(fileNameAndPath);
		if (!json.exists()) {
			return JSONCreatorDefaultContent.defaultJSON();
		}
		try {
			return JSONToObject.convert(Files.readAllLines(json.toPath()));
		} catch (Exception e) {
			return JSONCreatorDefaultContent.defaultJSON();
		}
	}
	
	public List<ClientJSONComponent> getClients() {
		List<ClientJSONComponent> clients; 
		try {
			clients = getJsonFile().getClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			clients = new ArrayList<>();
		}
		return clients;
	}
	
	public List<SystemJSONComponent> getSystems() {
		List<SystemJSONComponent> systems; 
		try {
			systems = getJsonFile().getSystem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			systems = new ArrayList<>();
		}
		return systems;
	}

	public void save(MainJSONComponent json) {
		this.json = json;
		try {
			removeEmptyData();
			FileWriter file = new FileWriter(fileNameAndPath);
			file.write(this.json.toJsonString());
			file.close();
			JOptionPane.showMessageDialog(null, "O Registrado foi Salvo com Sucesso!");
		} catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Não foi Possível Gravar o JSON no arquivo.");
		}
	}

	public void removeEmptyData() {
		removeEmptySystem();
		removeEmptyClient();
	}

	public void removeEmptyClient() {
		List<ClientJSONComponent> toRemove = new ArrayList<>();
		for (ClientJSONComponent client : this.json.getClient()) {
			if (client.getName().isEmpty()) {
				toRemove.add(client);
			}
		}

		this.json.getClient().removeAll(toRemove);
	}

	public void removeEmptySystem() {
		List<SystemJSONComponent> toRemove = new ArrayList<>();		
		List<ProjectPathJSONComponent> projectToRemove = new ArrayList<>();		
		for (SystemJSONComponent system : this.json.getSystem()) {
			for(ProjectPathJSONComponent project:system.getProjectPath()) {
				if(project.getPath().isEmpty() && (project.getName().isEmpty())) {
					projectToRemove.add(project);
				}
			}
			system.getProjectPath().removeAll(projectToRemove);
			if (system.getName().isEmpty()) {
				toRemove.add(system);
			}
		}

		this.json.getSystem().removeAll(toRemove);
	}

}
