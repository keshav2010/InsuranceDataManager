package DisplayForms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Misc.CustomDate;
//Add Record Dialog class, this dialog is poped up when user decides to add a new record to a file, 
//The dialog contains multiple fields for user to enter record's information and save it to file.
public class AddRecordDialog extends JDialog{
	
	public String activeFile;
	private JLabel fileName;//current active file in which data will be appended
	private JLabel fileRecordCount;// number of records that exists in file 
	private GridBagConstraints dialogLayoutHandler = new GridBagConstraints();
	
	private DialogEventManager dialogEventManager;
	
	public AddRecordDialog(String _fileName) {
		activeFile = new String(_fileName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Add New Record");
		this.setLayout(new GridBagLayout());
		this.setSize(500, 350);
		setupForm(); //sets up the form
		this.getContentPane().setBackground(Color.gray.brighter());
		this.setVisible(true);
	}
	//helper function
	private void updateLayoutHandler(int gridX, int gridY, int fill, double weightX, double weightY) {
		dialogLayoutHandler.gridx = gridX;
		dialogLayoutHandler.gridy = gridY;
		dialogLayoutHandler.weightx = weightX;
		dialogLayoutHandler.weighty = weightY;
		dialogLayoutHandler.fill = fill;
		
		//reset other parameters that might have been changed outside function call
		dialogLayoutHandler.gridheight=1;
		dialogLayoutHandler.gridwidth=1;
		dialogLayoutHandler.insets = new Insets(0,0,0,0);
	}
	//helper function that sets up the form structure where user can fill record data to be added
	private void setupForm() {
		//initializing Labels that displays fileName and Number of Records in file
		fileName = new JLabel("Data will be stored in file : "+ activeFile);
		int fileRecordsCount=0;
		try {
			FileInputStream fin = new FileInputStream(activeFile);
			//TODO : code for counting records from file required
		} catch (FileNotFoundException e) {
			//do nothing
		}
		fileRecordCount = new JLabel("Number of Records in current File : " + String.valueOf(fileRecordsCount));
		//
		
		JButton submitButton = new JButton("Add");
		
		String[] labels = {"Name", "Policy No.", "Phone No.",
				"D.O.B", "D.O.C", "Sum Assured", "Plan And Term",
				"Mode", "Primium", "Next Due"};
		
		for(int i=0; i<labels.length; i++) {
			if(labels[i].equals("D.O.B") || labels[i].equals("D.O.C")) {
				addDateField(labels[i], i);
			}
			else addTextField(labels[i], i);
		}		
		//setting up GridBagConstraints properties for submit-button in the form
		updateLayoutHandler(0, labels.length, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.gridwidth=2;
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		
		this.add(submitButton, dialogLayoutHandler);
		
		updateLayoutHandler(0, labels.length+1, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		this.add(fileName, dialogLayoutHandler);
		dialogLayoutHandler.gridx++;
		this.add(fileRecordCount, dialogLayoutHandler);
		
	}
	//helper function : adds a JLabel with "labelName" label and a JTextField on its right on specified rowNumber
	private void addTextField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 1, 1);
		dialogLayoutHandler.insets.left = 15; //some custom changes
		this.add(textLabel, dialogLayoutHandler);
		JTextField textData = new JTextField();
		textData.setEditable(true);
		
		updateLayoutHandler(1, rowNumber, GridBagConstraints.HORIZONTAL,1, 1);
		dialogLayoutHandler.insets.right = 15; //custom changes
		
		this.add(textData, dialogLayoutHandler);
	}
	
	//helper function : adds a custom dateField
	private void addDateField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 1, 1);
		dialogLayoutHandler.insets.left= 15;
		this.add(textLabel, dialogLayoutHandler);
		
		CustomDateField dateField = new CustomDateField();
		
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 1, 1);
		dialogLayoutHandler.insets.right= 15;
		dialogLayoutHandler.gridwidth=3;
		this.add(dateField, dialogLayoutHandler);
	}
	
}

//helper class, sets up a basic date field 
class CustomDateField  extends JComponent implements SwingConstants
{
	private CustomDate date;
	private JComboBox<String> monthBox;
	private JTextField dateField;
	private JTextField yearField;
	FlowLayout layout = new FlowLayout();
	public CustomDateField() {
		
		monthBox = new JComboBox<String>(CustomDate.getMonths());
		monthBox.setSelectedIndex(0);
		monthBox.setEditable(false);

		dateField = new JTextField("01");
		yearField = new JTextField("2018");
				
		layout.setHgap(2);
		layout.setAlignment(FlowLayout.TRAILING);
		this.setLayout(layout);
		
		this.add(dateField);
		this.add(monthBox);
		this.add(yearField);
		date = new CustomDate(dateField.getText(), monthBox.getSelectedItem().toString(), yearField.getText());
	}
	public CustomDate getDate() {
		return date;
	}
}

//NOTE : not implementing Comparable<Date>, 
//instead isEqualTo() is used for only testing equality and not comparing their relative values

// management of events is handled by this class
class DialogEventManager implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}