package DisplayForms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Misc.FileManager;

public class FileMakerDialog extends JDialog{
	public static final String[] labels = {"FILE NAME"};
	public HashMap<String, JTextField> textFields;
	public JButton btn_createFile;
	public GridBagLayout layout;
	public EventManager eventManager;
	public GridBagConstraints c;
	public MainFrame mainFrame;
	public FileMakerDialog(MainFrame _mainFrame)
	{
		this.mainFrame = _mainFrame;
		eventManager = new EventManager(this);
		textFields = new HashMap<String, JTextField>();
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		btn_createFile = new JButton("Create File");
		for(int i=0; i<FileMakerDialog.labels.length; i++)
		{
			textFields.put(FileMakerDialog.labels[i], new JTextField());
		}
		this.setLayout(layout);
		this.setTitle("Create New File");
		this.setSize(400, 100);
		this.getContentPane().setBackground(Color.gray.brighter());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setupForm();
		this.setVisible(true);
	}
	private void setupForm()
	{
		for(int i=0; i<FileMakerDialog.labels.length; i++)
		{
			if(FileMakerDialog.labels[i] == "FILE NAME")
			{
				
				updateLayoutHandler(0, i, GridBagConstraints.HORIZONTAL, 0, 0);
				c.insets.left=c.insets.right= 15;
				this.add(new JLabel("FILE NAME"), c);
				
				updateLayoutHandler(1, i, GridBagConstraints.HORIZONTAL, 1, 0);
				c.insets.left=c.insets.right= 15;
				this.add(textFields.get(labels[i]), c);
			}
		}
		updateLayoutHandler(0, 1, GridBagConstraints.HORIZONTAL, 1, 0);
		c.insets.left=c.insets.right= 15;
		btn_createFile.addActionListener(eventManager);
		this.add(btn_createFile, c);
	}
	private void updateLayoutHandler(int gridX, int gridY, int fill, double weightX, double weightY) {
		c.gridx = gridX;
		c.gridy = gridY;
		c.weightx = weightX;
		c.weighty = weightY;
		c.fill = fill;
		
		//reset other parameters that might have been changed outside function call
		c.gridheight=1;
		c.gridwidth=1;
		c.insets = new Insets(0,0,0,0);
	}
}
class EventManager implements ActionListener
{
	private FileMakerDialog fileMakerDialog;
	public EventManager(FileMakerDialog d)
	{
		fileMakerDialog = d;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(fileMakerDialog.btn_createFile))
		{
			String text=fileMakerDialog.textFields.get(FileMakerDialog.labels[0]).getText().trim();
			try {
				FileManager.setActiveFileName(new String(text));
				fileMakerDialog.mainFrame.mainPanel.label_fileName.setForeground(Color.black);
				fileMakerDialog.mainFrame.mainPanel.label_fileName.setText("Current Active File : "+FileManager.getActiveFileName());
			} catch (FileNotFoundException e1) {
				//enter here if file name is invalid
				System.out.println(e1.getMessage());
			}
		}
		fileMakerDialog.dispose();
	}
	
}
