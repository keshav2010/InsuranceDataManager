package Misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

import DisplayForms.MainPanel;
import UserDetail.User;
//FileManager is responsible for handling read/write operation of a file
/**
 * Utility class that is an all-static methods and state class and <b>must not be instantiated.</b><br>
 * Responsible for reading/writing user-entered data from/to binary file.<br>
 * @author KESHAV SHARMA
 */
public final class FileManager 
{
	private static String activeFileName=null;
	private static int fileRecordsCount=0;
	
	private static ObjectOutputStream ous;
	private static ObjectInputStream ois;
	
	private static File file;
	
	public static MainPanel mainPanel=null;
	//data is stored in file as key-value pair, key is Policy Number and value is user-data
	private static TreeMap<String, User> fileTreeMap=null; 
	
	/*
	 * current implementation is not fit for large amount of data
	 * reads in entire file content, updates it in-memory 
	 * and writes back entire content back to file.
	*/
	public static TreeMap<String, User> getFileTreeMap() throws RuntimeException
	{
		if(getActiveFileRecordsCount() == 0)
			throw new RuntimeException("Empty File, Exception Thrown : getFileTreeMap");
		return fileTreeMap;
	}
	
	public static String getActiveFileName() {
		return activeFileName;
	}
	public static void setActiveFileName(String filename) throws FileNotFoundException {
		if(filename.trim().length()==0)
			throw new FileNotFoundException("Invalid file name");
		
		activeFileName = new String(filename);
		
		file = new File(activeFileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("Created new file : "+filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("FileManager > setActiveFileName > error creating file");
				e.printStackTrace();
			}
		}
		else System.out.println("File already found");
		
		//read content of file and put in memory
		try {
			System.out.println("Reading content and updating treemap");
			ois = new ObjectInputStream(new FileInputStream(file));
			System.out.println("opened stream");
			if(fileTreeMap != null) {
				fileTreeMap.clear();
				fileTreeMap = null;
				fileRecordsCount=0;
			}
			fileTreeMap = new TreeMap<String, User> ( (TreeMap<String, User>) ois.readObject());
			ois.close();
			System.out.println("closed stream, updated TreeMap");
			fileRecordsCount = fileTreeMap.size();
		} catch (IOException e) {
			//might enter here if file is empty
			System.out.println("Empty File > IOException > setActiveFileName > reading content of file");
			fileTreeMap = new TreeMap<String, User>();
			fileRecordsCount=0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("CLASSNOTFOUND");
			e.printStackTrace();
		}
		if(mainPanel != null)
			mainPanel.updateTableView();
		
	}
	public static int getActiveFileRecordsCount() {
		return fileRecordsCount;
	}
	public static User getRecord(String policyNumber) throws RuntimeException{
		if(getActiveFileRecordsCount() == 0)
			throw new RuntimeException("Empty File, Exception Thrown : getRecord()");
		return fileTreeMap.get(policyNumber);
	}
	public static boolean isRecordExist(User record) throws RuntimeException {
		if(getActiveFileRecordsCount() == 0)
			throw new RuntimeException("Empty File, Exception Thrown : isRecordExist()");
		
		String policyNumber = new String(record.policyNumber);
		if(fileTreeMap == null)
			return false;
		if(mainPanel != null)
			mainPanel.updateTableView();
		return fileTreeMap.containsKey(policyNumber);
		
	}
	
	public static void addRecord(User record) throws FileNotFoundException, IOException {
		try{
			if(isRecordExist(record)) {
			System.out.println("record already exist");
			return;
			}
		}catch(RuntimeException e) {
			//enter here if current file is empty
			System.out.println("addRecord > RuntimeException > file empty , adding 1st record");
		}
		ous = new ObjectOutputStream(new FileOutputStream(file));
		fileTreeMap.put(record.policyNumber, record);
		ous.writeObject(fileTreeMap);
		ous.close();
		fileRecordsCount++;
	}
	public static void deleteRecord(User record) throws FileNotFoundException, IOException, RuntimeException {
		if(getActiveFileRecordsCount() == 0)
			throw new RuntimeException("Empty File, Exception Thrown : deleteRecord");
		
		if(!isRecordExist(record))
			return;
		fileTreeMap.remove(record.policyNumber);
		ous = new ObjectOutputStream(new FileOutputStream(file));
		ous.writeObject(fileTreeMap);
		ous.close();
		fileRecordsCount--;
	}
}
