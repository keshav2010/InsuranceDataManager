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

import UserDetail.User;
//FileManager is responsible for handling read/write operation of a file
public class FileManager 
{
	private static String activeFileName=null;
	private static int fileRecordsCount=0;
	
	private static ObjectOutputStream ous;
	private static ObjectInputStream ois;
	
	private static File file;
	
	//data is stored in file as key-value pair, key is Policy Number and value is user-data
	private static TreeMap<String, User> fileTreeMap=null; 
	
	/*
	 * current implementation is not fit for large amount of data
	 * reads in entire file content, updates it in-memory 
	 * and writes back entire content back to file.
	*/
	public static String getActiveFileName() {
		return activeFileName;
	}
	public static void setActiveFileName(String filename) {
		activeFileName = new String(filename);
		file = new File(activeFileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("FileManager > setActiveFileName > error creating file");
				e.printStackTrace();
			}
		}
		else System.out.println("File already found");
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			fileTreeMap = new TreeMap<String, User> ( (TreeMap<String, User>) ois.readObject());
			ois.close();
			fileRecordsCount = fileTreeMap.size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty File");
			fileTreeMap = new TreeMap<String, User>();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("CLASSNOTFOUND");
			e.printStackTrace();
		}
		
	}
	public static int getActiveFileRecordsCount() {
		return fileRecordsCount;
	}
	public static User getRecord(String policyNumber) {
		return fileTreeMap.get(policyNumber);
	}
	public static boolean isRecordExist(User record) {
		String policyNumber = new String(record.policyNumber);
		if(fileTreeMap == null)
			return false;
		return fileTreeMap.containsKey(policyNumber);
	}
	
	public static void addRecord(User record) throws FileNotFoundException, IOException {
		if(isRecordExist(record)) {
			System.out.println("record already exist");
			return;
		}
		ous = new ObjectOutputStream(new FileOutputStream(file));
		fileTreeMap.put(record.policyNumber, record);
		ous.writeObject(fileTreeMap);
		ous.close();
		fileRecordsCount++;
	}
	public static void deleteRecord(User record) throws FileNotFoundException, IOException {
		if(!isRecordExist(record))
			return;
		fileTreeMap.remove(record.policyNumber);
		ous = new ObjectOutputStream(new FileOutputStream(file));
		ous.writeObject(fileTreeMap);
		ous.close();
		fileRecordsCount--;
	}
}
