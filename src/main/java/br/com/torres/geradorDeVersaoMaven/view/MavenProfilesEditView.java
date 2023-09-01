package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MavenProfilesJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.file.IJSONFileRepository;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;
import br.com.torres.geradorDeVersaoMaven.view.components.DataTableModel;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;

@Qualifier("MavenProfilesEditView")
@Component
public class MavenProfilesEditView extends JPanel implements IPanelsRender {

	private JTextField profileName;
	private JTable table;

	private DataTableModel model;

	private final JComboBox systems;

	private IJSONFileRepository repository;

	private final JComboBox<Object> profileList;

	@Autowired
	private IValidationsExecutor validationsExecutor;

	private MavenProfilesJSONComponent mavenProfiles;

	private List<String> profiles;

	/**
	 * Create the panel.
	 */

	@Autowired
	public MavenProfilesEditView(IJSONFileRepository repository) {
		this.repository = repository;
		int count = 1;
		setLayout(null);

		JLabel lblRegisterProfile = new JLabel("Editar Maven Profiles");
		lblRegisterProfile.setBounds(132, 16, 319, 37);
		lblRegisterProfile.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblRegisterProfile);

		JLabel lblNome = new JLabel("Nome *");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNome.setBounds(41, 197, 87, 31);
		add(lblNome);

		profileName = new JTextField();
		profileName.setBounds(178, 203, 324, 26);
		add(profileName);
		profileName.setColumns(10);

		Button buttonSave = new Button("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateEditingColumn()) {
					save();
				}
			}
		});
		buttonSave.setFont(new Font("Dialog", Font.BOLD, 21));
		buttonSave.setBackground(Color.ORANGE);
		buttonSave.setBounds(171, 443, 174, 44);
		add(buttonSave);

		JLabel lblProjetos = new JLabel("Adicione um ou mais profiles");
		lblProjetos.setToolTipText("");
		lblProjetos.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProjetos.setBounds(124, 231, 304, 29);
		add(lblProjetos);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 294, 469, 143);
		add(scrollPane);

		this.model = new DataTableModel();

		model.addColumn("Profile");

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JButton btnAdicionarLinha = new JButton("Adicionar Linha");
		btnAdicionarLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[] { "", "", "" });
			}
		});
		btnAdicionarLinha.setBounds(49, 263, 143, 29);
		add(btnAdicionarLinha);

		JButton btnAdicionarLinha_1 = new JButton("Apagar Linha");
		btnAdicionarLinha_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeRow(table.getSelectedRow());
			}
		});
		btnAdicionarLinha_1.setBounds(198, 263, 143, 29);
		add(btnAdicionarLinha_1);

		JButton btnAdicionarLinha_1_1 = new JButton("↑");
		btnAdicionarLinha_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow() - 1);
				table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
			}
		});
		btnAdicionarLinha_1_1.setBounds(347, 263, 67, 29);
		add(btnAdicionarLinha_1_1);

		JButton btnAdicionarLinha_1_1_1 = new JButton("↓");
		btnAdicionarLinha_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow() + 1);
				table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() + 1);
			}
		});
		btnAdicionarLinha_1_1_1.setBounds(422, 263, 67, 29);
		add(btnAdicionarLinha_1_1_1);

		JLabel lblSystems = new JLabel("Sistema");
		lblSystems.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblSystems.setBounds(42, 125, 102, 31);
		add(lblSystems);

		systems = new JComboBox();
		systems.setBounds(178, 129, 324, 31);
		add(systems);
		getSystemList(systems);
		systems.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (systems.getItemCount() > 0) {
					getProfileList(systems.getSelectedItem());
				}
			}
		});

		JButton btnNewButton = new JButton("Recarregar Dados");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				systems.removeAllItems();
				getSystemList(systems);
			}
		});
		btnNewButton.setBounds(41, 80, 157, 29);
		add(btnNewButton);

		JLabel lblNomeProfile = new JLabel("Profile");
		lblNomeProfile.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNomeProfile.setBounds(42, 162, 102, 31);
		add(lblNomeProfile);

		profileList = new JComboBox();
		profileList.setBounds(178, 166, 324, 31);
		add(profileList);
		profileList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setProfileListValues(profileList.getSelectedItem());
			}
		});

	}

	public boolean validateEditingColumn() {
		if (table.isEditing()) {
			JOptionPane.showMessageDialog(null,
					"A tabela possui uma célula que ainda está em edição. Pressione 'Enter' na mesma para finalizar a edição");
		}
		return !table.isEditing();
	}

	public void getSystemList(JComboBox<Object> systems) {
		for (SystemJSONComponent system : repository.getJsonFile().getSystem()) {
			if (system.getMavenProfiles().size() > 0) {
				systems.addItem(system.getName());
			}
		}
	}

	public void getProfileList(Object system) {
		profileList.removeAllItems();
		for (SystemJSONComponent systems : repository.getJsonFile().getSystem()) {
			if (systems.getName().equals(system.toString())) {
				for (MavenProfilesJSONComponent profiles : systems.getMavenProfiles()) {
					profileList.addItem((Object) profiles.getName());
				}
			}
		}
	}

	public void setProfileListValues(Object profile) {
		try {
			for (SystemJSONComponent systems : repository.getJsonFile().getSystem()) {
				for (MavenProfilesJSONComponent profiles : systems.getMavenProfiles()) {
					if (profiles.getName().equals(profile.toString())) {
						profileName.setText(profiles.getName());
						model.removeAllModelRows();
						for (String s : profiles.getMavenProfiles()) {
							model.addRow(new Object[] { s });
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public JPanel render() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setMavenProfilesValues() {

		this.profiles = new ArrayList<String>();
		for (int i = 0; i < model.getRowCount(); i++) {
			String tableValue = (String) model.getValueAt(i, 0);
			this.profiles.add(tableValue);
		}

	}

	public void setMavenComponentsListValues(MainJSONComponent json) {
		this.mavenProfiles = new MavenProfilesJSONComponent(profileName.getText(), profiles);
		for (SystemJSONComponent systemRepo : repository.getJsonFile().getSystem()) {
			if (systemRepo.getName().equals(systems.getSelectedItem().toString())) {
				systemRepo.getMavenProfiles().add(mavenProfiles);
				json.getSystem().add(systemRepo);
			}
		}
	}

	@Override
	public boolean validateData() {
		// TODO Auto-generated method stub
		MainJSONComponent json = new MainJSONComponent();
		setMavenProfilesValues();
		setMavenComponentsListValues(json);
		return validationsExecutor.execute(json, ValidationType.PROFILES_VALIDATION);

	}

	@Override
	public void resetValues() {
		// TODO Auto-generated method stub
		systems.removeAllItems();
		getSystemList(systems);
	}
	
	public void update(MainJSONComponent json) {
		for (SystemJSONComponent systemRepo : json.getSystem()) {
			if (systemRepo.getName().equals(systems.getSelectedItem().toString())) {
				for(MavenProfilesJSONComponent profiles:systemRepo.getMavenProfiles()) {
					if(profiles.getName().equals(profileList.getSelectedItem().toString())) {
						profiles.setName(profileName.getText());
						profiles.setMavenProfiles(new ArrayList<String>());
						for(int i = 0;i<model.getRowCount();i++) {
							profiles.getMavenProfiles().add(model.getValueAt(i,0).toString());
						}
					}
				}
			}
		}
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		if (validateData()) {
			MainJSONComponent json = repository.getJsonFile();
			update(json);
			repository.save(json);
			resetValues();
		}
	}
}
