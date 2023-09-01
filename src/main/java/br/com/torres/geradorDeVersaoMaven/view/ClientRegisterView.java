package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JButton;
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
import br.com.torres.geradorDeVersaoMaven.validations.IValidations;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;

@Component
@Qualifier("ClientRegisterView")
public class ClientRegisterView extends JPanel implements IPanelsRender {
	
	private final String DEFAULT_WAR_OUTPUT_PATH = System.getProperty("user.home")+"\\Documents";
	private JTextField clientName;
	private JFileChooser file;

	private IJSONFileRepository repository;
	
	@Autowired
	private IValidationsExecutor validationsExecutor;
	
	private ClientJSONComponent client;

	@Inject
	public ClientRegisterView(IJSONFileRepository repository) {
		this.repository = repository;
		setLayout(null);

		final JLabel warPath = new JLabel("");
		warPath.setFont(new Font("Tahoma", Font.PLAIN, 18));
		warPath.setBounds(195, 181, 332, 29);
		add(warPath);
		
		JLabel lblRegisterClient = new JLabel("Cadastro Cliente");
		lblRegisterClient.setBounds(145, 16, 239, 53);
		lblRegisterClient.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblRegisterClient);

		JLabel lblClientName = new JLabel("Nome *");
		lblClientName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblClientName.setBounds(15, 84, 87, 31);
		add(lblClientName);

		clientName = new JTextField();
		lblClientName.setLabelFor(clientName);
		clientName.setBounds(208, 85, 287, 26);
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
		buttonSave.setBounds(171, 443, 174, 44);
		add(buttonSave);

		JButton btnFind = new JButton("Buscar");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file = new JFileChooser(DEFAULT_WAR_OUTPUT_PATH);
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				file.setDialogTitle("Selecione o diretório...");
				file.showOpenDialog(null);
				warPath.setText(file.getSelectedFile().getAbsolutePath());
			}
		});
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnFind.setBounds(380, 134, 115, 29);
		add(btnFind);

		JLabel lbFolderPath = new JLabel("Pasta para Salvar WAR * ");
		lbFolderPath.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbFolderPath.setBounds(15, 131, 283, 31);
		add(lbFolderPath);
		
		JLabel lblCaminhoWar = new JLabel("Caminho WAR:");
		lblCaminhoWar.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCaminhoWar.setBounds(15, 179, 168, 31);
		add(lblCaminhoWar);
		


	}

	@Override
	public void save() {	
		MainJSONComponent json = repository.getJsonFile();
		if (validateData()) {
			json.getClient().add(client);
			repository.save(json);
			resetValues();
		}
	}
	
	public String getWarPath() {
		try {
			return file.getSelectedFile().getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public JPanel render() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean validateData() {
		MainJSONComponent json = new MainJSONComponent();
		client = new ClientJSONComponent(this.clientName.getText(), getWarPath());
		json.getClient().add(client);
		return validationsExecutor.execute(json,ValidationType.CLIENT_VALIDATION);
	}

	@Override
	public void resetValues() {
		// TODO Auto-generated method stub
		this.clientName.setText("");
		file = new JFileChooser(DEFAULT_WAR_OUTPUT_PATH);
	}
}
