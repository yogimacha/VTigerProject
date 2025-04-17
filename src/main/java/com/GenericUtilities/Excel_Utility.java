package com.GenericUtilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel_Utility {
	
	public String FetchDataFromExcel(String sheet,int rowindex,int cellIndex) throws IOException{
		FileInputStream fis =new FileInputStream("./src/test/resources/Vtiger.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheet);
		Row r =sh.getRow(rowindex);
		Cell c=r.getCell(cellIndex);
		String data=c.toString();
		return data;	
	}
//	public String FetchMultipleDataFromExcel(String sheet,int rowindex,int cellIndex) throws IOException{
//		FileInputStream fis =new FileInputStream("./src/test/resources/Vtiger.xlsx");
//		Workbook wb = WorkbookFactory.create(fis);
//		Sheet sh = wb.getSheet(sheet);
//			
//	}
	
	public void WriteBackDataToExcel(String sheet,int rowindex,int cellIndex, String data) throws EncryptedDocumentException, IOException {
		FileInputStream fis =new FileInputStream("./src/test/resources/Vtiger.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheet);
		Row r =sh.getRow(rowindex);
		Cell c=r.getCell(cellIndex);
		c.setCellValue(data);
		
		FileOutputStream fos= new FileOutputStream("./src/test/resources/Vtiger.xlsx");
		wb.write(fos);
		wb.close();

	}

	public void writeCellValue1(String string, int i, int j, String string2) {
		// TODO Auto-generated method stub
		
	}

	public String getCellValue(String string, int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeCellValue(String string, int i, int j, String uniqueOrgName) {
		// TODO Auto-generated method stub
		
	}
	
}
