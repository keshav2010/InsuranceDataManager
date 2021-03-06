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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import Misc.FileManager;
import UserDetail.User;
//Add Record Dialog class, this dialog is poped up when user decides to add a new record to a file, 
//The dialog contains multiple fields for user to enter record's information and save it to file.
public class AddRecordDialog extends JDialog{
	
	//indices of labels in String[] labels array
	public static final int NAME=0, POLICY_NO=1, PHONE_NO=2,
			DOB=3, DOC=4, SUM_ASSURED=5,
			PLAN_AND_TERM=6, MODE=7, PRIMIUM=8, NEXT_DUE=9, ADDRESS = 10;
	public static final String[] labels = {"NAME", "POLICY NO.", "PHONE NO.",
			"D.O.B", "D.O.C", "SUM ASSURED", "PLAN AND TERM",
			"MODE", "PRIMIUM", "NEXT DUE", "ADDRESS"};
	
	public HashMap<String, JTextField> textFields; 
	public JButton submitButton;
	public CustomDateField dateField_DOB, dateField_DOC;
	public JComboBox comboBox_Mode;
	
	private JLabel label_fileName;//current active file in which data will be appended
	private JLabel label_fileRecordCount;// number of records that exists in file 
	private GridBagConstraints dialogLayoutHandler = new GridBagConstraints();
	
	
	private DialogEventManager dialogEventManager;
	public AddRecordDialog() {
		dialogEventManager = new DialogEventManager(this);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Add New Record");
		this.setLayout(new GridBagLayout());
		this.setSize(500, 350);
		setupForm(); //sets up the form
		this.getContentPane().setBackground(Color.gray.brighter());
		this.setVisible(true);
	}
	
	
	public User getNewUser() {
		User userRecord = new User();
		for(int i=0; i<AddRecordDialog.labels.length; i++)
		{
			String labelText = new String(AddRecordDialog.labels[i]);
			switch(labelText.toUpperCase()) {
				case "NAME": userRecord.name = new String(textFields.get("NAME").getText().trim().toUpperCase());
				break;
				case "POLICY NO.": userRecord.policyNumber = new String(textFields.get("POLICY NO.").getText().trim());
				break;
				case "PHONE NO.": userRecord.mobileNumber = new String(textFields.get("PHONE NO.").getText().trim());
				break;
				case "D.O.B": userRecord.dateOfBirth = new CustomDate(dateField_DOB.getDate());
				break;
				case "D.O.C": userRecord.doc = new CustomDate(dateField_DOC.getDate());
				break;
				case "SUM ASSURED": userRecord.sum = Double.parseDouble(textFields.get("SUM ASSURED").getText());
				break;
				case "PLAN AND TERM": userRecord.planAndTerm = new String(textFields.get("PLAN AND TERM").getText().trim());
				break;
				case "MODE": userRecord.mode = new String(comboBox_Mode.getModel().getSelectedItem().toString());
				break;
				case "PRIMIUM": userRecord.primium = Double.parseDouble(textFields.get("PRIMIUM").getText());
				break;
				case "NEXT DUE": userRecord.nextDue = new String(textFields.get("NEXT DUE").getText());
				break;
				case "ADDRESS" : userRecord.address = new String(textFields.get("ADDRESS").getText().trim());
				break;
				default: return null;
			}
			
		}
		System.out.println(userRecord);
		return userRecord;
	}
	
