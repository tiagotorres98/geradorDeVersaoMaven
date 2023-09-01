package br.com.torres.geradorDeVersaoMaven.JSON.util;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MavenProfilesJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;

public class JSONCreatorDefaultContent {

	public static MainJSONComponent defaultJSON(){
		MainJSONComponent main = new MainJSONComponent();
		try {
		ProjectPathJSONComponent pj = new ProjectPathJSONComponent();
		pj.setPath("");
		pj.setSequencial(1);
		pj.setName("");
		pj.setExecuteMavenProfile("nao");
		
		MavenProfilesJSONComponent profiles = new MavenProfilesJSONComponent();
		profiles.setName("");
		profiles.setMavenProfiles(new ArrayList<String>());
		
		SystemJSONComponent system = new SystemJSONComponent();
		system.setName("");
		system.setProjectPath(new ArrayList<ProjectPathJSONComponent>());
		system.setMavenProfiles(new ArrayList<MavenProfilesJSONComponent>());
		system.getProjectPath().add(pj);
		system.getMavenProfiles().add(profiles);

		ClientJSONComponent client = new ClientJSONComponent();
		client.setName("");
		client.setOutputWarPath("");
		
		main.setSystem(new ArrayList<SystemJSONComponent>());
		main.getSystem().add(system);
		main.setClient(new ArrayList<ClientJSONComponent>());
		main.getClient().add(client);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return main;
	}

	
}
