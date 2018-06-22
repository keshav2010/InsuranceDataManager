package DisplayForms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
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
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public GridBagConstraints layoutHandler = new GridBagConstraints();
	public SheetTable sheetTable;
	private JScrollPane scrollPane;
	
	public JLabel label_fileName;
	
	private JButton btn_exportToExcel;
	private PanelEventManager panelEventManager;
	
	//setup panel's layout, adds scrollpane and associated table
	public MainPanel() {
		panelEventManager = new PanelEventManager();
		
		btn_exportToExcel = new JButton("Generate Excel");
		btn_exportToExcel.setForeground(Color.green);
		btn_exportToExcel.setBackground(Color.black);
		
		this.setLayout(new GridBagLayout());
		sheetTable = new SheetTable();
		scrollPane = new JScrollPane(sheetTable);
		
		updateLayoutHandler(0,0, GridBagConstraints.HORIZONTAL, 0, 0);
		label_fileName = new JLabel("No active file found, create a new file (File > New) or open existing files. (File > Open)");
		label_fileName.setForeground(Color.red);
		this.add(label_fileName, layoutHandler);
		
		updateLayoutHandler(0,1, GridBagConstraints.BOTH, 1, 1); //update layoutHandler
		this.add(scrollPane, layoutHandler); //add scrollPane that includes Table in it
		
		updateLayoutHandler(1 ,2, GridBagConstraints.HORIZONTAL, 0, 0);
		layoutHandler.insets = new Insets(5,5,5,5);
		this.add(btn_exportToExcel, layoutHandler);
		btn_exportToExcel.addActionListener(panelEventManager);
		this.setVisible(true);
	}
	
	//helper function
	private void updateLayoutHandler(int gridX, int gridY, int fill, double weightX, double weightY) {
		layoutHandler.gridx = gridX;
		layoutHandler.gridy = gridY;
		layoutHandler.weightx = weightX;
		layoutHandler.weighty = weightY;
		layoutHandler.gridheight=1;
		layoutHandler.gridwidth=1;
		layoutHandler.insets.bottom = layoutHandler.insets.top = layoutHandler.insets.left = layoutHandler.insets.right = 0;
		layoutHandler.fill = fill;
	}
	
	public void updateTableView() {
		sheetTable.updateUI();
	}
	
	class PanelEventManager implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btn_exportToExcel)) {
				new GenerateExcelDialog();
			}
		}
	}
}


@SuppressWarnings("serial")
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
		if(FileManager.getActiveFileName() != null) {		
			return FileManager.getActiveFileRecordsCount();
		}
		return 0;
	}
	@Override
	public Object getValueAt(int row, int col) {
		try 
		{	
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
		}
		catch(RuntimeException e) {
			return null;
		}
		return null;
	}
	@Override //set up column header names
	public String getColumnName(int col) {
	      return columnNames[col];
	}
}//end of class TableModel
