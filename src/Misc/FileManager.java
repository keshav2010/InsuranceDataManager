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
//instance of FileManager is responsible for handling read/write operation of single file
//multiple instances should be made for multiple files.
public class FileManager {
	private String activeFileName=null;
	private int fileRecordsCount=0;
	
	private FileInputStream fin;
	private FileOutputStream fout;
	
	private ObjectOutputStream ous;
	private ObjectInputStream ois;
	
	private File file;
	
	//data is stored in file as key-value pair, key is Policy Number and value is user-data
	private TreeMap<String, User> fileTreeMap=null; 
	
	/*
	 * current implementation is not fit for large amount of data
	 * reads in entire file content, updates it in-memory 
	 * and writes back entire content back to file.
	*/
	public FileManager(String fileName) throws IOException {
		System.out.println("FileManager init. for file : "+activeFileName);
		file = new File(fileName);
		if(!file.exists())
			file.createNewFile();
		
		//initialize streams
		fin = new FileInputStream(file);
		fout = new FileOutputStream(file);
		
		ois = new ObjectInputStream(fin);
		ous = new ObjectOutputStream(fout);
		
		//load fileTreeMap with the latest file content
		try {
			fileTreeMap = new TreeMap<String, User>((TreeMap<String, User>) ois.readObject());
			fileRecordsCount = fileTreeMap.size();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to update fileTreeMap in FileManager.java, constructor");
		}
		
	}
	
	@Override
	protected void finalize() {
		System.out.println("finalizing FileManager instance for file : " + activeFileName);
		try {
			ois.close();
			
			ous.writeObject(fileTreeMap);
			ous.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getActiveFileName() {
		return activeFileName;
	}
	public int getActiveFileRecordsCount() {
		fileRecordsCount = fileTreeMap.size();
		return fileRecordsCount;
	}
	public User getRecord(String policyNumber) {
		return fileTreeMap.get(policyNumber);
	}
	public boolean isRecordExist(User record) {
		String policyNumber = new String(record.policyNumber);
		return fileTreeMap.containsKey(policyNumber);
	}
	
	public void addRecord(User record) {
		if(isRecordExist(record))
			return;
		fileTreeMap.put(record.policyNumber, record);
		//ous.writeObject(fileTreeMap);
		fileRecordsCount++;
	}
	public void deleteRecord(User record) {
		if(!isRecordExist(record))
			return;
		fileTreeMap.remove(record.policyNumber);
		//ous.writeObject(fileTreeMap);
		fileRecordsCount--;
	}
	public void updateFileContent() throws IOException {
		ous.writeObject(fileTreeMap);
	}
}
