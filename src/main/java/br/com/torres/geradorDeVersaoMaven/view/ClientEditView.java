package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ClientJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.MainJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.file.IJSONFileRepository;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.beans.PropertyChangeEvent;

@Component
@Qualifier("ClientEditView")
public class ClientEditView extends JPanel implements IPanelsRender {
	
	private JTextField clientName;
	
	private JFileChooser file;
	
	private IJSONFileRepository repository;
	
	private final String DEFAULT_WAR_OUTPUT_PATH = System.getProperty("user.home") + "\\Documents";
	
	@Autowired
	private IValidationsExecutor validationsExecutor;

	private ClientJSONComponent client;
	
	private final JComboBox clients;

	/**
	 * Create the panel.
	 */

	@Autowired
	public ClientEditView(final IJSONFileRepository repository) {
		this.repository = repository;
		setLayout(null);

		JLabel lblRegisterSystem = new JLabel("Cadastro Cliente");
		lblRegisterSystem.setBounds(145, 16, 239, 53);
		lblRegisterSystem.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblRegisterSystem);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNome.setBounds(38, 176, 69, 20);
		add(lblNome);

		clientName = new JTextField();
		clientName.setBounds(218, 173, 287, 26);
		add(clientName);
		clientName.setColumns(10);

		Button buttonSave = new Button("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSave.setFont(new Font("Dialog", Font.BOLD, 21));
		buttonSave.setBackground(Color.ORANGE);
		buttonSave.setBounds(95, 443, 174, 44);
		add(buttonSave);

		JLabel lblNome_2 = new JLabel("");
		lblNome_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNome_2.setBounds(33, 156, 69, 20);
		add(lblNome_2);

		final JLabel warPath = new JLabel("");
		warPath.setFont(new Font("Tahoma", Font.PLAIN, 18));
		warPath.setBounds(218, 263, 332, 29);
		add(warPath);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				file = new JFileChooser(DEFAULT_WAR_OUTPUT_PATH);
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				file.setDialogTitle("Selecione o diretório...");
				file.showOpenDialog(null);
				warPath.setText(file.getSelectedFile().getAbsolutePath());

			}
		});
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBuscar.setBounds(367, 215, 115, 29);
		add(btnBuscar);

		JLabel lblPastaParaSalvar = new JLabel("Pasta para Salvar WAR ");
		lblPastaParaSalvar.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPastaParaSalvar.setBounds(38, 214, 261, 31);
		add(lblPastaParaSalvar);

		Button buttonSave_1 = new Button("Excluir");
		buttonSave_1.setForeground(Color.WHITE);
		buttonSave_1.setFont(new Font("Dialog", Font.BOLD, 21));
		buttonSave_1.setBackground(Color.RED);
		buttonSave_1.setBounds(303, 443, 174, 44);
		add(buttonSave_1);

		JLabel lblCaminhoWar = new JLabel("Caminho WAR:");
		lblCaminhoWar.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCaminhoWar.setBounds(38, 261, 168, 31);
		add(lblCaminhoWar);

		clients = new JComboBox();
		clients.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					for (ClientJSONComponent client : repository.getJsonFile().getClient()) {
						if (clients.getSelectedItem().toString().equals(client.getName())) {
							clientName.setText(client.getName());
							file = new JFileChooser(new File(client.getOutputWarPath()));
							warPath.setText(file.getCurrentDirectory().getAbsolutePath());
						}
					}
				} catch (Exception ex) {
				}
			}
		});

		clients.setBounds(213, 128, 287, 26);
		add(clients);
		getClientsList(clients);

		JLabel lblSistema = new JLabel("Clientes");
		lblSistema.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblSistema.setBounds(38, 126, 86, 31);
		add(lblSistema);

		JButton btnNewButton = new JButton("Recarregar Dados");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clients.removeAllItems();
				getClientsList(clients);
			}
		});
		btnNewButton.setBounds(33, 87, 157, 29);
		add(btnNewButton);

	}

	public void getClientsList(JComboBox<Object> clients) {
		for (ClientJSONComponent client : repository.getJsonFile().getClient()) {
			clients.addItem(client.getName());
		}
	}

	@Override
	public JPanel render() {
		return this;
	}

	@Override
	public boolean validateData() {
		MainJSONComponent json = new MainJSONComponent();
		client = new ClientJSONComponent(this.clientName.getText(), getWarPath());
		json.getClient().add(client);
		return validationsExecutor.execute(json, ValidationType.CLIENT_VALIDATION);
	}

	@Override
	public void resetValues() {
		// TODO Auto-generated method stub
		clients.removeAllItems();
		getClientsList(clients);
	}
	
	public String getWarPath() {
		try {
			return file.getSelectedFile().getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}


	@Override
	public void save() {
		MainJSONComponent json = repository.getJsonFile();
		if (validateData()) {
			for(ClientJSONComponent clientRepo:json.getClient()) {
				if(clientRepo.getName().equals(clients.getSelectedItem())) {
					clientRepo.setName(client.getName());
					clientRepo.setOutputWarPath(client.getOutputWarPath());
				}
			}
			repository.save(json);
			resetValues();
		}
	}
}
