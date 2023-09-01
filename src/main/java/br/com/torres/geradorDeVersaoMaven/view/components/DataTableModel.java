package br.com.torres.geradorDeVersaoMaven.view.components;

import javax.swing.table.DefaultTableModel;

public class DataTableModel extends DefaultTableModel {
	
	public void removeAllModelRows() {
		while(this.getRowCount() > 0) {
			this.removeRow(0);
		}
	}
}
