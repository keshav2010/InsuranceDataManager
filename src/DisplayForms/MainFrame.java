package DisplayForms;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import Misc.FileManager;
/*
 * Handles the working of MainFrame(inherits JFrame), and related menu bars
 * Also handles event through custom Event Manager Classes
 */
public class MainFrame extends JFrame{
	//These constant String values are defined here for safety and easy debugging, 
	//As these are passed in as parameter file constructing MenuItems as well as used by 
	//Event Manager class to find out source of event
	public final static String stringMenuNew = new String("New");
	public final static String stringMenuOpen = new String("Open");
	public final static String stringMenuSave = new String("Save");
	public final static String stringMenuExit = new String("Exit");
	public final static String stringMenuAddRecord = new String("Add");
	
	public FileChooser fileSelectDialog = null;
	
	public MainPanel mainPanel;
	private FrameEventManager eventManager; //handles all events generated by Frame or any component related to MainFrame core
	private static final long serialVersionUID = 1L;
	public MainFrame(String title) {
		mainPanel = new MainPanel();
		FileManager.mainPanel = mainPanel;
		this.add(mainPanel);
		eventManager = new FrameEventManager(this); //initialized with this as parameter, thus registering the instance to manager class
		this.setTitle(title);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setJMenuBar(createMenuBar());
		this.setMinimumSize(new Dimension(800,600));
		this.setVisible(true);
	}
	//Setup the MenuBar
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar(); 
		JMenu menu;
		JMenuItem menuItem;
		
		//----first menu---- 
		menu = new JMenu("File");
		
		//Adding items in First-Menu
		menuItem = new JMenuItem(stringMenuNew);
		menuItem.setToolTipText("Create new File");
		menuItem.addActionListener(eventManager);
		menu.add(menuItem);
		
		menuItem = new JMenuItem(stringMenuOpen);
		menuItem.setToolTipText("Open and View File");
		menuItem.addActionListener(eventManager); 
		menu.add(menuItem);
		
		menuItem = new JMenuItem(stringMenuSave);
		menuItem.setToolTipText("Save Existing File");
		menuItem.addActionListener(eventManager);
		menu.add(menuItem);
		
		menuItem = new JMenuItem(stringMenuExit);
		menuItem.setToolTipText("Quit Program");
		menuItem.addActionListener(eventManager);
		menu.add(menuItem);
		
		//Adding First-Menu to MenuBar
		menuBar.add(menu);
		
		//----second menu----
		menu = new JMenu("User");
		
		//Adding items in Second-Menu
		menuItem = new JMenuItem(stringMenuAddRecord);
		menuItem.setToolTipText("Add Record in Current Opened File");
		menuItem.addActionListener(eventManager);
		menu.add(menuItem);
		
		menuBar.add(menu);
		return menuBar;
	}
}

//FrameEventManager class handles event management code
class FrameEventManager implements ActionListener {
	
	private static MainFrame mainFrame;
	public FrameEventManager(MainFrame frame) {
		mainFrame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		if(item.getText().equals(MainFrame.stringMenuExit)) {
			mainFrame.dispose();
		}
		else if(item.getText().equals(MainFrame.stringMenuNew)) {
			System.out.println("Clicked New");
			FileMakerDialog fm = new FileMakerDialog(mainFrame);
		}
		else if(item.getText().equals(MainFrame.stringMenuOpen)) {
			System.out.println("Clicked Open");
			mainFrame.fileSelectDialog = new FileChooser();
			mainFrame.fileSelectDialog.showOpenDialog(mainFrame);
		}
		
		else if(item.getText().equals(MainFrame.stringMenuSave)) {
			System.out.println("Clicked SAVE");
		}
			
		else if(item.getText().equals(MainFrame.stringMenuAddRecord)) {
			AddRecordDialog g = null;
			if(FileManager.getActiveFileName() != null) {
				 g = new AddRecordDialog();//TODO: file name passed as argument
			}
			else {
				mainFrame.mainPanel.label_fileName.setText("Cannot Add New Record, Please select a file or create a new file");
			}
		}
	}
}

//FileChooser class handles the setup of dialog box that pops up when user attempts to save/open document files
//It extends JFileChooser and encapsulates the extra code that is commonly required, such as setting up title, and File filters
class FileChooser extends JFileChooser {

	private FileNameExtensionFilter filter; //to handle custom 
	private static final long serialVersionUID = 1L;
	public FileChooser() {
		filter = new FileNameExtensionFilter("Work Sheets", "lic", "xls");
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(filter);
	}
	
}