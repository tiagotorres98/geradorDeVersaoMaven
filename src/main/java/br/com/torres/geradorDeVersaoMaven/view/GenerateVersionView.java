package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MavenProfilesJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.JSON.dto.GenerateVersionParametersDTO;
import br.com.torres.geradorDeVersaoMaven.JSON.file.IJSONFileRepository;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IDialogRender;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;

@Component
@Primary
public class GenerateVersionView extends JPanel implements IPanelsRender {

	private IJSONFileRepository repository;

	private final JComboBox<Object> profileList;

	private final JComboBox<Object> clients;

	private final JComboBox<Object> systems;

	private final JCheckBox chckbxNoProfiles;

	private final JCheckBox chckbxNoMoverWar;

	@Autowired
	private IDialogRender process;

	public JPanel render() {
		return this;
	}

	@Inject
	public GenerateVersionView(IJSONFileRepository repository) {
		this.repository = repository;
		setLayout(null);

		JLabel lblClients = new JLabel("Cliente");
		lblClients.setBounds(32, 112, 176, 31);
		lblClients.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(lblClients);

		clients = new JComboBox<Object>();
		lblClients.setLabelFor(clients);
		clients.setBounds(223, 113, 290, 29);
		add(clients);
		getClientsList(clients);

		JLabel lblSystems = new JLabel("Sistema");
		lblSystems.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblSystems.setBounds(32, 159, 176, 31);
		add(lblSystems);

		systems = new JComboBox();
		lblSystems.setLabelFor(systems);
		systems.setBounds(223, 159, 290, 31);
		add(systems);
		getSystemList(systems);
		systems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getProfileList(systems.getSelectedItem());
			}
		});

		JLabel params = new JLabel("Parâmetros");
		params.setFont(new Font("Tahoma", Font.PLAIN, 23));
		params.setBounds(205, 276, 119, 53);
		add(params);

		chckbxNoProfiles = new JCheckBox("Gerar Versão Sem Profiles");
		chckbxNoProfiles.setBounds(32, 328, 234, 29);
		add(chckbxNoProfiles);

		Button buttonGenerate = new Button("Gerar Versão");
		buttonGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonGenerate.setFont(new Font("Dialog", Font.BOLD, 21));
		buttonGenerate.setBackground(Color.ORANGE);
		buttonGenerate.setBounds(171, 443, 174, 44);
		add(buttonGenerate);

		chckbxNoMoverWar = new JCheckBox("Não Mover WAR(s) Para Pasta");
		chckbxNoMoverWar.setBounds(273, 328, 245, 29);
		add(chckbxNoMoverWar);

		JButton btnReloadData = new JButton("Recarregar Dados");
		btnReloadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clients.removeAllItems();
				systems.removeAllItems();
				getClientsList(clients);
				getSystemList(systems);
			}
		});
		btnReloadData.setBounds(33, 67, 157, 29);
		add(btnReloadData);

		profileList = new JComboBox();
		profileList.setBounds(223, 206, 290, 31);
		add(profileList);
		getProfileList(systems.getSelectedItem());

		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblProfile.setBounds(32, 206, 176, 31);
		add(lblProfile);

	}

	public void getClientsList(JComboBox<Object> clients) {
		for (ClientJSONComponent client : repository.getJsonFile().getClient()) {
			clients.addItem(client.getName());
		}
	}

	public void getSystemList(JComboBox<Object> systems) {
		for (SystemJSONComponent system : repository.getJsonFile().getSystem()) {
			if (system.getMavenProfiles().size() > 0) {
				systems.addItem(system.getName());
			}
		}
	}

	public void getProfileList(Object system) {
		try {
			profileList.removeAllItems();
			for (SystemJSONComponent systems : repository.getJsonFile().getSystem()) {
				if (systems.getName().equals(system.toString())) {
					for (MavenProfilesJSONComponent profiles : systems.getMavenProfiles()) {
						profileList.addItem((Object) profiles.getName());
					}
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public boolean validateData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetValues() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		DataToGenerateVersionDTO data = new DataToGenerateVersionDTO(getParameters(), getMavenProfilesSelected(),
				getSelectedClient(), getSelectedSystem());

		process.render(data);

	}

	public GenerateVersionParametersDTO getParameters() {
		GenerateVersionParametersDTO parameter = new GenerateVersionParametersDTO();
		parameter.setDoNotMoveWarToFolder(chckbxNoMoverWar.isSelected());
		parameter.setGenerateVersionWithNoProfiles(chckbxNoProfiles.isSelected());
		return parameter;
	}

	public ClientJSONComponent getSelectedClient() {
		for (ClientJSONComponent client : repository.getClients()) {
			if (client.getName().equals(clients.getSelectedItem().toString())) {
				return client;
			}
		}
		return null;
	}

	public SystemJSONComponent getSelectedSystem() {
		for (SystemJSONComponent system : repository.getSystems()) {
			if (system.getName().equals(systems.getSelectedItem().toString())) {
				return system;
			}
		}
		return null;
	}

	public MavenProfilesJSONComponent getMavenProfilesSelected() {
		for (SystemJSONComponent system : repository.getSystems()) {
			if (system.getName().equals(systems.getSelectedItem().toString())) {
				for (MavenProfilesJSONComponent profile : system.getMavenProfiles()) {
					if (profile.getName().equals(profileList.getSelectedItem())) {
						return profile;
					}
				}
			}
		}
		return null;
	}
}
