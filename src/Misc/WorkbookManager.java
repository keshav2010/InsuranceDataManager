package Misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	private static String sheetColumnHeader[] = {"S.No", "Name", "Address", "Policy No.", 
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
		
		//sample excel sheet
		FileOutputStream fout = new FileOutputStream(new File(workbookName));
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		
		writeHeaderToSheet(sheet);
		
		workbook.write(fout);
		fout.close();
		System.out.println("Generated Workbook : " + workbookName);
	}
	//utility method to create 1st row that consist of column headers
	private static void writeHeaderToSheet(XSSFSheet sheet)
	{
		sheet.createRow(0);
		for(int i=0; i<sheetColumnHeader.length; i++)
		{
			XSSFCell cell = sheet.getRow(0).createCell(i);
			cell.setCellValue(sheetColumnHeader[i]);
		}
	}
	
}
