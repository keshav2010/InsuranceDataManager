package Misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DisplayForms.WarningDialog;
import UserDetail.User;

/**
 * This class is an all-static class that must not be instantiated.
 * Responsible for generating Excel Workbooks and establishing communication with
 * FileManager to read/write current active file's data
 * Using active file's data, generate an excel worksheet.
 * This is acting as a bride between the generated excel workbook and the active file's data structure 
 * that is currently in the working memory.
 */
public final class WorkbookManager {
	/**
	 * Throws IOException if workbookName is too long or empty.
	 * @param workbookName <br><i>(without extension, if in case extension is provided, for example abc.dat will be converted to abc.xlsx)</br></i>
	 * @throws IOException
	 */
	private static final String sheetColumnHeader[] = {"S.No", "Name", "Address", "Policy No.", 
			"D.O.B", "D.O.C", "Sum Assured", 
			"Plan & Term", "Mode", "Primium", 
			"Next Due"};
	
	public static void generateWorkbook(String workbookName) throws IOException {
		workbookName = new String(workbookName.trim());
		if(workbookName.length() == 0) {
			throw new IOException("WorkbookManager : Empty workbook Name");
		}else if(workbookName.length() > 255) {
			throw new IOException("WorkbookManager :workbook name very long.");
		}
		if(workbookName.contains(".")) {
			workbookName = new String(workbookName.substring(0, workbookName.indexOf(".")) + ".xlsx");
		}
		else workbookName = new String(workbookName + ".xlsx");
		File file = new File(workbookName);
		//sample excel sheet
		try {	
			FileOutputStream fout = new FileOutputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet "+ String.valueOf(workbook.getNumberOfSheets()+1) );
			
			/*
			 * Write data to excel sheet, where the first row (indexed 0) is used to generate
			 * column headers
			 */
			writeHeaderToSheet(sheet);
			writeDataToSheet(sheet, FileManager.getFileTreeMap());
			
			workbook.write(fout);
			fout.close();
			new WarningDialog("Generated Workbook " + workbookName);
		}
		catch(FileNotFoundException e) {
			//thrown if file is already opened 
			new WarningDialog("Error Generating Excel File. Make sure file is not already opened.");
		}
	}
	/**
	 * utility method to create 1st row that consist of column headers
	 * @param sheet : <i> XSSFSheet reference </i>
	 */
	private static void writeHeaderToSheet(XSSFSheet sheet)
	{
		sheet.createRow(0);
		for(int i=0; i<sheetColumnHeader.length; i++)
		{
			XSSFCell cell = sheet.getRow(0).createCell(i);
			cell.setCellValue(sheetColumnHeader[i]);
		}
	}
	/**
	 * Writes Data to Sheet
	 * @param sheet : XSSFSheet reference
	 * @param dataTree : TreeMap<String, User> reference
	 */
	private static void writeDataToSheet(XSSFSheet sheet, TreeMap<String, User> dataTree)
	{
		Set<String> policyNumberSet = dataTree.keySet();
		int row=1;
		for(String policyString : policyNumberSet)
		{	
			User currentRecord = dataTree.get(policyString);
			sheet.createRow(row);
			//loop entire row
			for(int i=0; i<sheetColumnHeader.length; i++)
			{
				XSSFCell cell = sheet.getRow(row).createCell(i);
				switch(sheetColumnHeader[i].toUpperCase())
				{
				case "S.NO" : cell.setCellValue(row); break;
				case "NAME" : cell.setCellValue(currentRecord.name); break;
				case "ADDRESS" : cell.setCellValue(currentRecord.address); break;
				case "POLICY NO." : cell.setCellValue(Double.parseDouble(policyString)); break;
				case "D.O.B": cell.setCellValue(currentRecord.dateOfBirth.getDateString());break;
				case "D.O.C": cell.setCellValue(currentRecord.doc.getDateString());break;
				case "SUM ASSURED": cell.setCellValue(currentRecord.sum);break;
				case "PLAN & TERM": cell.setCellValue(currentRecord.planAndTerm);break;
				case "MODE": cell.setCellValue(currentRecord.mode);break;
				case "PRIMIUM": cell.setCellValue(currentRecord.primium);break;
				case "NEXT DUE": cell.setCellValue(currentRecord.nextDue);break;
				}
			}
			row++;
		}
	}
}
