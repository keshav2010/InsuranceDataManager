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
	
	//indices of labels in String[] labels array
	public static final int NAME=0, POLICY_NO=1, PHONE_NO=2,
			DOB=3, DOC=4, SUM_ASSURED=5,
			PLAN_AND_TERM=6, MODE=7, PRIMIUM=8, NEXT_DUE=9;
	public static final String[] labels = {"NAME", "POLICY NO.", "PHONE NO.",
			"D.O.B", "D.O.C", "SUM ASSURED", "PLAN AND TERM",
			"MODE", "PRIMIUM", "NEXT DUE"};
	

	public JButton submitButton;
	public JTextField textField_Name, textField_PolicyNo, textField_PhoneNo,
		textField_SumAssured, textField_PlanAndTerm, textField_Mode, 
		textField_Primium, textField_NextDue;
	public CustomDateField dateField_DOB, dateField_DOC;
	public JComboBox comboBox_Mode;
	
	public String activeFile;
	private JLabel label_fileName;//current active file in which data will be appended
	private JLabel label_fileRecordCount;// number of records that exists in file 
	private GridBagConstraints dialogLayoutHandler = new GridBagConstraints();
	
	
	private DialogEventManager dialogEventManager;
	public AddRecordDialog(String _fileName) {
		dialogEventManager = new DialogEventManager(this);	
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
		label_fileName = new JLabel("Data will be stored in file : "+ activeFile);
		int fileRecordsCount=0;
		label_fileRecordCount = new JLabel("Number of Records in current File : " + String.valueOf(fileRecordsCount));
		//
		
		submitButton = new JButton("Add");
		submitButton.addActionListener(dialogEventManager);
		
		
		for(int i=0; i<labels.length; i++) {
			if(labels[i].equals("D.O.B") || labels[i].equals("D.O.C")) {
				addDateField(labels[i], i);
			}
			else if(labels[i].equals("MODE")) {
				addModeComboBox(labels[i], i);
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
		this.add(label_fileName, dialogLayoutHandler);
		dialogLayoutHandler.gridx++;
		this.add(label_fileRecordCount, dialogLayoutHandler);

	}
	//adds a comboBox for MODE field
	private void addModeComboBox(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		String[] modes = {"MTLY", "H/YRLY", "QRTLY", "YRLY"};
		JComboBox<String> comboBox = new JComboBox<String>(modes);
		
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		this.add(textLabel, dialogLayoutHandler);
		
		dialogLayoutHandler.gridx = 1;
		this.add(comboBox, dialogLayoutHandler);
	}
	//helper function : adds a JLabel with "labelName" label and a JTextField on its right on specified rowNumber
	private void addTextField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		JTextField textData = new JTextField();

		if(labelName.equals( labels[NAME] )) {
			textField_Name = textData;
		}
		else if(labelName.equals( labels[POLICY_NO] )) {
			textField_PolicyNo = textData;
		}
		else if(labelName.equals( labels[PHONE_NO])) {
			textField_PhoneNo = textData;
		}
		else if(labelName.equals( labels[SUM_ASSURED])) {
			textField_SumAssured = textData;
		}
		else if(labelName.equals( labels[PLAN_AND_TERM])) {
			textField_PlanAndTerm = textData;
		}
		else if(labelName.equals( labels[PRIMIUM])) {
			textField_Primium = textData;
		}
		else if(labelName.equals( labels[NEXT_DUE] )) {
			textField_NextDue = textData;
		}
		
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 1, 1);
		dialogLayoutHandler.insets.left = 15; //some custom changes
		this.add(textLabel, dialogLayoutHandler);

		textData.setEditable(true);
		textData.setName(labelName);

		dialogLayoutHandler.gridx=1;
		dialogLayoutHandler.insets.right = 15; //custom changes
		
		this.add(textData, dialogLayoutHandler);
	}
	
	//helper function : adds a custom dateField
	private void addDateField(String labelName, int rowNumber) {
		JLabel textLabel = new JLabel(labelName);
		updateLayoutHandler(0, rowNumber, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.insets.left= 15;
		this.add(textLabel, dialogLayoutHandler);
		
		CustomDateField dateField = new CustomDateField();
		dateField.setName(labelName);
		
		dialogLayoutHandler.gridx=1;
		dialogLayoutHandler.insets.right= 15;
		this.add(dateField, dialogLayoutHandler);
	}
	
}

//helper class, sets up a basic date field 
class CustomDateField  extends JComponent
{
	private CustomDate date;
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
	private AddRecordDialog dialog;
	
	public DialogEventManager(AddRecordDialog dialog) {
		this.dialog=dialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	private boolean isFormValid() {
		
		return true;
	}
};