	//tests for empty fields only
	public boolean isFormValid() {
		for(int i=0; i<AddRecordDialog.labels.length; i++) {
			if(AddRecordDialog.labels[i].equals("D.O.B")) {
				continue;
			}
			else if(AddRecordDialog.labels[i].equals("D.O.C")) {
				continue;
			}
			else if(AddRecordDialog.labels[i].equals("MODE")) {
				continue;
			}
			JTextField temp = this.textFields.get(AddRecordDialog.labels[i]);
			if(temp.getText().trim().length()==0)
				return false;
		}
		return true;
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
	//sets up the form structure where user can fill record data to be added
	
	public void updateVisibleInfo() {
		if(label_fileName == null)
			label_fileName = new JLabel();
		if(FileManager.getActiveFileName() == null)
			label_fileName = new JLabel("Data won't be saved, no active file");
		else label_fileName = new JLabel("Data will be stored in file : " + FileManager.getActiveFileName());
		
		if(label_fileRecordCount==null)
			label_fileRecordCount = new JLabel();
		label_fileRecordCount.setText("Number of Records in current File : " + String.valueOf(FileManager.getActiveFileRecordsCount()));
	}
	private void setupForm() {
		//initializing Labels that displays fileName and Number of Records in file
		updateVisibleInfo();
		textFields = new HashMap<String, JTextField>();
		submitButton = new JButton("Add");
		submitButton.addActionListener(dialogEventManager);
		
		for(int i=0; i<labels.length; i++) {
			
			if(labels[i].equals("D.O.B"))
				dateField_DOB = addDateField(labels[i], i);
			else if(labels[i].equals("D.O.C"))
				dateField_DOC = addDateField(labels[i], i);
			else if(labels[i].equals("MODE"))
				comboBox_Mode = addModeComboBox(labels[i], i);
			else textFields.putIfAbsent(labels[i], addTextField(labels[i],i));
			
		}		
		//setting up GridBagConstraints properties for submit-button in the form
		updateLayoutHandler(0, labels.length, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.gridwidth=2;
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		
		this.add(submitButton, dialogLayoutHandler);
		
		updateLayoutHandler(0, labels.length+1, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		this.add(label_fileName, dialogLayoutHandler);
		dialogLayoutHandler.gridx++;
		this.add(label_fileRecordCount, dialogLayoutHandler);

	}
	//adds a comboBox for MODE field
	private JComboBox<String> addModeComboBox(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		String[] modes = {"MTLY", "H/YRLY", "QRTLY", "YRLY"};
		JComboBox<String> comboBox = new JComboBox<String>(modes);
		
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		this.add(textLabel, dialogLayoutHandler);
		
		dialogLayoutHandler.gridx = 1;
		this.add(comboBox, dialogLayoutHandler);
		
		return comboBox;
	}
	//helper function : adds a JLabel with "labelName" label and a JTextField on its right on specified rowNumber
	private JTextField addTextField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		JTextField textData = new JTextField();

		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 1, 1);
		dialogLayoutHandler.insets.left = 15; //some custom changes
		this.add(textLabel, dialogLayoutHandler);

		textData.setEditable(true);
		textData.setName(labelName);

		dialogLayoutHandler.gridx=1;
		dialogLayoutHandler.insets.right = 15; //custom changes
		
		this.add(textData, dialogLayoutHandler);
		return textData;
	}
	
	//helper function : adds a custom dateField
	private CustomDateField addDateField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left= 15;
		this.add(textLabel, dialogLayoutHandler);
		
		CustomDateField dateField = new CustomDateField();
		dateField.setName(labelName);
		
		dialogLayoutHandler.gridx=1;
		dialogLayoutHandler.insets.right= 15;
		this.add(dateField, dialogLayoutHandler);
		
		return dateField;
	}
	
}

//helper class, sets up a basic date field 
class CustomDateField  extends JComponent
{
	CustomDate date;
	private JComboBox<String> monthBox;
	private JTextField dateField;
	private JTextField yearField;
	GridLayout layout = new GridLayout();
	public CustomDateField() {
		
		monthBox = new JComboBox<String>(CustomDate.getMonths());
		monthBox.setSelectedIndex(0);
		monthBox.setEditable(false);

		dateField = new JTextField("01");
		yearField = new JTextField("2018");
				
		layout.setHgap(10);
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
	private AddRecordDialog addRecordDialog;
	
	public DialogEventManager(AddRecordDialog dialog) {
		this.addRecordDialog=dialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//addNewRecord Button
		if(e.getSource().equals(addRecordDialog.submitButton)) {
			System.out.println("Submit Pressed");
			if(addRecordDialog.isFormValid()){
				System.out.println("Form Valid");
				//construct object and store it in file
				try {
					System.out.println("adding record");
					FileManager.addRecord(addRecordDialog.getNewUser());
					addRecordDialog.updateVisibleInfo();
				} catch (IOException e1) {
					System.out.println("Failed to add Record to file , exption raised in DialogEventManager");
					e1.printStackTrace();
					
				}
			}
			else System.out.println("invalid form");
		}
	}	
};