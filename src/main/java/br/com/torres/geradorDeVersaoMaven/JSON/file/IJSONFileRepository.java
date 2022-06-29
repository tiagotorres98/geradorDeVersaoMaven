package br.com.torres.geradorDeVersaoMaven.JSON.file;

import java.util.List;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;

public interface IJSONFileRepository {

	public MainJSONComponent getJsonFile();

	public void save(MainJSONComponent json);
	
	public List<ClientJSONComponent> getClients();
	
	public List<SystemJSONComponent> getSystems();
	
	
}
