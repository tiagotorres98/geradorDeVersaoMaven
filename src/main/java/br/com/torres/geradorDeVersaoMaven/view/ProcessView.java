package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.services.IVersionGenerator;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IDialogRender;
import lombok.Getter;

@Component
@Getter
public class ProcessView extends JDialog implements IDialogRender {

	private final JPanel contentPanel = new JPanel();

	private JTextArea textArea;

	private DataToGenerateVersionDTO data;

	private IVersionGenerator generator;

	private JLabel lblSistemaValue;

	private JLabel lblProjetoValue;

	private JLabel lblProfileValue;

	private JLabel lblClientValue;
	
	private Thread process;
	
	private Boolean killThread = new Boolean(false);
	
	@Autowired
	public ProcessView(IVersionGenerator generator) {
		this.generator = generator;
		setTitle("Realizando Processamento - Gerando Versão...");
		setModal(true);
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 681, 496);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 675, 20);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);

		JLabel lblSistema = new JLabel("Sistema:");
		lblSistema.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblSistema.setBounds(10, 16, 87, 28);
		getContentPane().add(lblSistema);

		lblSistemaValue = new JLabel(".");
		lblSistemaValue.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblSistemaValue.setBounds(108, 16, 552, 28);
		getContentPane().add(lblSistemaValue);

		JLabel lblProjeto = new JLabel("Projeto:");
		lblProjeto.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblProjeto.setBounds(10, 46, 87, 28);
		getContentPane().add(lblProjeto);

		lblProjetoValue = new JLabel(".");
		lblProjetoValue.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblProjetoValue.setBounds(108, 46, 562, 28);
		getContentPane().add(lblProjetoValue);

		JLabel lblProfile = new JLabel("Profile:");
		lblProfile.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblProfile.setBounds(10, 76, 87, 28);
		getContentPane().add(lblProfile);

		lblProfileValue = new JLabel(".");
		lblProfileValue.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblProfileValue.setBounds(108, 76, 552, 28);
		getContentPane().add(lblProfileValue);

		JLabel lblLog = new JLabel("Log");
		lblLog.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblLog.setBounds(10, 151, 36, 28);
		getContentPane().add(lblLog);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 650, 256);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel lblClient = new JLabel("Cliente");
		lblClient.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblClient.setBounds(10, 107, 69, 28);
		getContentPane().add(lblClient);
		
		lblClientValue = new JLabel(".");
		lblClientValue.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblClientValue.setBounds(108, 107, 552, 28);
		getContentPane().add(lblClientValue);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	closeWindow();
		    }
		});
	}

	public void closeWindow() {
		if(JOptionPane.showConfirmDialog(this, "Você deseja finalizar o processo?", "Finalizar o Processo?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				killThread = new Boolean(true);
				this.dispose();
		}
	}

	@Async
	@Override
	public JDialog render(DataToGenerateVersionDTO data) {
		// TODO Auto-generated method stub
		this.data = data;
		process = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				executeGeneration();
			}
		});
		process.start();
		setVisible(true);
		return this;
	}

	public void executeGeneration() {
		generator.generate(this, data);
	}
	
	public void updateSpecificTextOnScreen(HashMap<String,String> listString) {
		textArea.setText("");
		for(String s:listString.values()) {
			textArea.append(s.concat("\n"));
		}
	}

}
