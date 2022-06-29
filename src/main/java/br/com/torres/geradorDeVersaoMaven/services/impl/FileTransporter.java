package br.com.torres.geradorDeVersaoMaven.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.torres.geradorDeVersaoMaven.JSON.components.ProjectPathJSONComponent;
import br.com.torres.geradorDeVersaoMaven.JSON.dto.DataToGenerateVersionDTO;
import br.com.torres.geradorDeVersaoMaven.log.ILogInfomations;
import br.com.torres.geradorDeVersaoMaven.services.IFileTransporter;
import br.com.torres.geradorDeVersaoMaven.validations.IValidationsExecutor;
import br.com.torres.geradorDeVersaoMaven.view.ProcessView;

@Component
public class FileTransporter implements IFileTransporter {

	private IValidationsExecutor validationExecutor;

	private ILogInfomations log;

	private List<File> warFiles;

	private String mainFolder = "";

	private File newFolder;

	private ProcessView dialog;

	HashMap<String, String> fila = new HashMap<>();

	@Autowired
	public FileTransporter(IValidationsExecutor validationExecutor, ILogInfomations log) {
		this.validationExecutor = validationExecutor;
		this.log = log;
	}

	@Override
	public void transport(ProcessView dialog, DataToGenerateVersionDTO data) {
		this.dialog = dialog;
		warFiles = new ArrayList<>();

		for (ProjectPathJSONComponent path : data.getSystem().getProjectPath()) {
			getWarFiles(path.getPath());
		}
		createNewFolder(data);
		for (File warFile : warFiles) {
			dialog.updateSpecificTextOnScreen(fila);
			copyFile(warFile);
		}

	}

	public void copyFile(File warFile) {
		try {
			InputStream is = new FileInputStream(warFile.getAbsoluteFile());
			OutputStream os = new FileOutputStream(
					new File(newFolder.getAbsoluteFile().toString().concat("\\").concat(warFile.getName())));
			Integer totalFileSize = is.available();
			byte[] buf = new byte[1024];
			fila.put(warFile.getName(), "0");
			int bytesRead;
			while ((bytesRead = is.read(buf)) > 0) {
				os.write(buf, 0, bytesRead);
				fila.replace(warFile.getName(), getStringMessagePorcent(warFile.getName()));
			}
			is.close();
			os.close();
		} catch (Exception e) {

		}
	}

	public String getStringMessagePorcent(String name) {
		return "Transferindo arquivo ".concat(name);
	}

	public void createNewFolder(DataToGenerateVersionDTO data) {
		try {
			String newPath = data.getClient().getOutputWarPath().concat("\\").concat(data.getSystem().getName())
					.concat("\\").concat(LocalDate.now().toString());
			newFolder = new File(newPath);
			newFolder.mkdirs();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível criar pasta da data no diretório de WAR cadastrado para o sistema selecionado."
							+ e);
		}
	}

	public void getWarFiles(String path) {
		File folder = new File(path);
		File[] filesOfFolder = folder.listFiles();
		for (File file : filesOfFolder) {

			if (new File(file.getAbsoluteFile().toString()).isDirectory()) {
				for (File target : file.listFiles()) {

					if (target.getName().contains("war")) {
						if (target.getParent().equals(mainFolder)
								|| new File(target.getParent()).getParent().equals(mainFolder)) {
							warFiles.add(new File(target.getAbsoluteFile().toString()));
						}
					}
				}
			}

			if (file.getName().contains("war")) {
				warFiles.add(new File(file.getAbsolutePath().toString()));
			} else {
				File testFile = new File(file.getAbsoluteFile().toString().concat("\\target"));
				if (testFile.exists()) {
					getWarFiles(testFile.getAbsolutePath().toString());
				}
			}
		}

	}

}
