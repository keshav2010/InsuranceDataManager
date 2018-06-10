package DisplayForms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JLabel;
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
	
	public JLabel label_fileName;
	
	//setup panel's layout, adds scrollpane and associated table
	public MainPanel() {
		
		this.setLayout(new GridBagLayout());
		sheetTable = new SheetTable();
		scrollPane = new JScrollPane(sheetTable);
		
		updateLayoutHandler(0,0, GridBagConstraints.HORIZONTAL, 0, 0);
		label_fileName = new JLabel("No active file found, create a new file (File > New) or open existing files. (File > Open)");
		label_fileName.setForeground(Color.red);
		this.add(label_fileName, layoutHandler);
		
		updateLayoutHandler(0,1, GridBagConstraints.BOTH, 1, 1); //update layoutHandler
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
	public void updateTableView() {
		sheetTable.updateUI();
	}
	
}
//end of class MainPanel

class SheetTable extends JTable{
	public TableModel tableModel;
	public SheetTable() {
		tableModel = new TableModel(); 
		this.setModel(tableModel);
		this.setToolTipText("View Sheet Record");
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setVisible(true);
	}
}
//end of class SheetTable

class TableModel extends AbstractTableModel
{
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
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		if(FileManager.getActiveFileName() == null)
			return 0;
		return FileManager.getActiveFileRecordsCount();
	}
	@Override
	public Object getValueAt(int row, int col) {
		if(col==0)
			return row+1;
		else if(col == 1) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).name;
		}
		else if(col == 2) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).policyNumber;
		}
		else if(col == 3) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).dateOfBirth.getDateString();
		}
		else if(col == 4) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).doc.getDateString();
		}
		else if(col == 5) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return String.valueOf(FileManager.getFileTreeMap().get(key).sum);
		}
		else if(col == 6) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).planAndTerm;
		}
		else if(col == 7) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).mode;
		}
		else if(col == 8) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).primium;
		}
		else if(col == 9) {
			String key = (String) FileManager.getFileTreeMap().keySet().toArray()[row];
			return FileManager.getFileTreeMap().get(key).nextDue;
		}
		return null;
	}
	@Override //set up column header names
	public String getColumnName(int col) {
	      return columnNames[col];
	}
}//end of class TableModel
