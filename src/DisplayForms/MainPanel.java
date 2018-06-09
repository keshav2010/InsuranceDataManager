package DisplayForms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import Misc.FileManager;
//sets up the panel that includes a SrollPane along with a JTable object, 
//MainPanel will also include several additional components such as checkboxes etc
public class MainPanel extends JPanel {

	public GridBagConstraints layoutHandler = new GridBagConstraints();
	public SheetTable sheetTable;
	private JScrollPane scrollPane;
	
	//setup panel's layout, adds scrollpane and associated table
	public MainPanel() {
		
		this.setLayout(new GridBagLayout());
		sheetTable = new SheetTable();
		scrollPane = new JScrollPane(sheetTable);
		updateLayoutHandler(0,0, GridBagConstraints.BOTH, 1, 1); //update layoutHandler
		this.add(scrollPane, layoutHandler); //add scrollPane that includes Table in it
		this.setVisible(true);
	}
	
	//helper function
	private void updateLayoutHandler(int gridX, int gridY, int fill, double weightX, double weightY) {
		layoutHandler.gridx = gridX;
		layoutHandler.gridy = gridY;
		layoutHandler.weightx = weightX;
		layoutHandler.weighty = weightY;
		layoutHandler.fill = fill;
	}
	
}//end of class MainPanel

class SheetTable extends JTable{
	public TableModel tableModel = new TableModel(); 
	public SheetTable() {
		this.setModel(tableModel);
		this.setToolTipText("View Sheet Record");
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setVisible(true);
	}
}//end of class SheetTable

class TableModel extends AbstractTableModel{
	public String activeFileName=null;
	FileManager fileManager;
	private static final long serialVersionUID = 1L;
	private static final String[] columnNames = 
		{
				"S.NO",
				"NAME",
				"POLICY NO.",
				"D.O.B",
				"D.O.C",
				"SUM ASSURED",
				"PLAN & TERM",
				"MODE",
				"PRIMIUM",
				"NEXT DUE"
		};
	public TableModel() {
		activeFileName = null;
	}
	public TableModel(String _fileName) {
		activeFileName = new String(_fileName);
		try {
			fileManager = new FileManager(activeFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TableModel : issue with init. fileManager");
			System.exit(1);
		}
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		if(activeFileName == null)
			return 0;
		return fileManager.getActiveFileRecordsCount();
	}
	@Override
	public Object getValueAt(int row, int col) {
		return null;
	}
	@Override //set up column header names
	public String getColumnName(int col) {
	      return columnNames[col];
	}
}//end of class TableModel
