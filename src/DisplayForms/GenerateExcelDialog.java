package DisplayForms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Misc.WorkbookManager;

//Dialog presents user with option to write existing file data to an excel file
public class GenerateExcelDialog extends JDialog{
	JTextField fld_workbookName;
	XSSFWorkbook workbook;
	JLabel lbl_workbookName;
	JButton btn_generateFile;
	EventManager eventManager;
	GridBagLayout layout;
	GridBagConstraints layoutHandler;
	public GenerateExcelDialog()
	{
		//init
		eventManager = new EventManager();
		btn_generateFile = new JButton("Convert");
		fld_workbookName = new JTextField();
		lbl_workbookName = new JLabel("Enter Workbook Name : ");
		layout = new GridBagLayout();
		layoutHandler = new GridBagConstraints();
		btn_generateFile.addActionListener(eventManager);
		this.setLayout(layout);
		setupUI();
		this.setTitle("Generate Excel Workbook");
		this.setMinimumSize(new Dimension(300,50));
		this.pack();
		this.setVisible(true);
	}
	private void setupUI() {
		updateLayoutHandler(0, 0, GridBagConstraints.HORIZONTAL, 0, 0);
		this.add(lbl_workbookName, layoutHandler);
		
		updateLayoutHandler(1, 0, GridBagConstraints.HORIZONTAL, 1, 0);
		this.add(fld_workbookName, layoutHandler);
		
		updateLayoutHandler(0, 1, GridBagConstraints.HORIZONTAL, 0, 0);
		this.add(btn_generateFile, layoutHandler);
	}
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
	
	//nested class, responsible for managing events for this specific dialog only
	class EventManager implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource().equals(btn_generateFile)) {
				System.out.println("Pressed Convert File");
				String fileName = new String(fld_workbookName.getText().trim());
				if(fileName.length()==0){
					new WarningDialog("Empty fields not allowed.");
					return;
				}
				else if(fileName.length() > 255) {
					new WarningDialog("Name too long. You can only enter upto 255 characters");
					return;
				}
				//generate excel sheet with data inside
				/*
				 * From this point, a utility class that is responsible for 
				 * generating workbooks, sheet should take over responsibility of generating excel workbook
				 * by reading the current active file's data structure, writing values to particular sheet
				 */
				try {
					WorkbookManager.generateWorkbook(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
}
