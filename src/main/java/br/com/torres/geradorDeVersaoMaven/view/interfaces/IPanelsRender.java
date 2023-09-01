package br.com.torres.geradorDeVersaoMaven.view.interfaces;

import java.awt.Component;

import javax.swing.JPanel;

public interface IPanelsRender {

	public JPanel render();
	
	public Component add(Component comp);
	
	public boolean validateData();
	
	public void resetValues();
	
	public void save();
}
