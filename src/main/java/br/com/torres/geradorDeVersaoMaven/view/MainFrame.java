package br.com.torres.geradorDeVersaoMaven.view;

import java.awt.HeadlessException;

import javax.inject.Inject;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.view.interfaces.IMainScreenRender;
import br.com.torres.geradorDeVersaoMaven.view.interfaces.IPanelsRender;

@Component
public class MainFrame extends JFrame implements IMainScreenRender {

	private JPanel contentPane;

	private IPanelsRender generateVersionView;
	private IPanelsRender clientRegisterView;
	private IPanelsRender clientEditView;
	private IPanelsRender systemRegisterView;
	private IPanelsRender systemEditView;
	private IPanelsRender mavenProfilesRegisteView;
	private IPanelsRender mavenProfilesEditView;

	
	
	@Autowired
	private MainFrame(IPanelsRender generateVersionView,
			@Qualifier("ClientRegisterView") IPanelsRender clientRegisterView,
			@Qualifier("ClientEditView") IPanelsRender clientEditView,
			@Qualifier("SystemRegisterView") IPanelsRender systemRegisterView,
			@Qualifier("SystemEditView") IPanelsRender systemEditView,
			@Qualifier("MavenProfilesRegisteView") IPanelsRender mavenProfilesRegisteView,
	        @Qualifier("MavenProfilesEditView") IPanelsRender mavenProfilesEditView) throws HeadlessException {
		setResizable(false);
		this.generateVersionView = generateVersionView;
		this.clientRegisterView = clientRegisterView;
		this.clientEditView = clientEditView;
		this.systemRegisterView = systemRegisterView;
		this.systemEditView = systemEditView;
		this.mavenProfilesRegisteView = mavenProfilesRegisteView;
		this.mavenProfilesEditView = mavenProfilesEditView;

		setVisible(true);
		setTitle("Gerador de Versão 1.1.0 - by Tiago Torres");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 559, 575);
		contentPane.add(tabbedPane);

		tabbedPane.addTab("Gerar Versão", null, generateVersionView.render(), null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Cliente", null, tabbedPane_1, null);
		

		tabbedPane_1.addTab("Cadastrar", null, clientRegisterView.render(), null);

		tabbedPane_1.addTab("Editar", null, clientEditView.render(), null);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(305, 197, 1, 1);

		clientEditView.add(desktopPane);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Sistema", null, tabbedPane_2, null);

		tabbedPane_2.addTab("Cadastrar", null, systemRegisterView.render(), null);

		tabbedPane_2.addTab("Editar", null, systemEditView.render(), null);
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Maven Profiles", null, tabbedPane_3, null);
		
		tabbedPane_3.addTab("Cadastrar", null, mavenProfilesRegisteView.render(), null);

		tabbedPane_3.addTab("Editar", null, mavenProfilesEditView.render(), null);
	}

}
