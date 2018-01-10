package Misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import UserDetail.User;

//Manages data of active file
public class FileManager {
	private String activeFile;
	private int fileRecordsCount;
	
	private FileInputStream fin;
	private FileOutputStream fout;
	
	private ObjectOutputStream ous;
	private ObjectInputStream ois;
	
	private File file;
	
	private Map<String, User> fileData=null; //data is stored in file as key-value pair, key is Policy Number and value is user-data
	
	public FileManager(String fileName) throws IOException {
		file = new File(fileName);
		file.createNewFile();
	}
	public String getActiveFileName() {
		return activeFile;
	}
	public int getActiveFileRecordsCount() {
		return fileRecordsCount;
	}
	public User getRecord(String policyNumber) {
		return fileData.get(policyNumber);
	}
	public boolean isRecordExist(User record) {
		String policyNumber = new String(record.policyNumber);
		return fileData.containsKey(policyNumber);
	}
	public void addRecord(User record) {
		if(isRecordExist(record))
			return;
		fileData.put(record.policyNumber, record);
		fileRecordsCount++;
	}
	public void deleteRecord(User record) {
		if(!isRecordExist(record))
			return;
		fileData.remove(record.policyNumber);
		fileRecordsCount--;
	}
}
