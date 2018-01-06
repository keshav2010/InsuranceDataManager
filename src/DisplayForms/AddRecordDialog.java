package DisplayForms;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
//Add Record Dialog class, this dialog is poped up when user decides to add a new record to a file, 
//The dialog contains multiple fields for user to enter record's information and save it to file.
public class AddRecordDialog extends JDialog{
	
	private GridBagConstraints dialogLayoutHandler = new GridBagConstraints();
	public AddRecordDialog() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Add New Record");
		this.setLayout(new GridBagLayout());
		this.setSize(350, 350);
		setupForm(); //sets up the form
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
		JButton submitButton = new JButton("Add");
		
		String[] labels = {"Name", "Policy No.", "Phone No.",
				"D.O.B", "D.O.C", "Sum Assured", "Plan And Term",
				"Mode", "Primium", "Next Due"};
		
		for(int i=0; i<labels.length; i++)
			addTextField(labels[i], i);
		
		//setting up GridBagConstraints properties for submit-button in the form
		updateLayoutHandler(0, labels.length, GridBagConstraints.HORIZONTAL, 0, 0);
		dialogLayoutHandler.gridwidth=2;
		dialogLayoutHandler.insets.left = dialogLayoutHandler.insets.right = 15;
		
		this.add(submitButton, dialogLayoutHandler);
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
}

//helper class, sets up a basic date field 
class CustomDateField  extends Component// DD- MM - YYYY (DateFormat Predominant in India)
{
	private Date date;
	private JComboBox monthBox;
	private JTextField dateField;
	private JTextField yearField;
	public CustomDateField() {
		
	}
}

//NOTE : not implementing Comparable<Date>, 
//instead isEqualTo() is used for only testing equality and not comparing their relative values
class Date{
	private String day, month, year;
	
	public Date(String day, String month, String year){
		this.day= new String(day);
		this.month = new String(month);
		this.year = new String(year);
	}
	public String getDateString() {
		return new String(day + month + year);
	}
	public void setDate(String day, String month, String year) {
		this.day = new String(day);
		this.month = new String(month);
		this.year = new String(year);
	}
	
	public boolean isEqualTo(Date other) {
		return this.day.equals(other.day) && this.month.equals(other.month) && this.year.equals(other.year);	
	}
	
}