package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.components.SystemJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.file.IJSONFileRepository;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.validations.enuns.ValidationType;
import br.com.torres.geradorDeVersaoMaven.view.components.DataTableModel;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;

@Component
@Qualifier("SystemRegisterView")
public class SystemRegisterView extends JPanel implements IPanelsRender {
	
	private final Integer PATH_COLUMN = 2;
	private final Integer MAVEN_PROFILES_COLUMN = 1;
	private final Integer NAME_COLUMN = 0;
	private final String DEFAULT_PATH = System.getProperty("user.home") + "\\Documents";
	
	private JFileChooser file;
	private JTextField sysName;
	private JTable table;

	private DataTableModel model;

	@Autowired
	private IJSONFileRepository repository;

	@Autowired
	private IValidationsExecutor validationsExecutor;

	private SystemJSONComponent system;

	private List<ProjectPathJSONComponent> projectPath;

	/**
	 * Create the panel.
	 */
	public SystemRegisterView() {
		int count = 1;
		setLayout(null);

		JLabel lblRegisterSystem = new JLabel("Cadastro Sistema");
		lblRegisterSystem.setBounds(145, 16, 239, 53);
		lblRegisterSystem.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblRegisterSystem);

		JLabel lblNome = new JLabel("Nome *");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNome.setBounds(33, 84, 87, 31);
		add(lblNome);

		sysName = new JTextField();
		sysName.setBounds(171, 85, 324, 26);
		add(sysName);
		sysName.setColumns(10);

		Button buttonSave = new Button("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateEditingColumn()) {
					save();
				}
			}
		});
		buttonSave.setFont(new Font("Dialog", Font.BOLD, 21));
		buttonSave.setBackground(Color.ORANGE);
		buttonSave.setBounds(171, 443, 174, 44);
		add(buttonSave);

		JLabel lblProjetos = new JLabel("Adicionar diretório de um ou mais projetos");
		lblProjetos.setToolTipText("");
		lblProjetos.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProjetos.setBounds(33, 120, 469, 46);
		add(lblProjetos);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 231, 469, 184);
		add(scrollPane);

		this.model = new DataTableModel();

		model.addColumn("Nome");
		model.addColumn("Caminho Projeto");
		model.addColumn("Executar Maven Profiles");

		model.addRow(new Object[] { "", "", "nao" });

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JButton btnAdicionarLinha = new JButton("+");
		btnAdicionarLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[] { "", "", "nao" });
			}
		});
		btnAdicionarLinha.setBounds(204, 195, 56, 29);
		add(btnAdicionarLinha);

		JButton btnAdicionarLinha_1 = new JButton("-");
		btnAdicionarLinha_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeRow(table.getSelectedRow());
			}
		});
		btnAdicionarLinha_1.setBounds(275, 195, 56, 29);
		add(btnAdicionarLinha_1);

		JLabel lblOrganizeASequencia = new JLabel("Organize a sequencia de excecução.");
		lblOrganizeASequencia.setToolTipText("");
		lblOrganizeASequencia.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblOrganizeASequencia.setBounds(129, 169, 269, 21);
		add(lblOrganizeASequencia);

		JButton btnAdicionarLinha_1_1 = new JButton("↑");
		btnAdicionarLinha_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow() - 1);
				table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
			}
		});
		btnAdicionarLinha_1_1.setBounds(340, 195, 67, 29);
		add(btnAdicionarLinha_1_1);

		JButton btnAdicionarLinha_1_1_1 = new JButton("↓");
		btnAdicionarLinha_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow() + 1);
				table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() + 1);
			}
		});
		btnAdicionarLinha_1_1_1.setBounds(415, 195, 67, 29);
		add(btnAdicionarLinha_1_1_1);
		
		JButton btnAddVarios = new JButton("Adicionar Vários");
		btnAddVarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file = new JFileChooser(DEFAULT_PATH);
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				file.setDialogTitle("Selecione o diretório...");
				file.showOpenDialog(null);
				File path = new File(file.getSelectedFile().getAbsolutePath());
				
				Arrays.asList(path.listFiles())
				.stream()
				.filter(f-> f.isDirectory())
				.forEach(p-> model.addRow(new Object[] { p.getName(), p.getPath(), "nao" }));
			
			}
		});
		btnAddVarios.setBounds(33, 195, 156, 29);
		add(btnAddVarios);

	}
	
	public boolean validateEditingColumn() {
		if(table.isEditing()) {
			JOptionPane.showMessageDialog(null, "A tabela possui uma célula que ainda está em edição. Pressione 'Enter' na mesma para finalizar a edição");
		}
		return !table.isEditing();
	}

	@Override
	public JPanel render() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean validateData() {
		// TODO Auto-generated method stub
		setProjectPathValues();
		MainJSONComponent json = new MainJSONComponent();
		system = new SystemJSONComponent(projectPath, null, sysName.getText());
		json.getSystem().add(system);
		return validationsExecutor.execute(json, ValidationType.SYSTEM_VALIDATION);
	}

	public void setProjectPathValues() {
		Integer sequence = 1;
		this.projectPath = new ArrayList<ProjectPathJSONComponent>();
		try {
			for (int row = 0; row < model.getRowCount(); row++) {
				this.projectPath.add(new ProjectPathJSONComponent(sequence, model.getValueAt(row, MAVEN_PROFILES_COLUMN).toString(),
						(model.getValueAt(row, PATH_COLUMN).toString()), model.getValueAt(row, NAME_COLUMN).toString()));
				sequence++;
			}
		} catch (Exception e) {
			System.out.println(e);
			this.projectPath.add(new ProjectPathJSONComponent(sequence, "", "nao", ""));
		}
	}

	@Override
	public void resetValues() {
		// TODO Auto-generated method stub
		sysName.setText("");
		model.removeAllModelRows();
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		MainJSONComponent json = repository.getJsonFile();
		if (validateData()) {
			json.getSystem().add(system);
			repository.save(json);
			resetValues();
		}
	}
}
